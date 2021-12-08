package com.pucmm.proyectofinal.roomviewmodel.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @Ignore
    public Category(String name) {
        this.name = name;
    }

    @Ignore
    public Category(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public Category(int id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public byte[] getImage() { return image; }

    public void setImage(byte[] image) { this.image = image; }
}
