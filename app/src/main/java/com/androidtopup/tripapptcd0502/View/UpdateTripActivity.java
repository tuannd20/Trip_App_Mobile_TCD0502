package com.androidtopup.tripapptcd0502.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.androidtopup.tripapptcd0502.Database.ExpenseAppDataBaseHelper;
import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    MaterialToolbar toolbar;

    Context context;
    String  id, name, destination, date, assessment, desc;
    ExpenseAppDataBaseHelper ExpenseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip);

        ExpenseDB = new ExpenseAppDataBaseHelper(UpdateTripActivity.this);
        handleUpdateTrip();
        handleDateTrip();

        toolbar = findViewById(R.id.topAppBarSub);
        toolbar.setTitle("Trip: "+ name);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar_sub, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            confirmDelete();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                final String strName = name_input.getEditText().getText().toString().trim();
                final String strDestination = destination_input.getEditText().getText().toString().trim();
                final String strDate = date_of_trip_input.getEditText().getText().toString().trim();
                final String strDescription = description_input.getEditText().getText().toString().trim();
                final String value = getValueAssessment();

                updateDataOfTrip(id, strName, strDestination, strDate, value, strDescription);
                Intent intent = new Intent(UpdateTripActivity.this, MainActivity.class);
                startActivity(intent);
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
                Log.i("date: ", date);
                Log.i("assessment: ", assessment);

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
        rb_yes = findViewById(R.id.rb_yes_update);
        rb_no = findViewById(R.id.rb_no_update);
        Log.i("Value: ", assessment_update);

        Objects.requireNonNull(name_input.getEditText()).setText(name_update);
        Objects.requireNonNull(destination_input.getEditText()).setText(destination_update);
        Objects.requireNonNull(date_of_trip_input.getEditText()).setText(date_update);
        Objects.requireNonNull(description_input.getEditText()).setText(desc_update);

        if ("Yes".equals(assessment_update) ) {
            rb_yes.setChecked(true);
        } else if ("No".equals(assessment_update)) {
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

    private void handleDateTrip() {
        TextInputEditText mTextInputEditText = findViewById(R.id.date_trip_txt_update);

        mTextInputEditText.setInputType(InputType.TYPE_NULL);
        mTextInputEditText.setKeyListener(null);
        mTextInputEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new DatePickerUpdate();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateDOT(LocalDate dot){
        TextInputEditText dobText =  findViewById(R.id.date_trip_txt_update);
        DateTimeFormatter formatDateOfTrip = DateTimeFormatter.ofPattern( "dd/MM/uuuu" ) ;
        String result = dot.format( formatDateOfTrip ) ;
        dobText.setText(result);
    }

    private String getValueAssessment() {
        String valueAssessment = "";
        rb_yes = findViewById(R.id.rb_yes_update);
        rb_no = findViewById(R.id.rb_no_update);

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

    private void confirmDelete() {
        AlertDialog.Builder confirmAlert = new AlertDialog.Builder(this);
        confirmAlert.setTitle("Delete " + name + " ?");
        confirmAlert.setMessage("Are you sure you want to delete " + name + " ?");
        confirmAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ExpenseDB = new ExpenseAppDataBaseHelper(UpdateTripActivity.this);
                ExpenseDB.deleteOneTripById(id);
                Intent intent = new Intent(UpdateTripActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        confirmAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        confirmAlert.create().show();
    }
}
