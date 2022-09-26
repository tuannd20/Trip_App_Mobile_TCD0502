package com.androidtopup.tripapptcd0502.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtopup.tripapptcd0502.R;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private Context context;
    private final ArrayList trip_id;
    private final ArrayList trip_name;
    private final ArrayList trip_destination;
    private final ArrayList trip_date;
    private final ArrayList trip_assessment;

    public TripAdapter(Context context,
                       ArrayList _id,
                       ArrayList _name,
                       ArrayList _destination,
                       ArrayList _date,
                       ArrayList _assessment) {
        this.context = context;
        this.trip_name = _name;
        this.trip_id = _id;
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
    public void onBindViewHolder(@NonNull TripAdapter.TripViewHolder holder, int position) {
        holder.tv_trip_id.setText(String.valueOf(trip_id.get(position)));
        holder.tv_trip_name.setText(String.valueOf(trip_name.get(position)));
        holder.tv_trip_date.setText(String.valueOf(trip_date.get(position)));
        holder.tv_trip_destination.setText(String.valueOf(trip_destination.get(position)));
        holder.tv_trip_assessment.setText(String.valueOf(trip_assessment.get(position)));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView tv_trip_id, tv_trip_name, tv_trip_date, tv_trip_destination, tv_trip_assessment;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_trip_id = itemView.findViewById(R.id.tv_trip_id);
            tv_trip_name = itemView.findViewById(R.id.tv_trip_name);
            tv_trip_date = itemView.findViewById(R.id.tv_trip_date);
            tv_trip_destination = itemView.findViewById(R.id.tv_trip_destination);
            tv_trip_assessment = itemView.findViewById(R.id.tv_trip_assessment);
        }
    }
}
