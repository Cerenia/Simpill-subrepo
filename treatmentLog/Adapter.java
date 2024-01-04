package com.example.simpill.ext.treatmentLog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpill.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    // Keeping it simple for the test Repo, everything will be done from the UI Thread
    List<Treatment> treatments;
    private final String pattern = "yyyy-MM-dd H-m";
    final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView treatment;
        private final TextView amount;
        private final TextView unit;
        private final TextView date;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            treatment = itemView.findViewById(R.id.treatment);
            amount = itemView.findViewById(R.id.amount);
            unit = itemView.findViewById(R.id.unit);
            date = itemView.findViewById(R.id.date);
        }

        public TextView getAmount() {
            return amount;
        }

        public TextView getTreatment() {
            return treatment;
        }

        public TextView getUnit() {
            return unit;
        }

        public TextView getDate() {
            return date;
        }
    }

    public Adapter(List<Treatment> list){
        treatments = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getAmount().setText(treatments.get(position).getAmount().toString());
        holder.getUnit().setText(treatments.get(position).getUnit());
        holder.getTreatment().setText(treatments.get(position).getTreatment());
        holder.getDate().setText(dateFormat.format(new Date(treatments.get(position).getTime())));
    }

    @Override
    public int getItemCount() {
       return treatments.size();
    }
}
