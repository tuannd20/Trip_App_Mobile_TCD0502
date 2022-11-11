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
    private final ArrayList id;
    private final ArrayList name;
    private final ArrayList destination;
    private final ArrayList date;
    private final ArrayList assessment;
    Activity activity;

    public TripAdapter(Context _context,
                       Activity _activity,
                       ArrayList _id,
                       ArrayList _name,
                       ArrayList _destination,
                       ArrayList _date,
                       ArrayList _assessment) {
        this.context = _context;
        this.activity = _activity;
        this.id = _id;
        this.name = _name;
        this.destination = _destination;
        this.date = _date;
        this.assessment = _assessment;
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
        holder.tv_trip_id.setText(String.valueOf(id.get(position)));
        holder.tv_trip_name.setText(String.valueOf(name.get(position)));
        holder.tv_trip_date.setText(String.valueOf(date.get(position)));
        holder.tv_trip_destination.setText(String.valueOf(destination.get(position)));
        holder.tv_trip_assessment.setText(new StringBuilder().append("Assessment: ")
                                                             .append(assessment.get(position)).toString());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateTripScreen = new Intent(context, UpdateTripActivity.class);
                updateTripScreen.putExtra("id", String.valueOf(id.get(position)));
                updateTripScreen.putExtra("name", String.valueOf(name.get(position)));
                updateTripScreen.putExtra("destination", String.valueOf(destination.get(position)));
                updateTripScreen.putExtra("date", String.valueOf(date.get(position)));
                updateTripScreen.putExtra("assessment", String.valueOf(assessment.get(position)));
                updateTripScreen.putExtra("description", String.valueOf(assessment.get(position)));
                activity.startActivityForResult(updateTripScreen, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
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
