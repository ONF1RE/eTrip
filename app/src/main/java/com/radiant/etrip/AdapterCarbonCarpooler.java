package com.radiant.etrip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterCarbonCarpooler extends RecyclerView.Adapter<AdapterCarbonCarpooler.MyViewHolder> {

    Context context;
    ArrayList<HelperCarpooler> list;

    public AdapterCarbonCarpooler(Context context, ArrayList<HelperCarpooler> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_carbon_carpooler, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HelperCarpooler helperCarpooler = list.get(position);
        holder.origin.setText(helperCarpooler.getStartPoint());
        holder.destination.setText(helperCarpooler.getEndPoint());
        holder.date.setText(helperCarpooler.getDate());

        DecimalFormat df = new DecimalFormat("###.##");
        holder.distance.setText(String.valueOf(df.format(helperCarpooler.getDistance())) + " km");

        holder.carbonSaved.setText(String.valueOf("+ " + df.format(helperCarpooler.getCarbonSaved())) + " kg CO2");
        holder.points.setText(String.valueOf("+ " + df.format(helperCarpooler.getPoints())) + " pts");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView origin, destination, date, distance, carbonSaved, points;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            origin =itemView.findViewById(R.id.originCC);
            destination =itemView.findViewById(R.id.destinationCC);
            date = itemView.findViewById(R.id.dateCC);
            distance =itemView.findViewById(R.id.distanceCC);
            carbonSaved = itemView.findViewById(R.id.carbonSavedCC);
            points = itemView.findViewById(R.id.pointCC);

        }
    }
}
