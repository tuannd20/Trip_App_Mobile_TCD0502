package com.androidtopup.tripapptcd0502.View;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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

        id = getIntent().getStringExtra("id_update_screen");
        name = getIntent().getStringExtra("name_update_screen");
        destination = getIntent().getStringExtra("destination_update_screen");
        date = getIntent().getStringExtra("date_update_screen");
        assessment = getIntent().getStringExtra("assessment_update_screen");
        desc = getIntent().getStringExtra("description_update_screen");
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
            Log.i("No data", "Trip do not have any expense");
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
        addExpenseScreen.putExtra("id_trip_item", id);
        addExpenseScreen.putExtra("name_trip_item", name);
        addExpenseScreen.putExtra("destination_trip_item", destination);
        addExpenseScreen.putExtra("date_trip_item", date);
        addExpenseScreen.putExtra("assessment_trip_item", assessment);
        addExpenseScreen.putExtra("description_trip_item", desc);
        startActivity(addExpenseScreen);
    }
}
