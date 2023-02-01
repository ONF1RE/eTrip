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

public class AdapterCarbonDriver extends RecyclerView.Adapter<AdapterCarbonDriver.MyViewHolder> {

    Context context;
    ArrayList<HelperDriver> list;

    public AdapterCarbonDriver(Context context, ArrayList<HelperDriver> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_carbon_driver, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HelperDriver helperDriver = list.get(position);
        holder.destination.setText(helperDriver.getDestination());
        holder.date.setText(helperDriver.getDate());
        holder.carType.setText(helperDriver.getCarType());
        holder.carPlate.setText(helperDriver.getCarPlate());

        DecimalFormat df = new DecimalFormat("###.##");
        holder.distance.setText(String.valueOf(df.format(helperDriver.getDistance())) + " km");
        holder.carbonSaved.setText(String.valueOf("+ " + df.format(helperDriver.getCarbonSaved())) + " kg CO2");
        holder.points.setText(String.valueOf("+ " + df.format(helperDriver.getPoints())) + " pts");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView destination, date, carType, carPlate, distance, carbonSaved, points;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            destination =itemView.findViewById(R.id.destinationCD);
            date = itemView.findViewById(R.id.dateCD);
            carType = itemView.findViewById(R.id.carTypeCD);
            carPlate = itemView.findViewById(R.id.carPlateCD);
            distance =itemView.findViewById(R.id.distanceCD);
            carbonSaved = itemView.findViewById(R.id.carbonSavedCD);
            points = itemView.findViewById(R.id.pointCD);

        }
    }
}

