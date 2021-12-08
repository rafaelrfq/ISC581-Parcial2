package com.pucmm.proyectofinal.roomviewmodel.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pucmm.proyectofinal.roomviewmodel.database.ProductDao;

import org.jetbrains.annotations.NotNull;

public class ProductViewModelFactory implements ViewModelProvider.Factory {

    private ProductDao productDao;

    public ProductViewModelFactory(ProductDao productDao) { this.productDao = productDao; }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProductViewModel.class)) {
            return (T) new ProductViewModel(productDao);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel Class");
        }
    }
}