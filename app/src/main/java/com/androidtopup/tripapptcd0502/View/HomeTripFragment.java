package com.androidtopup.tripapptcd0502.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
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
    ArrayList<String> id, name, destination, date, assessment;
    TripAdapter tripAdapter;
    MaterialToolbar toolbar;
    Button searchBtn;
    EditText keySearchTrip;
    String keySearch;
    MenuItem icAddTrip;
    String State;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_trip, container, false);
        context = view.getContext();
        toolbar = view.findViewById(R.id.topAppBar);
        searchBtn = view.findViewById(R.id.buttonSearch);
        keySearchTrip = view.findViewById(R.id.textTripNameFilter);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        activity.setSupportActionBar(toolbar);

        ExpenseDB = new ExpenseAppDataBaseHelper(context);
        id = new ArrayList<>();
        name = new ArrayList<>();
        destination = new ArrayList<>();
        date = new ArrayList<>();
        assessment = new ArrayList<>();

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

        if (id.toString().length() < 5) {
            State = "HIDE_ITEM";
            add_button = view.findViewById((R.id.add_button));
            add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addTripScreen = new Intent(HomeTripFragment.this.getActivity(), AddTripActivity.class);
                    HomeTripFragment.this.startActivity(addTripScreen);
                }
            });
        } else if (id.toString().length() >= 5) {
            State = "SHOW_ITEM";
            add_button = view.findViewById((R.id.add_button));
            add_button.setVisibility(View.INVISIBLE);
        }

        recyclerView = view.findViewById(R.id.recyclerViewTrip);
        tripAdapter = new TripAdapter(HomeTripFragment.this.getActivity(), HomeTripFragment.this.getActivity(),
                id, name, destination, date, assessment);
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
        String isShow = "SHOW_ITEM";
        icAddTrip = menu.findItem(R.id.add);

        if (State != isShow) {
            icAddTrip.setVisible(false);
        } else {
            icAddTrip.setVisible(true);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.delete:
//                confirmDeleteAll();
                showWarningDialogDeleteAllTrip(view);
                return true;
            case R.id.add:
                handleNavigateScreen();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void handleStoreDataInArrays(String key){
        Cursor cursor = ExpenseDB.displayAllTrip(key);
        if (cursor.getCount() == 0) {
//            Toast.makeText(this.getContext(), "No Data", Toast.LENGTH_SHORT).show();
            Log.i("No data", "NOdata");
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                destination.add(cursor.getString(2));
                date.add(cursor.getString(3));
                assessment.add(cursor.getString(4));
            }
        }
    }

    public void showWarningDialogDeleteAllTrip(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeTripFragment.this.getContext(), R.style.AlertDialogTheme);
         view = LayoutInflater.from(HomeTripFragment.this.getContext()).inflate(
                R.layout.layout_warning_dailog,
                (ConstraintLayout)view.findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Delete Warning");
        ((TextView) view.findViewById(R.id.textMessage)).setText("Are you sure you want to delete all trip");
        ((Button) view.findViewById(R.id.buttonYes)).setText(getResources().getString(R.string.yes));
        ((Button) view.findViewById(R.id.buttonNo)).setText(getResources().getString(R.string.no));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.warning);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseDB = new ExpenseAppDataBaseHelper(HomeTripFragment.this.getContext());
                ExpenseDB.deleteAllTrip();
                Intent intent = new Intent(HomeTripFragment.this.getContext(), MainActivity.class);
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

    private void handleNavigateScreen() {
        Intent screen = new Intent(HomeTripFragment.this.getActivity(), AddTripActivity.class);
        startActivity(screen);
    }
}