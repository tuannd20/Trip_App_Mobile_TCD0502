package com.androidtopup.tripapptcd0502;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeTripFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_trip, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        add_button = view.findViewById((R.id.add_button));
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeTripFragment.this.getActivity(), AddTripActivity.class);
                HomeTripFragment.this.startActivity(intent);
            }
        });
        return view;
    }
}