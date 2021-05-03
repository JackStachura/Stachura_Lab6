package com.example.stachura_lab6;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.stachura_lab6.db.Stock;
import com.example.stachura_lab6.ui.stocks.StocksFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    public PortfolioViewModel portfolioViewModel;
    private LiveData<List<Stock>> allStocks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        portfolioViewModel = new ViewModelProvider(this).get(PortfolioViewModel.class);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_stocks, R.id.navigation_Create, R.id.navigation_settings, R.id.updateFragment, R.id.displayFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        portfolioViewModel.getAllStocks().observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {

                for (Stock stock : stocks) {
                    if (!portfolioViewModel.getAllStocks().getValue().contains(stock)){
                        portfolioViewModel.getAllStocks().getValue().add(stock);

                    }
                }
                //do the navigation here so the RecyclerView is updated at the right time.
                navController.navigate(R.id.navigation_stocks);

            }
        });

        navController.navigate(R.id.navigation_Create);

    }
    public boolean isStockInDatabase_faster(String name) {
        //simple boolean check to test whether a stock already exists in the database.

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
    public io.reactivex.Observer<Stock> getStockObserver(Stock stock) { // OBSERVER
        //Uses databaseOperations field present on the parameter stock object to construct
        //an io.reactivex.Observer object. Oberrides the onNext() method based on the type of operation.
        return new io.reactivex.Observer<Stock>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("DB", "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Stock stock) {
                switch(stock.databaseOperations) {
                    case INSERT:
                        if (!isStockInDatabase_faster(stock.name)) {
                            portfolioViewModel.getPortfolioDatabase().stockDao().insert(stock);
                        }
                        break;
                    case DELETE:
                        portfolioViewModel.getPortfolioDatabase().stockDao().delete(stock);
                        break;
                    case UPDATE:
                        Log.i("DB", "Update");
                        portfolioViewModel.getPortfolioDatabase().stockDao().update(stock);
                        break;
                    default:
                        Log.i("DB", "Default");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("DB", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("DB", "All items are emitted!");


            }
        };
    }
    public io.reactivex.Observer<String> getDeleteAllObserver(String s) { // OBSERVER
        //Creates an io.reactivex.Observer object that observes a blank string.
        //This is because we can overwrite the onNext() method to perform the deleteAll()
        //and use a similar code structure in the caller to access the Scheduler and prevent
        //a runtime error due to the database update.
        return new io.reactivex.Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("DB", "onSubscribe");
            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull String s) {
                portfolioViewModel.getPortfolioDatabase().stockDao().deleteAll();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("DB", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("DB", "All items are emitted!");


            }
        };
    }
}

