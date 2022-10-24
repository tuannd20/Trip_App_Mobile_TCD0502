package com.androidtopup.tripapptcd0502.View;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.androidtopup.tripapptcd0502.Database.ExpenseAppDataBaseHelper;
import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddExpense extends AppCompatActivity {
    String[] types = {"Food", "Transport", "Travel", "Service", "Hotel"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    MaterialToolbar toolbar;
    String  id, name, destination, date, assessment, desc;

    TextInputLayout type;
    TextInputLayout amount;
    TextInputLayout time;
    Button add_expense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        autoCompleteTextView = findViewById(R.id.type_auto_tv);
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
        handleAddExpense();
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

    private void handleAddExpense() {
        type = findViewById(R.id.dropdownType);
        amount = findViewById(R.id.inputAmount);
        time = findViewById(R.id.dateOfExpenseInput);
        add_expense = findViewById(R.id.button_add_expense);
        add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tripId = Integer.parseInt(id);
                final String strType = autoCompleteTextView.getText().toString();
                final String strAmount = amount.getEditText().getText().toString().trim();
                final String strTime = time.getEditText().getText().toString().trim();
                Log.i("id", String.valueOf(tripId));
                Log.i("type", String.valueOf(strType));

                insertDataExpense(tripId, strType, strAmount, strTime);
            }
        });
    }

    private void insertDataExpense(int tripId, String type, String amount, String time) {
        ExpenseAppDataBaseHelper db = new ExpenseAppDataBaseHelper(AddExpense.this);
        db.createExpenses(tripId, type, amount, time);
    }
}