package com.androidtopup.tripapptcd0502.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ExpenseDetail extends AppCompatActivity {

    MaterialToolbar toolbar;
    String  id, name, destination, date, assessment, desc;
    TextView trip_name;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_view_detail);

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
}
