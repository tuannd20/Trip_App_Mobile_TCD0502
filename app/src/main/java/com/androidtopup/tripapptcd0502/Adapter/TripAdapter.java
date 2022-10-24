package com.androidtopup.tripapptcd0502.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtopup.tripapptcd0502.R;
import com.androidtopup.tripapptcd0502.View.UpdateTripActivity;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private Context context;
    private final ArrayList trip_id;
    private final ArrayList trip_name;
    private final ArrayList trip_destination;
    private final ArrayList trip_date;
    private final ArrayList trip_assessment;
    Activity activity;

    public TripAdapter(Context context,
                       Activity activity,
                       ArrayList _id,
                       ArrayList _name,
                       ArrayList _destination,
                       ArrayList _date,
                       ArrayList _assessment) {
        this.context = context;
        this.activity = activity;
        this.trip_id = _id;
        this.trip_name = _name;
        this.trip_destination = _destination;
        this.trip_date = _date;
        this.trip_assessment = _assessment;
    }

    @NonNull
    @Override
    public TripAdapter.TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trip_view, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.TripViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_trip_id.setText(String.valueOf(trip_id.get(position)));
        holder.tv_trip_name.setText(String.valueOf(trip_name.get(position)));
        holder.tv_trip_date.setText(String.valueOf(trip_date.get(position)));
        holder.tv_trip_destination.setText(String.valueOf(trip_destination.get(position)));
        holder.tv_trip_assessment.setText(new StringBuilder().append("Assessment: ")
                                                             .append(String.valueOf(trip_assessment.get(position))).toString());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateTripScreen = new Intent(context, UpdateTripActivity.class);
                updateTripScreen.putExtra("id", String.valueOf(trip_id.get(position)));
                updateTripScreen.putExtra("name", String.valueOf(trip_name.get(position)));
                updateTripScreen.putExtra("destination", String.valueOf(trip_destination.get(position)));
                updateTripScreen.putExtra("date", String.valueOf(trip_date.get(position)));
                updateTripScreen.putExtra("assessment", String.valueOf(trip_assessment.get(position)));
                updateTripScreen.putExtra("description", String.valueOf(trip_assessment.get(position)));

                activity.startActivityForResult(updateTripScreen, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trip_id.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView tv_trip_id, tv_trip_name, tv_trip_date, tv_trip_destination, tv_trip_assessment;
        LinearLayout mainLayout;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_trip_id = itemView.findViewById(R.id.tv_trip_id);
            tv_trip_name = itemView.findViewById(R.id.tv_trip_name);
            tv_trip_date = itemView.findViewById(R.id.tv_trip_date);
            tv_trip_destination = itemView.findViewById(R.id.tv_trip_destination);
            tv_trip_assessment = itemView.findViewById(R.id.tv_trip_assessment);
            mainLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
