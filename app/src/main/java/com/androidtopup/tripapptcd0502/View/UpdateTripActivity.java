package com.androidtopup.tripapptcd0502.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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
                Intent intent = new Intent(UpdateTripActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        navigateExpensesScreen();
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
            showWarningDialogDeleteAllTrip();
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

                if (TextUtils.isEmpty(strName) && TextUtils.isEmpty(strDestination) && TextUtils.isEmpty(strDate) && value.matches("")) {
                    name_input.setError("Name of trip is empty");
                    destination_input.setError("Destination is empty");
                    date_of_trip_input.setError("Date of trip is empty");
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

                showConfirmDataDialog(id, strName, strDestination, strDate, value, strDescription);
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

    private void navigateExpensesScreen() {
        view_expense_btn = findViewById(R.id.button_view_expenses);
        view_expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateTripActivity.this, ExpenseDetail.class);
                intent.putExtra("trip_id", id);
                intent.putExtra("trip_name", name);
                intent.putExtra("trip_destination", destination);
                intent.putExtra("trip_date", date);
                intent.putExtra("trip_assessment", assessment);
                intent.putExtra("trip_description", desc);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showConfirmDataDialog(String id, String name,
                                       String destination,
                                       String date,
                                       String assessment,
                                       String description){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateTripActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(UpdateTripActivity.this).inflate(
                R.layout.layout_confirm_dailog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Confirm to update trip");
        ((TextView) view.findViewById(R.id.textMessage)).setText("\nName: " + name +
                "\nDestination: " + destination +
                "\nDate of trip: " + date +
                "\nRequire Assessment: " + assessment +
                "\nDescription: " + description);
        ((Button) view.findViewById(R.id.buttonYes)).setText("Submit");
        ((Button) view.findViewById(R.id.buttonNo)).setText("Cancel");
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_action_name);

        final android.app.AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataOfTrip(id, name, destination, date, assessment, description);
                Intent intent = new Intent(UpdateTripActivity.this, MainActivity.class);
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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateTripActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(UpdateTripActivity.this).inflate(
                R.layout.layout_error_dailog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Invalid Data");
        ((TextView) view.findViewById(R.id.textMessage)).setText("You need to fill all required fields");
        ((Button) view.findViewById(R.id.buttonAction)).setText("Close");
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.error);

        final android.app.AlertDialog alertDialog = builder.create();

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

    public void showWarningDialogDeleteAllTrip(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTripActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(UpdateTripActivity.this).inflate(
                R.layout.layout_warning_dailog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Delete " + name + " ?");
        ((TextView) view.findViewById(R.id.textMessage)).setText("Are you sure you want to delete trip");
        ((Button) view.findViewById(R.id.buttonYes)).setText(getResources().getString(R.string.yes));
        ((Button) view.findViewById(R.id.buttonNo)).setText(getResources().getString(R.string.no));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.warning);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseDB = new ExpenseAppDataBaseHelper(UpdateTripActivity.this);
                ExpenseDB.deleteOneTripById(id);
                Intent intent = new Intent(UpdateTripActivity.this, MainActivity.class);
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
}
