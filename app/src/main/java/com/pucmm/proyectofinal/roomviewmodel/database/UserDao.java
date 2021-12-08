package com.pucmm.proyectofinal.roomviewmodel.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pucmm.proyectofinal.roomviewmodel.models.User;

import java.util.List;

@Dao
public interface UserDao {
    // CRUD
    @Query("SELECT * FROM user ORDER BY id")
    LiveData<List<User>> findAll();

    @Query("SELECT * FROM user WHERE id = :id")
    User find(int id);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete (User user);
}
