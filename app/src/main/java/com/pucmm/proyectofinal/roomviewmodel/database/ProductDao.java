package com.pucmm.proyectofinal.roomviewmodel.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pucmm.proyectofinal.roomviewmodel.models.Product;

import java.util.List;

@Dao
public interface ProductDao {
    // CRUD
    @Query("SELECT * FROM product ORDER BY uuid")
    LiveData<List<Product>> findAll();

    @Query("SELECT * FROM product WHERE uuid = :uuid")
    Product find(int uuid);

    @Insert
    void insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);
}
