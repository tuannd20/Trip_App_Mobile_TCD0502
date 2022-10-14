package com.androidtopup.tripapptcd0502.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtopup.tripapptcd0502.Adapter.TripAdapter;
import com.androidtopup.tripapptcd0502.Database.ExpenseAppDataBaseHelper;
import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeTripFragment extends Fragment  {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    Context context;
    ExpenseAppDataBaseHelper ExpenseDB;
    ArrayList<String> trip_id, trip_name, trip_destination, trip_date, trip_assessment;
    TripAdapter tripAdapter;
    MaterialToolbar toolbar;
    Button searchBtn;
    EditText keySearchTrip;
    String keySearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_trip, container, false);
        context = view.getContext();
        toolbar = view.findViewById(R.id.topAppBar);
        searchBtn = view.findViewById(R.id.buttonSearch);
        keySearchTrip = view.findViewById(R.id.textTripNameFilter);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        activity.setSupportActionBar(toolbar);

        add_button = view.findViewById((R.id.add_button));
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTripScreen = new Intent(HomeTripFragment.this.getActivity(), AddTripActivity.class);
                HomeTripFragment.this.startActivity(addTripScreen);
            }
        });

        ExpenseDB = new ExpenseAppDataBaseHelper(context);
        trip_id = new ArrayList<>();
        trip_name = new ArrayList<>();
        trip_destination = new ArrayList<>();
        trip_date = new ArrayList<>();
        trip_assessment = new ArrayList<>();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keySearch = keySearchTrip.getText().toString();
                Intent intent = new Intent(HomeTripFragment.this.getActivity(), MainActivity.class);
                intent.putExtra("key", keySearch);
                startActivity(intent);
            }
        });

        keySearchTrip.setText((CharSequence) requireActivity().getIntent().getStringExtra("key"));

        if (keySearchTrip.length() == 0) {
            keySearch = keySearchTrip.getText().toString();
            handleStoreDataInArrays(keySearch);
        } else if (keySearchTrip.length() > 0 ) {
            String key = keySearchTrip.getText().toString();
            handleStoreDataInArrays(key);
        }

        recyclerView = view.findViewById(R.id.recyclerViewTrip);
        tripAdapter = new TripAdapter(HomeTripFragment.this.getActivity(), HomeTripFragment.this.getActivity(),
                trip_id, trip_name, trip_destination, trip_date, trip_assessment);
        recyclerView.setAdapter(tripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeTripFragment.this.getActivity()));

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.delete:
                confirmDeleteAll();
                return true;
            case R.id.search:
                Toast.makeText(context, "Search Successfully", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void handleStoreDataInArrays(String key){
        Cursor cursor = ExpenseDB.displayAllTrip(key);
        if (cursor.getCount() == 0) {
            Toast.makeText(this.getContext(), "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                trip_id.add(cursor.getString(0));
                trip_name.add(cursor.getString(1));
                trip_destination.add(cursor.getString(2));
                trip_date.add(cursor.getString(3));
                trip_assessment.add(cursor.getString(4));
            }
        }
    }

    private void getkeySearch() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keySearchTrip.getText().toString().trim();
                Log.i("Search key", String.valueOf(keySearchTrip));
            }
        });
    }

    private void handleSearchTrip(String key) {
        Cursor cursor = ExpenseDB.searchTrip(key);
        if (cursor.getCount() == 0) {
            Toast.makeText(this.getContext(), "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                trip_id.add(cursor.getString(0));
                trip_name.add(cursor.getString(1));
                trip_destination.add(cursor.getString(2));
                trip_date.add(cursor.getString(3));
                trip_assessment.add(cursor.getString(4));
            }
        }
    }

    private void confirmDeleteAll() {
        AlertDialog.Builder confirmAlert = new AlertDialog.Builder(HomeTripFragment.this.requireContext());
        confirmAlert.setTitle("Delete ");
        confirmAlert.setMessage("Are you sure you want to delete ");
        confirmAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ExpenseDB = new ExpenseAppDataBaseHelper(HomeTripFragment.this.getContext());
                ExpenseDB.deleteAllTrip();
                Intent intent = new Intent(HomeTripFragment.this.getContext(), MainActivity.class);
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutApp, fragment);
        fragmentTransaction.commit();
    }
}