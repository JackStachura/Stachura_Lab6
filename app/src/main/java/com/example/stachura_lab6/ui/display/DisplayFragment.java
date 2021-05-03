package com.example.stachura_lab6.ui.display;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.stachura_lab6.MainActivity;
import com.example.stachura_lab6.PortfolioViewModel;
import com.example.stachura_lab6.R;
import com.example.stachura_lab6.db.DatabaseOperations;
import com.example.stachura_lab6.db.Stock;
import com.example.stachura_lab6.ui.update.UpdateFragment;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DisplayFragment extends Fragment {

    private PortfolioViewModel portfolioViewModel;
    private View view;
    private ArrayList<Stock> stocks;
    private Observable<Stock> observable;
    public static com.example.stachura_lab6.ui.display.DisplayFragment newInstance() {
        return new DisplayFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.display_fragment, container, false);
        view = v;
        Button back_button = v.findViewById(R.id.backButton);
        Bundle b = this.getArguments();
        portfolioViewModel =  ((MainActivity) getActivity()).portfolioViewModel;
        stocks = (ArrayList<Stock>) ((MainActivity) getActivity()).portfolioViewModel.getAllStocks().getValue();
        TextView tv = v.findViewById(R.id.stock_info_extra);
        String name_selected = b.getString("nameStock");
        String to_display = getDisplayString(name_selected);
        tv.setText(to_display);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_stocks);
            }
        });
        Button update_button = v.findViewById(R.id.updateButton);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateFragment uf = new UpdateFragment();
                Bundle b = new Bundle();
                b.putString("nameStock", name_selected);
                uf.setArguments(b);
                Navigation.findNavController(v).navigate(R.id.updateFragment, b);
            }
        });
        Button delete_button = v.findViewById(R.id.deleteButton);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Stock s : stocks){
                    if (s.name.equals(name_selected)){
                        s.databaseOperations = DatabaseOperations.DELETE;
                        observable = io.reactivex.Observable.just(s);
                        io.reactivex.Observer<Stock> observer = ((MainActivity) getActivity()).getStockObserver(s);
                        observable.observeOn(Schedulers.io()).subscribe(observer);
                    }
                }
            }
        });


        return v;
    }

    private String getDisplayString(String name_selected) {
        for (Stock s : stocks){

            if (s.name.equals(name_selected)){
                return "More on " + name_selected + ":\nCompany Name: " + s.fullName + "\nSector: " + s.sector + "\nPrice: " + s.price;
            }
        }
        return "Error, no Country Selected for " + name_selected;
    }

    }




