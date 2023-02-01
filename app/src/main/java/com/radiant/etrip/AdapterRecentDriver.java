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

public class AdapterRecentDriver extends RecyclerView.Adapter<AdapterRecentDriver.MyViewHolder> {

    Context context;
    ArrayList<HelperDriver> list;

    public AdapterRecentDriver(Context context, ArrayList<HelperDriver> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_recent_driver, parent, false);
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView destination, date, carType, carPlate, distance;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            destination =itemView.findViewById(R.id.destinationRD);
            date = itemView.findViewById(R.id.dateRD);
            carType = itemView.findViewById(R.id.carTypeRD);
            carPlate = itemView.findViewById(R.id.carPlateRD);
            distance =itemView.findViewById(R.id.distanceRD);

        }
    }
}

