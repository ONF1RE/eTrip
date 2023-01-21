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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<HelperCarpooler> list;

    public MyAdapter(Context context, ArrayList<HelperCarpooler> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HelperCarpooler helperCarpooler = list.get(position);
        holder.origin.setText(helperCarpooler.getStartPoint());
        holder.destination.setText(helperCarpooler.getEndPoint());

        DecimalFormat df = new DecimalFormat("###.####");
        holder.distance.setText(String.valueOf(df.format(helperCarpooler.getDistance())) + " km");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView origin, destination, distance;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            origin =itemView.findViewById(R.id.originTV);
            destination =itemView.findViewById(R.id.desinationTV);
            distance =itemView.findViewById(R.id.distanceTV);

        }
    }
}
