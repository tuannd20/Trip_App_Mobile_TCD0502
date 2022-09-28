package com.androidtopup.tripapptcd0502.View;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtopup.tripapptcd0502.Adapter.TripAdapter;
import com.androidtopup.tripapptcd0502.Database.ExpenseAppDataBaseHelper;
import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeTripFragment extends Fragment  {

    View view;
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    Context context;
    ExpenseAppDataBaseHelper ExpenseDB;
    ArrayList<String> trip_id, trip_name, trip_destination, trip_date, trip_assessment;
    TripAdapter tripAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_trip, container, false);
        context = view.getContext();
        recyclerView = view.findViewById(R.id.recyclerViewTrip);
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

        handleStoreDataInArrays();

        tripAdapter = new TripAdapter(HomeTripFragment.this.getActivity(), HomeTripFragment.this.getActivity(),
                trip_id, trip_name, trip_destination, trip_date, trip_assessment);
        recyclerView.setAdapter(tripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeTripFragment.this.getActivity()));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            getActivity().recreate();
        }
    }

    public void handleStoreDataInArrays(){
        Cursor cursor = ExpenseDB.displayAllTrip();
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
}