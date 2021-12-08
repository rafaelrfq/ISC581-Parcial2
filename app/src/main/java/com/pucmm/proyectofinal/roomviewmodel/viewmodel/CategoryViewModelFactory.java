package com.pucmm.proyectofinal.roomviewmodel.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pucmm.proyectofinal.roomviewmodel.database.CategoryDao;

import org.jetbrains.annotations.NotNull;

public class CategoryViewModelFactory implements ViewModelProvider.Factory {

    private CategoryDao categoryDao;

    public CategoryViewModelFactory(CategoryDao categoryDao) { this.categoryDao = categoryDao; }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
            return (T) new CategoryViewModel(categoryDao);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
