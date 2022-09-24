package com.androidtopup.tripapptcd0502;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddTripActivity extends AppCompatActivity {

    TextInputLayout name_input;
    TextInputLayout destination_input;
    TextInputLayout date_of_trip_input;
    RadioButton rb_yes;
    RadioButton rb_no;
    TextInputLayout description_input;
    Button add_trip;
    Button cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        handleDateTrip();
        handleCancelBtn();
        getInputData();
    }

    private void handleDateTrip() {
        TextInputEditText mTextInputEditText = findViewById(R.id.date_trip);

        mTextInputEditText.setInputType(InputType.TYPE_NULL);
        mTextInputEditText.setKeyListener(null);
        mTextInputEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    private void getInputData() {
        name_input = (TextInputLayout)findViewById(R.id.inputNameOfTrip);
        destination_input = (TextInputLayout)findViewById(R.id.inputDestination);
        date_of_trip_input = (TextInputLayout)findViewById(R.id.dateOfTripInput);
        description_input = (TextInputLayout)findViewById(R.id.inputDescription);
        add_trip = findViewById(R.id.button_add);
        add_trip.setOnClickListener(new View.OnClickListener() {
            String strName = name_input.getEditText().getText().toString().trim();
            String strDestination = destination_input.getEditText().getText().toString().trim();
            String strDate = date_of_trip_input.getEditText().getText().toString().trim();
            String strDescription = description_input.getEditText().getText().toString().trim();
            String value = getValueAssessment();
            @Override
            public void onClick(View view) {
                insertDataOfTrip(strName, strDestination, strDate, value, strDescription);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateDOB(LocalDate dot){
        TextInputEditText dobText = (TextInputEditText) findViewById(R.id.date_trip);
        DateTimeFormatter formatDateOfTrip = DateTimeFormatter.ofPattern( "dd/MM/uuuu" ) ;
        String result = dot.format( formatDateOfTrip ) ;
        dobText.setText(result);
    }

    private void handleCancelBtn() {
        cancel_btn = findViewById(R.id.button_cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String getValueAssessment() {
        String valueAssessment = new String();
        rb_yes = findViewById(R.id.rb_yes);
        rb_no = findViewById(R.id.rb_no);

        if (rb_yes.isChecked()){
            valueAssessment = rb_yes.getText().toString().trim();
            return valueAssessment;
        }
        if (rb_no.isChecked()) {
            valueAssessment = rb_no.getText().toString().trim();
            return valueAssessment;
        }
        return valueAssessment;
    }

    private void insertDataOfTrip(String nameTrip, String destination, String date, String assessment, String desc) {
        ExpenseAppDataBaseHelper db = new ExpenseAppDataBaseHelper(AddTripActivity.this);
        db.create(nameTrip, destination, date, assessment, desc);
    }

}