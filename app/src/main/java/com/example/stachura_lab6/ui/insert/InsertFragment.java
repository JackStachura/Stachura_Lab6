package com.example.stachura_lab6.ui.insert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.fragment.app.Fragment;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import com.example.stachura_lab6.MainActivity;
import com.example.stachura_lab6.PortfolioViewModel;
import com.example.stachura_lab6.R;
import com.example.stachura_lab6.db.DatabaseOperations;
import com.example.stachura_lab6.db.Stock;
import com.example.stachura_lab6.dialogs.AlreadyInDatabaseDialog;
import com.example.stachura_lab6.dialogs.PriceErrorDialog;


public class InsertFragment extends Fragment {

    private Button submit;
    private EditText symbol;
    private EditText fullName;
    private EditText price;
    private EditText sector;
    private PortfolioViewModel portfolioViewModel;
    private final String TAG = "I_F";
    private Observable<Stock> observable;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_insert,container, false);
        submit = root.findViewById(R.id.submitUpdateButton);
        symbol = root.findViewById(R.id.editTextStockSymbol);
        symbol.setHint(R.string.Symbol_label);
        fullName = root.findViewById(R.id.editTextFullName);
        fullName.setHint(R.string.Full_label);
        price = root.findViewById(R.id.editTextPrice);
        price.setHint(R.string.Price_label);
        sector = root.findViewById(R.id.editTextSector);
        sector.setHint(R.string.Sector_label);
        portfolioViewModel = ((MainActivity) getActivity()).portfolioViewModel;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sym = symbol.getText().toString();
                try {
                    double pri = Double.valueOf(price.getText().toString());
                    String full = fullName.getText().toString();
                    String sect = sector.getText().toString();
                    Stock stock = new Stock(sym, pri, full, sect);
                    if (((MainActivity) getActivity()).isStockInDatabase_faster(stock.name)){
                        inDataBaseAlert();
                        return;
                    }

                    stock.databaseOperations = DatabaseOperations.INSERT;
                    observable = io.reactivex.Observable.just(stock);
                    io.reactivex.Observer<Stock> observer = ((MainActivity) getActivity()).getStockObserver(stock);
                    observable.observeOn(Schedulers.io()).subscribe(observer);
                } catch (NumberFormatException e){
                    new PriceErrorDialog().show(getActivity().getSupportFragmentManager(), TAG);
                }

            }
        });
        return root;
    }
    private boolean isStockInDatabase_faster(String name) {
        boolean inDB = false;
        if (portfolioViewModel.getAllStocks().getValue() == null){
            return false;
        }
        for (Stock stock : portfolioViewModel.getAllStocks().getValue()) {
            if (name.equals(stock.name)) {
                inDB = true;
                break;
            }
        }

        return inDB;
    }
    private void inDataBaseAlert() {
        new AlreadyInDatabaseDialog().show(getActivity().getSupportFragmentManager(), TAG);
    }

}