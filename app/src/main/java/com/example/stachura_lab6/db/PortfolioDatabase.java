package com.example.stachura_lab6.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Stock.class}, version = 1)
public abstract class PortfolioDatabase extends RoomDatabase {
    public abstract StockDao stockDao();
    private static PortfolioDatabase INSTANCE;

    public static synchronized PortfolioDatabase getInstance(Context context) {
        if (null == INSTANCE) {
            //Singleton Pattern
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    PortfolioDatabase.class, "portfolio_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };

}
