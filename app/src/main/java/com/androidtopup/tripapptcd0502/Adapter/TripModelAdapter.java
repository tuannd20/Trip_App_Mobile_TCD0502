package com.androidtopup.tripapptcd0502.Adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtopup.tripapptcd0502.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TripModelAdapter extends RecyclerView.Adapter<TripModelAdapter.TripModelViewHolder> {

    private List<TripModel> tripList;

    public TripModelAdapter(List<TripModel> tripList) {
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public TripModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trip_view, parent, false);
        return new TripModelAdapter.TripModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripModelViewHolder holder, @SuppressLint("RecyclerView") int position) {
        List<String> fullData = new ArrayList<String>();
        TripModel currentItem = tripList.get(position);
        String listId = String.valueOf(currentItem.getIdTrip());
        String listName = String.valueOf(currentItem.getNameTrip());
        String listDestination = String.valueOf(currentItem.getDestination());
        String listDate = String.valueOf(currentItem.getDateOfTrip());
        String listAssessment = String.valueOf(currentItem.getAssessment());
        fullData.addAll(Collections.singleton(listId));
        fullData.addAll(Collections.singleton(listName));
        fullData.addAll(Collections.singleton(listDestination));
        fullData.addAll(Collections.singleton(listDate));
        fullData.addAll(Collections.singleton(listAssessment));

        Log.i("List Id ", String.valueOf(fullData));

        for ( int i = 0; i < currentItem.toString().length(); i++) {
            holder.tv_trip_id.setText(currentItem.getIdTrip());
            holder.tv_trip_name.setText(currentItem.getNameTrip().toString());
            holder.tv_trip_destination.setText(currentItem.getDestination().toString());
            holder.tv_trip_date.setText(currentItem.getDateOfTrip().toString());
            holder.tv_trip_assessment.setText(new StringBuilder().append(" Require Assessment: ").append(currentItem.getAssessment().toString()));
        }

//        ArrayList<String> id = new ArrayList<>(currentItem.getIdTrip());

//        Log.i("Helolooo ", String.valueOf(currentItem));
//        Log.i("Helolooo ", String.valueOf(id));
        Log.i("List Id ", listId);
        Log.i("Helolooo ", String.valueOf(currentItem.getIdTrip()));
        Log.i("Helolooo ", String.valueOf(currentItem.getNameTrip()));
        Log.i("Helolooo ", String.valueOf(currentItem.getDestination()));
        Log.i("Helolooo ", String.valueOf(currentItem.getDateOfTrip()));
        Log.i("Helolooo ", String.valueOf(currentItem.getAssessment()));
//        holder.tv_trip_id.setText(currentItem.getIdTrip().toString());
//        holder.tv_trip_name.setText(currentItem.getNameTrip().toString());
//        holder.tv_trip_destination.setText(currentItem.getDestination().toString());
//        holder.tv_trip_date.setText(currentItem.getDateOfTrip().toString());
//        holder.tv_trip_assessment.setText(new StringBuilder().append(" Require Assessment: ").append(currentItem.getAssessment().toString()));
    }


    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class TripModelViewHolder extends RecyclerView.ViewHolder {
        TextView tv_trip_id, tv_trip_name, tv_trip_date, tv_trip_destination, tv_trip_assessment;
        LinearLayout mainLayout;
        CardView card_trip_item;

        public TripModelViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_trip_id = itemView.findViewById(R.id.tv_trip_id);
            tv_trip_name = itemView.findViewById(R.id.tv_trip_name);
            tv_trip_date = itemView.findViewById(R.id.tv_trip_date);
            tv_trip_destination = itemView.findViewById(R.id.tv_trip_destination);
            tv_trip_assessment = itemView.findViewById(R.id.tv_trip_assessment);
            card_trip_item = itemView.findViewById(R.id.cardTripItem);
            mainLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
