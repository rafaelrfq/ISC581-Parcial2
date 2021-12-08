package com.pucmm.proyectofinal.roomviewmodel.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pucmm.proyectofinal.roomviewmodel.models.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    // CRUD
    @Query("SELECT * FROM category ORDER BY id")
    LiveData<List<Category>> findAll();

    @Query("SELECT * FROM category WHERE id = :id")
    Category find(int id);

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);
}
