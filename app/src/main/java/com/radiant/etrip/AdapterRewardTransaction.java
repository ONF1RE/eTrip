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

public class AdapterRewardTransaction extends RecyclerView.Adapter<AdapterRewardTransaction.MyViewHolder> {

    Context context;
    ArrayList<HelperReward> list;

    public AdapterRewardTransaction(Context context, ArrayList<HelperReward> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_reward_transaction, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HelperReward helperReward = list.get(position);
        holder.date.setText(helperReward.getDate());
        holder.item.setText(helperReward.getReward());
        holder.spent.setText( "-" + helperReward.getSpent() + "Pts");
        holder.balance.setText(helperReward.getBalance() + "Pts");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date, item, spent, balance;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date =itemView.findViewById(R.id.dateRT);
            item = itemView.findViewById(R.id.itemRT);
            spent = itemView.findViewById(R.id.spentRT);
            balance =itemView.findViewById(R.id.balanceRT);
        }
    }
}

