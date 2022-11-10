package com.androidtopup.tripapptcd0502.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.androidtopup.tripapptcd0502.Database.ExpenseAppDataBaseHelper;
import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.appbar.MaterialToolbar;
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

    private Context context;
    MaterialToolbar toolbar;
    TextView emptyAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        handleDateTrip();
        handleCancelBtn();
        handleInsertData();

        toolbar = findViewById(R.id.topAppBarSub);
        toolbar.setTitle("New Trip");
        setSupportActionBar(toolbar);
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

    private void handleInsertData() {
         name_input = findViewById(R.id.inputNameOfTrip);
         destination_input =  findViewById(R.id.inputDestination);
         date_of_trip_input =  findViewById(R.id.dateOfTripInput);
         description_input =  findViewById(R.id.inputDescription);
         add_trip = findViewById(R.id.button_add);
         emptyAssessment = findViewById(R.id.risk_empty);
         add_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String strName = name_input.getEditText().getText().toString().trim();
                final String strDestination = destination_input.getEditText().getText().toString().trim();
                final String strDate = date_of_trip_input.getEditText().getText().toString().trim();
                final String strDescription = description_input.getEditText().getText().toString().trim();
                final String value = getValueAssessment();

                if (TextUtils.isEmpty(strName) && TextUtils.isEmpty(strDestination) && TextUtils.isEmpty(strDate) && value.matches("")) {
                    name_input.setError("Name of trip is empty");
                    destination_input.setError("Destination is empty");
                    date_of_trip_input.setError("Date of trip is empty");
                    emptyAssessment.setVisibility(View.VISIBLE);
                    showErrorInvalidDialog();
                    return;
                }

                if  (TextUtils.isEmpty(strName)){
                    name_input.setError("Name of trip is empty");
                    showErrorInvalidDialog();
                    return;
                }

                if  (TextUtils.isEmpty(strDestination)){
                    destination_input.setError("Destination is empty");
                    showErrorInvalidDialog();
                    return;
                }

                if  (TextUtils.isEmpty(strDate)){
                    date_of_trip_input.setError("Date of trip is empty");
                    showErrorInvalidDialog();
                    return;
                }

                if (value.matches("")) {
                    emptyAssessment.setVisibility(View.VISIBLE);
                    showErrorInvalidDialog();
                    return;
                }

                showConfirmDataDialog(strName, strDestination, strDate, value, strDescription);
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

    private void insertDataOfTrip(String nameTrip,
                                  String destination,
                                  String date,
                                  String assessment,
                                  String desc) {
        ExpenseAppDataBaseHelper db = new ExpenseAppDataBaseHelper(AddTripActivity.this);
        db.create(nameTrip, destination, date, assessment, desc);
    }

    @SuppressLint("SetTextI18n")
    private void showConfirmDataDialog(String name,
                                       String destination,
                                       String date,
                                       String assessment,
                                       String description){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddTripActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(AddTripActivity.this).inflate(
                R.layout.layout_confirm_dailog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Confirm Trip Data");
        ((TextView) view.findViewById(R.id.textMessage)).setText("\nName: " + name +
                "\nDestination: " + destination +
                "\nDate of trip: " + date +
                "\nRequire Assessment: " + assessment +
                "\nDescription: " + description);
        ((Button) view.findViewById(R.id.buttonYes)).setText("Submit");
        ((Button) view.findViewById(R.id.buttonNo)).setText("Cancel");
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_action_name);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDataOfTrip(name, destination, date, assessment, description);
                Intent intent = new Intent(AddTripActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showErrorInvalidDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddTripActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(AddTripActivity.this).inflate(
                R.layout.layout_error_dailog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Invalid Data");
        ((TextView) view.findViewById(R.id.textMessage)).setText("You need to fill all required fields");
        ((Button) view.findViewById(R.id.buttonAction)).setText("Close");
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.error);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

}