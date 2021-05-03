package com.example.stachura_lab6.ui.stocks;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stachura_lab6.MainActivity;
import com.example.stachura_lab6.PortfolioViewModel;
import com.example.stachura_lab6.R;
import com.example.stachura_lab6.controller.ItemRecycleView;
import com.example.stachura_lab6.db.Stock;

import java.util.ArrayList;
import java.util.List;

public class StocksFragment extends Fragment {

    private PortfolioViewModel portfolioViewModel;
    public static ItemRecycleView itemRecycleView;
    public ArrayList<Stock> allStocks;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i("Stocks", "ONCREATEVIEW CALLED");
        View root = inflater.inflate(R.layout.fragment_stocks, container, false);
        portfolioViewModel = ((MainActivity) getActivity()).portfolioViewModel;
        allStocks = (ArrayList<Stock>) portfolioViewModel.getAllStocks().getValue();
        if (allStocks != null && (allStocks.size() > 0)) {
            recyclerView = root.findViewById(R.id.stock_recyclerView);
            itemRecycleView = new ItemRecycleView(R.layout.cardview_layout, allStocks);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(itemRecycleView);
            Log.i("Stocks", "ONCREATEVIEW Finished");
        }
        else{
            TextView empty = root.findViewById(R.id.emptyText);
            empty.setText("You have no stocks in your portfolio. Add some in the Create tab!");
        }
        return root;
    }


}