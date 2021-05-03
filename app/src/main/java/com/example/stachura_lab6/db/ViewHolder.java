package com.example.stachura_lab6.db;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.stachura_lab6.PortfolioViewModel;
import com.example.stachura_lab6.R;
import com.example.stachura_lab6.ui.display.DisplayFragment;
import com.example.stachura_lab6.ui.stocks.StocksFragment;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView symbol;
    public TextView fullname;
    public TextView price;
    public ArrayList<Stock> stockList;
    public ViewHolder(View itemView, ArrayList<Stock> stocks){
        super(itemView);
        itemView.setOnClickListener(this);
        symbol = itemView.findViewById(R.id.text_Symbol);
        fullname = itemView.findViewById(R.id.text_FullName);
        price = itemView.findViewById(R.id.text_Price);
        stockList = stocks;

    }

    @Override
    public void onClick(View v) {
        Log.i("TAG", "Clicked" + this.symbol.getText());
        Bundle bundle = new Bundle();
        bundle.putSerializable("lstStocks", (Serializable) stockList);
        bundle.putString("nameStock", this.symbol.getText().toString());
        Fragment displayFragment = new DisplayFragment();
        displayFragment.setArguments(bundle);
        Navigation.findNavController(v).navigate(R.id.displayFragment, bundle);






    }
}
