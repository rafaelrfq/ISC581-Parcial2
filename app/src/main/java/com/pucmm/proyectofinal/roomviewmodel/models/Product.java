package com.pucmm.proyectofinal.roomviewmodel.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class Product {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uuid")
    private int uuid;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "price")
    private double price;

    @Ignore
    public Product(String description, double price) {
        this.description = description;
        this.price = price;
    }

    public Product(int uuid, String description, double price) {
        this.uuid = uuid;
        this.description = description;
        this.price = price;
    }

    public int getUuid() { return uuid; }

    public void setUuid(int uuid) { this.uuid = uuid; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }
}
