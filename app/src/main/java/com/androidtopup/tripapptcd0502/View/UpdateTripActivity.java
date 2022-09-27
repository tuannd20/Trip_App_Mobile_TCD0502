package com.androidtopup.tripapptcd0502.View;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidtopup.tripapptcd0502.Database.ExpenseAppDataBaseHelper;
import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class UpdateTripActivity extends AppCompatActivity {
    TextInputLayout name_input;
    TextInputLayout destination_input;
    TextInputLayout date_of_trip_input;
    RadioButton rb_yes;
    RadioButton rb_no;
    TextInputLayout description_input;
    Button update_trip_btn;
    Button view_expense_btn;

    Context context;
    String  id, name, destination, date, assessment, desc;
    ExpenseAppDataBaseHelper ExpenseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip);

        ExpenseDB = new ExpenseAppDataBaseHelper(UpdateTripActivity.this);
        handleUpdateTrip();
    }

    private void handleUpdateTrip() {
        name_input = findViewById(R.id.updateNameOfTrip);
        destination_input =  findViewById(R.id.updateDestination);
        date_of_trip_input =  findViewById(R.id.updateDate);
        description_input =  findViewById(R.id.updateDescription);
        update_trip_btn = findViewById((R.id.button_update));
        getIntentData();
        update_trip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataOfTrip(id, name, destination, date, assessment, desc);
            }
        });
    }

    void getIntentData() {
        if (    getIntent().hasExtra("id") &&
                getIntent().hasExtra("name") &&
                getIntent().hasExtra("destination") &&
                getIntent().hasExtra("assessment") &&
                getIntent().hasExtra("date"))
            {

                id = getIntent().getStringExtra("id");
                name = getIntent().getStringExtra("name");
                destination = getIntent().getStringExtra("destination");
                date = getIntent().getStringExtra("date");
                assessment = getIntent().getStringExtra("assessment");
                Log.i("id: ", id);

                Cursor cursor = ExpenseDB.getDescriptionById(id);
                if(cursor.moveToFirst()){
                    do{
                        desc = cursor.getString(0);
                    }while(cursor.moveToNext());
                }
                setIntendData(name, destination, date, assessment, desc);
            } else {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            }
    }

    void setIntendData(String name_update, String destination_update,
                       String date_update, String assessment_update, String desc_update) {
        String value = assessment_update;
        rb_yes = findViewById(R.id.rb_yes);
        rb_no = findViewById(R.id.rb_no);

        Objects.requireNonNull(name_input.getEditText()).setText(name_update);
        Objects.requireNonNull(destination_input.getEditText()).setText(destination_update);
        Objects.requireNonNull(date_of_trip_input.getEditText()).setText(date_update);
        Objects.requireNonNull(description_input.getEditText()).setText(desc_update);

        if (rb_yes.getText() == value) {
            rb_yes.setChecked(true);
        } else {
            rb_no.setChecked(true);
        }
    }

    private void updateDataOfTrip(String id, String nameTrip,
                                  String destination,
                                  String date,
                                  String assessment,
                                  String desc) {
        ExpenseDB = new ExpenseAppDataBaseHelper(UpdateTripActivity.this);
        ExpenseDB.updateData(id, nameTrip, destination, date, assessment, desc);
    }

    private String getValueAssessment() {
        String valueAssessment = "";
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
}
