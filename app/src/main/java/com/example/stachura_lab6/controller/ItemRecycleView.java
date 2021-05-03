package com.example.stachura_lab6.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stachura_lab6.db.Stock;
import com.example.stachura_lab6.db.ViewHolder;



import java.util.ArrayList;

public class ItemRecycleView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//class to handle the adpation of ViewHolder entities to the recyclerView.
    private int layout_id;
    private ArrayList<Stock> stockList;

    public ItemRecycleView(int id, ArrayList<Stock> stocks) {
        this.layout_id = id;
        this.stockList = stocks;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout_id, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view, stockList);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView name = ((ViewHolder) holder).symbol;
        TextView full = ((ViewHolder) holder).fullname;
        TextView price = ((ViewHolder) holder).price;
        name.setText(stockList.get(position).name);
        full.setText("Company Name: "+ stockList.get(position).fullName);
        price.setText("Price: " + stockList.get(position).price);
    }




    @Override
    public int getItemCount() {
        // return 0 if null to avoid crashing and display the correct splash text in Stocks view.
        return (stockList == null) ? 0 : stockList.size();
    }


}
