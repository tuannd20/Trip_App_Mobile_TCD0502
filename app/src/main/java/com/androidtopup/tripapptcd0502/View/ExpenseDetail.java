package com.androidtopup.tripapptcd0502.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ExpenseDetail extends AppCompatActivity {

    MaterialToolbar toolbar;
    String  id, name, destination, date, assessment, desc;
    TextView trip_name;

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

        toolbar = findViewById(R.id.topAppBarSub);
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

}
