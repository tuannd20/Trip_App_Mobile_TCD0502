package com.androidtopup.tripapptcd0502.View;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddExpense extends AppCompatActivity {
    String[] types = {"Food", "Transport", "Travel", "Service", "Hotel"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    MaterialToolbar toolbar;
    String  id, name, destination, date, assessment, desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        adapterItems = new ArrayAdapter<String>(AddExpense.this,R.layout.type_expense_item,types);
        autoCompleteTextView.setAdapter(adapterItems);

        id = getIntent().getStringExtra("trip_id");
        name = getIntent().getStringExtra("trip_name");
        destination = getIntent().getStringExtra("trip_destination");
        date = getIntent().getStringExtra("trip_date");
        assessment = getIntent().getStringExtra("trip_assessment");
        desc = getIntent().getStringExtra("trip_description");

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Type: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        toolbar = findViewById(R.id.topAppBarSub);
        toolbar.setTitle("New Expense");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddExpense.this, ExpenseDetail.class);
                intent.putExtra("trip_id", id);
                intent.putExtra("trip_name", name);
                intent.putExtra("trip_destination", destination);
                intent.putExtra("trip_date", date);
                intent.putExtra("trip_assessment", assessment);
                intent.putExtra("trip_description", desc);
                startActivity(intent);
            }
        });
        handleDateTrip();
    }

    private void handleDateTrip() {
        TextInputEditText mTextInputEditText = findViewById(R.id.date_expense);

        mTextInputEditText.setInputType(InputType.TYPE_NULL);
        mTextInputEditText.setKeyListener(null);
        mTextInputEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new DatePickerExpense();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateDOExpense(LocalDate dot){
        TextInputEditText dobText =  findViewById(R.id.date_expense);
        DateTimeFormatter formatDateOfTrip = DateTimeFormatter.ofPattern( "dd/MM/uuuu" ) ;
        String result = dot.format( formatDateOfTrip ) ;
        dobText.setText(result);
    }
}