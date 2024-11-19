package com.example.javamainapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Transection> list;
    private final LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<Transection> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Transection getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Check if the existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, parent, false);
            holder = new ViewHolder();
            holder.transactionCategory = convertView.findViewById(R.id.transactionCategory);
            holder.transactionType = convertView.findViewById(R.id.transactionType);
            holder.transactionAmount = convertView.findViewById(R.id.transactionAmount);
            convertView.setTag(holder); // Store the holder with the view
        } else {
            holder = (ViewHolder) convertView.getTag(); // Reuse the holder
        }

        // Get the current transaction and set the data to the views
        Transection transaction = getItem(position);
        holder.transactionCategory.setText(transaction.category); // Adjust this method according to your Transection class
        holder.transactionType.setText(transaction.type); // Adjust this method according to your Transection class
        holder.transactionAmount.setText(String.format("$%.2f", transaction.amount)); // Adjust this method according to your Transection class

        return convertView;
    }

    // ViewHolder pattern for better performance
    private static class ViewHolder {
        TextView transactionCategory;
        TextView transactionType;
        TextView transactionAmount;
    }
}
