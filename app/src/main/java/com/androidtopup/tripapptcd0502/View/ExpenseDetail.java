package com.androidtopup.tripapptcd0502.View;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtopup.tripapptcd0502.Adapter.ExpenseAdapter;
import com.androidtopup.tripapptcd0502.Database.ExpenseAppDataBaseHelper;
import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class ExpenseDetail extends AppCompatActivity {

    MaterialToolbar toolbar;
    String  id, name, destination, date, assessment, desc;
    TextView trip_name;

    ExpenseAppDataBaseHelper ExpenseDB;
    ArrayList<String> expense_id, expense_type, expense_amount, expense_time;

    RecyclerView recyclerView;
    ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_view_detail);

        ExpenseDB = new ExpenseAppDataBaseHelper(ExpenseDetail.this);
        expense_id = new ArrayList<>();
        expense_type = new ArrayList<>();
        expense_amount = new ArrayList<>();
        expense_time = new ArrayList<>();

        id = getIntent().getStringExtra("trip_id");
        name = getIntent().getStringExtra("trip_name");
        destination = getIntent().getStringExtra("trip_destination");
        date = getIntent().getStringExtra("trip_date");
        assessment = getIntent().getStringExtra("trip_assessment");
        desc = getIntent().getStringExtra("trip_description");
        trip_name = findViewById(R.id.textViewTripName);
        trip_name.setText(new StringBuilder().append("Trip name: ").append(name).toString());

        toolbar = findViewById(R.id.topAppBarExpense);
        toolbar.setTitle("Expense Detail");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseDetail.this, UpdateTripActivity.class);
                intent.putExtra("id", String.valueOf(id));
                intent.putExtra("name", String.valueOf(name));
                intent.putExtra("destination", String.valueOf(destination));
                intent.putExtra("date", String.valueOf(date));
                intent.putExtra("assessment", String.valueOf(assessment));
                intent.putExtra("description", String.valueOf(desc));
                startActivity(intent);
            }
        });

//        ExpenseDB = new ExpenseAppDataBaseHelper(ExpenseDetail.this);
//        expense_id = new ArrayList<>();
//        expense_type = new ArrayList<>();
//        expense_amount = new ArrayList<>();
//        expense_time = new ArrayList<>();

        int tripId = Integer.parseInt(id);
        getAllExpenseByTripId(tripId);

        recyclerView = findViewById(R.id.recyclerView);
        expenseAdapter = new ExpenseAdapter(ExpenseDetail.this, expense_id, expense_type, expense_amount, expense_time);
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpenseDetail.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar_expense, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addExpense) {
            handleNavigateScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllExpenseByTripId(int tripId) {
        Cursor cursor = ExpenseDB.readAllExpenseOfTrip(tripId);
        if (cursor.getCount() == 0) {
//            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
//            AlertDialog.Builder confirmAlert = new AlertDialog.Builder(ExpenseDetail.this);
//            confirmAlert.setTitle("Expense");
//            confirmAlert.setMessage("The trip do not have expense");
//            confirmAlert.setPositiveButton("Close", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            }).show();
            showWarningDialog();
        } else {
            while (cursor.moveToNext()) {
                String idExpense = cursor.getString(0);
                expense_id.add(idExpense);
                expense_type.add(cursor.getString(2));
                expense_amount.add(cursor.getString(4));
                expense_time.add(cursor.getString(3));
            }
        }
    }

    private void handleNavigateScreen() {
        Intent addExpenseScreen = new Intent(ExpenseDetail.this, AddExpense.class);
        addExpenseScreen.putExtra("trip_id", id);
        addExpenseScreen.putExtra("trip_name", name);
        addExpenseScreen.putExtra("trip_destination", destination);
        addExpenseScreen.putExtra("trip_date", date);
        addExpenseScreen.putExtra("trip_assessment", assessment);
        addExpenseScreen.putExtra("trip_description", desc);
        startActivity(addExpenseScreen);
    }

    private void showWarningDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseDetail.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ExpenseDetail.this).inflate(
                R.layout.layout_warning_dailog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(getResources().getString(R.string.warning_title));
        ((TextView) view.findViewById(R.id.textMessage)).setText(getResources().getString(R.string.dummy_text));
        ((Button) view.findViewById(R.id.buttonYes)).setText(getResources().getString(R.string.yes));
        ((Button) view.findViewById(R.id.buttonNo)).setText(getResources().getString(R.string.no));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.warning);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Toast.makeText(ExpenseDetail.this, "Yes", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Toast.makeText(ExpenseDetail.this, "No", Toast.LENGTH_SHORT).show();
            }
        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
