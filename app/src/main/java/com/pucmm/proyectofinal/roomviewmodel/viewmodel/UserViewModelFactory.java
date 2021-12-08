package com.pucmm.proyectofinal.roomviewmodel.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pucmm.proyectofinal.roomviewmodel.database.UserDao;

import org.jetbrains.annotations.NotNull;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private UserDao userDao;

    public UserViewModelFactory(UserDao userDao) {
        this.userDao = userDao;
    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(userDao);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel Class");
        }
    }
}
