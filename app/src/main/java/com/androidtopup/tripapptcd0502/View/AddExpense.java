package com.androidtopup.tripapptcd0502.View;

import static java.lang.Thread.sleep;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.androidtopup.tripapptcd0502.Database.ExpenseAppDataBaseHelper;
import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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
    String latitude, longitude;
    Context context;

    String locationAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        showLocation = findViewById(R.id.textShowLocation);
        context = getApplicationContext();

        autoCompleteTextView = findViewById(R.id.type_auto_tv);
        adapterItems = new ArrayAdapter<String>(AddExpense.this,R.layout.type_expense_item,types);
        autoCompleteTextView.setAdapter(adapterItems);

        id = getIntent().getStringExtra("id_trip_item");
        name = getIntent().getStringExtra("name_trip_item");
        destination = getIntent().getStringExtra("destination_trip_item");
        date = getIntent().getStringExtra("date_trip_item");
        assessment = getIntent().getStringExtra("assessment_trip_item");
        desc = getIntent().getStringExtra("description_trip_item");

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
                intent.putExtra("id_update_screen", id);
                intent.putExtra("name_update_screen", name);
                intent.putExtra("destination_update_screen", destination);
                intent.putExtra("date_update_screen", date);
                intent.putExtra("assessment_update_screen", assessment);
                intent.putExtra("description_update_screen", desc);
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

                try {
                    getLocation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                insertDataExpense(tripId, strType, strAmount, strTime, locationAddress);
//                try {
//                    try {
//                        getLocation();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    sleep(5000);
//                    insertDataExpense(tripId, strType, strAmount, strTime, locationAddress);
////                    sleep(5000);
//                    showSuccessDialog(strType, strAmount, strTime, locationAddress);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                showSuccessDialog(strType, strAmount, strTime, locationAddress);
            }
        });
    }

    private void insertDataExpense(int tripId, String type, String amount, String time, String address){
        ExpenseAppDataBaseHelper db = new ExpenseAppDataBaseHelper(AddExpense.this);
        db.createExpenses(tripId, type, amount, time, address);
    };

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

    private void getLocation() throws IOException {
        if (ActivityCompat.checkSelfPermission(
                AddExpense.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                AddExpense.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                double lat = location.getLatitude();
                double longi = location.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                Log.i("Value", String.valueOf(lat + longi));
                Geocoder myLocation = new Geocoder(getApplicationContext(), Locale.getDefault());

                List<Address> address =	myLocation.getFromLocation(lat, longi, 1);

                Address adAddress = address.get(0);
                String strAddress = adAddress.getAddressLine(0);
                locationAddress = adAddress.getAddressLine(0);
                showLocation.setText(strAddress);
                Log.i("location", strAddress);

            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
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
                                   String Time,
                                   String locationAdd){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddExpense.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(AddExpense.this).inflate(
                R.layout.layout_success_dailog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(getResources().getString(R.string.success_title));
        ((TextView) view.findViewById(R.id.textMessage)).setText( "\nType: " + Type +
                "\nAmount: " + Amount +
                "\nTime: " + Time +
                "\nAddress: " + locationAdd);
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