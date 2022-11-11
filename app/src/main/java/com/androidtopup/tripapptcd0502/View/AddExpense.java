package com.androidtopup.tripapptcd0502.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    TextInputEditText amount_txt, time_txt;

    private static final int REQUEST_LOCATION = 1;
    TextView showLocation;
    LocationManager locationManager;
    String latitude, longitude;

    String location;


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
                Log.i("Type expense", item);
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
        amount_txt = findViewById(R.id.amount_expense);
        time_txt = findViewById(R.id.date_expense);
        add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tripId = Integer.parseInt(id);
                final String strType = autoCompleteTextView.getText().toString();
                final String strAmount = amount.getEditText().getText().toString().trim();
                final String strTime = time.getEditText().getText().toString().trim();
                Log.i("id", String.valueOf(tripId));
                Log.i("type", String.valueOf(strType));

                if (TextUtils.isEmpty(strType) && TextUtils.isEmpty(strAmount) && TextUtils.isEmpty(strTime)){
                    autoCompleteTextView.setError("Not empty");
                    amount_txt.setError("Not empty");
                    time.setError("Time is not empty");
                    showErrorInvalidDialog();
                    return;
                }

                if  (TextUtils.isEmpty(strType)){
                    autoCompleteTextView.setError("Not empty");
                    showErrorInvalidDialog();
                    return;
                }

                if  (TextUtils.isEmpty(strAmount)){
                    amount_txt.setError("Not empty");
                    showErrorInvalidDialog();
                    return;
                }

                if  (TextUtils.isEmpty(strTime)){
                    time.setError("Time is not empty");
                    showErrorInvalidDialog();
                    return;
                }

//                if (ActivityCompat.checkSelfPermission(
//                        AddExpense.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                        AddExpense.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(AddExpense.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//                } else {
//                    LocationManager locationManager = (LocationManager)
//                            getSystemService(Context.LOCATION_SERVICE);
//                    Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    if (locationGPS != null) {
//                        double lat = locationGPS.getLatitude();
//                        double longi = locationGPS.getLongitude();
//                        latitude = String.valueOf(lat);
//                        longitude = String.valueOf(longi);
//                        showLocation.setText("Your Location: " +  "Latitude: " + latitude + "Longitude: " + longitude);
//                        Log.i("dadadadaadad", latitude);
//                        Log.i("dadadadaadad", longitude);
//                    } else {
//                        Toast.makeText(AddExpense.this, "Unable to find location.", Toast.LENGTH_SHORT).show();
//                    }
//                }
                insertDataExpense(tripId, strType, strAmount, strTime);
                showSuccessDialog(strType, strAmount, strTime);
            }
        });
    }

    private void insertDataExpense(int tripId, String type, String amount, String time) {
        ExpenseAppDataBaseHelper db = new ExpenseAppDataBaseHelper(AddExpense.this);
        db.createExpenses(tripId, type, amount, time);
    }

    private void displayErrorAlert() {
        new AlertDialog.Builder(this).setTitle("Error").setMessage(
                "You need to fill all required fields"
        ).setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    private void showErrorInvalidDialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddExpense.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(AddExpense.this).inflate(
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

    private void displayDataAlert(String Type,
                                  String Amount,
                                  String Time) {
        new AlertDialog.Builder(this).setTitle("Details trip").setMessage(
                "\nType: " + Type +
                        "\nAmount: " + Amount +
                        "\nTime: " + Time
        ).setPositiveButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(AddExpense.this, ExpenseDetail.class);
                intent.putExtra("trip_id", id);
                intent.putExtra("trip_name", name);
                intent.putExtra("trip_destination", destination);
                intent.putExtra("trip_date", date);
                intent.putExtra("trip_assessment", assessment);
                intent.putExtra("trip_description", desc);
                startActivity(intent);
            }
        }).show();
    }

    private void showSuccessDialog(String Type,
                                   String Amount,
                                   String Time){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddExpense.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(AddExpense.this).inflate(
                R.layout.layout_success_dailog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(getResources().getString(R.string.success_title));
        ((TextView) view.findViewById(R.id.textMessage)).setText( "\nType: " + Type +
                "\nAmount: " + Amount +
                "\nTime: " + Time);
        ((Button) view.findViewById(R.id.buttonSuccess)).setText(getResources().getString(R.string.okay));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.done);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonSuccess).setOnClickListener(new View.OnClickListener() {
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

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}