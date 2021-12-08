package com.pucmm.proyectofinal.roomviewmodel.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pucmm.proyectofinal.databinding.ActivityMainRoomBinding;
import com.pucmm.proyectofinal.databinding.ActivityProductMainBinding;
import com.pucmm.proyectofinal.roomviewmodel.adaptors.ProductAdaptor;
import com.pucmm.proyectofinal.roomviewmodel.database.AppDataBase;
import com.pucmm.proyectofinal.roomviewmodel.database.AppExecutors;
import com.pucmm.proyectofinal.roomviewmodel.database.ProductDao;
import com.pucmm.proyectofinal.roomviewmodel.models.Product;
import com.pucmm.proyectofinal.roomviewmodel.viewmodel.ProductViewModel;
import com.pucmm.proyectofinal.roomviewmodel.viewmodel.ProductViewModelFactory;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductMainActivity extends AppCompatActivity {

    private ActivityProductMainBinding mBinding;
    private FloatingActionButton productFloatingActionButton;
    private RecyclerView mRecyclerView;
    private ProductAdaptor mProductAdaptor;
    private ProductDao productDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProductMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        productDao = AppDataBase.getInstance(getApplicationContext()).productDao();

        productFloatingActionButton = mBinding.addProductFAB;
        productFloatingActionButton.setOnClickListener(view -> startActivity(new Intent(ProductMainActivity.this, ProductRoomActivity.class)));

        mRecyclerView = mBinding.recyclerView;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProductAdaptor = new ProductAdaptor(this);
        mRecyclerView.setAdapter(mProductAdaptor);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(() -> {
                    final int position = viewHolder.getBindingAdapterPosition();
                    final Product product = mProductAdaptor.getProduct(position);
                });
            }
        });

        touchHelper.attachToRecyclerView(mRecyclerView);
        retrieveTasks();
    }

    private void retrieveTasks() {
        ProductViewModel productViewModel = new ViewModelProvider(this, new ProductViewModelFactory(productDao)).get(ProductViewModel.class);

        productViewModel.getProductListLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) { mProductAdaptor.setProducts(products); }
        });
    }
}