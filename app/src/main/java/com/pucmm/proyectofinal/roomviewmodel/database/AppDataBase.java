package com.pucmm.proyectofinal.roomviewmodel.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.pucmm.proyectofinal.roomviewmodel.models.Category;
import com.pucmm.proyectofinal.roomviewmodel.models.Product;
import com.pucmm.proyectofinal.roomviewmodel.models.User;

@Database(entities = {User.class, Product.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "proyecto_final";
    private static final Object LOCK = new Object();
    private static AppDataBase _instance;

    public static AppDataBase getInstance(Context context) {
        if (_instance == null) {
            synchronized (LOCK) {
                _instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DATABASE_NAME).build();
            }
        }
        return _instance;
    }

    public abstract UserDao userDao();
    public abstract ProductDao productDao();
    public abstract CategoryDao categoryDao();
}
