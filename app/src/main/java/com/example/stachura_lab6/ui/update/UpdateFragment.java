package com.example.stachura_lab6.ui.update;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stachura_lab6.MainActivity;
import com.example.stachura_lab6.PortfolioViewModel;
import com.example.stachura_lab6.R;
import com.example.stachura_lab6.db.DatabaseOperations;
import com.example.stachura_lab6.db.Stock;
import com.example.stachura_lab6.dialogs.PriceErrorDialog;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class UpdateFragment extends Fragment {

    private Observable<Stock> observable;
    private PortfolioViewModel portfolioViewModel;
    private final String TAG = "U_F";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        portfolioViewModel = ((MainActivity) getActivity()).portfolioViewModel;
        View root = inflater.inflate(R.layout.fragment_update, container, false);
        EditText price_input = root.findViewById(R.id.editTextPrice2);
        price_input.setHint("New Price");
        EditText sector_input = root.findViewById(R.id.editTextSector2);
        sector_input.setHint("New Sector");
        EditText full_input = root.findViewById(R.id.editTextFullName2);
        full_input.setHint("New Company Name");
        Button submit = root.findViewById(R.id.submitUpdateButton);
        TextView tv = root.findViewById(R.id.update_title);
        String symbol = this.getArguments().getString("nameStock");
        tv.setText("Update " + symbol);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double price = Double.valueOf(price_input.getText().toString());
                    String sector = sector_input.getText().toString();
                    String full_name = full_input.getText().toString();
                    Stock s = new Stock(symbol, price, full_name, sector);
                    s.databaseOperations = DatabaseOperations.UPDATE;
                    observable = io.reactivex.Observable.just(s);
                    io.reactivex.Observer<Stock> observer = ((MainActivity)  getActivity()).getStockObserver(s);
                    observable.observeOn(Schedulers.io()).subscribe(observer);

                } catch (NumberFormatException e){
                    new PriceErrorDialog().show(getActivity().getSupportFragmentManager(), TAG);
                }



            }
        });

        return root;
    }


}
