package com.pucmm.proyectofinal.roomviewmodel.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.pucmm.proyectofinal.roomviewmodel.database.UserDao;
import com.pucmm.proyectofinal.roomviewmodel.models.User;

import java.util.List;

public class UserViewModel extends ViewModel {

    private LiveData<List<User>> userListLiveData;

    public UserViewModel(@NonNull UserDao userDao) {
        userListLiveData = userDao.findAll();
    }

    public LiveData<List<User>> getUserListLiveData() { return userListLiveData; }
}
