package com.pucmm.proyectofinal.roomviewmodel.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.pucmm.proyectofinal.roomviewmodel.database.CategoryDao;
import com.pucmm.proyectofinal.roomviewmodel.models.Category;

import java.util.List;

public class CategoryViewModel extends ViewModel {

    private LiveData<List<Category>> categoryListLiveData;

    public CategoryViewModel(@NonNull CategoryDao categoryDao) { categoryListLiveData = categoryDao.findAll(); }

    public LiveData<List<Category>> getCategoryListLiveData() { return categoryListLiveData; }
}
