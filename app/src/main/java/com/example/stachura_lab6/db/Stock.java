package com.example.stachura_lab6.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Stock {

    public Stock(@NonNull String name, double price, String fullName, String sector) {
        this.name = name;
        this.price = price;
        this.fullName = fullName;
        this.sector = sector;
    }

    @PrimaryKey @NonNull
    public String name;

    @ColumnInfo
    public double price;

    @ColumnInfo
    public String fullName;

    @ColumnInfo
    public String sector;

    @Ignore
    public DatabaseOperations databaseOperations;
}
