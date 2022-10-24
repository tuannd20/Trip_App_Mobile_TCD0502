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

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>{

    Context context;
    ArrayList id, type, amount, time;

    public ExpenseAdapter(Context context, ArrayList id, ArrayList type, ArrayList amount, ArrayList time) {
        this.context = context;
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.time = time;
    }

    @NonNull
    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expense_view, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ExpenseViewHolder holder, int position) {
        holder.type.setText(String.valueOf(type.get(position)));
        holder.amount.setText(String.valueOf(amount.get(position)));
        holder.time.setText(String.valueOf(time.get(position)));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView type, amount, time;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.expense_type_txt);
            amount = itemView.findViewById(R.id.expense_amount_txt);
            time = itemView.findViewById(R.id.expense_date_txt);
        }
    }
}
