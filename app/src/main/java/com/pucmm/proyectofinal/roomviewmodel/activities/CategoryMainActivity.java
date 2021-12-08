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
import com.pucmm.proyectofinal.databinding.ActivityCategoryMainBinding;
import com.pucmm.proyectofinal.roomviewmodel.adaptors.CategoryAdaptor;
import com.pucmm.proyectofinal.roomviewmodel.database.AppDataBase;
import com.pucmm.proyectofinal.roomviewmodel.database.AppExecutors;
import com.pucmm.proyectofinal.roomviewmodel.database.CategoryDao;
import com.pucmm.proyectofinal.roomviewmodel.models.Category;
import com.pucmm.proyectofinal.roomviewmodel.models.Product;
import com.pucmm.proyectofinal.roomviewmodel.viewmodel.CategoryViewModel;
import com.pucmm.proyectofinal.roomviewmodel.viewmodel.CategoryViewModelFactory;
import com.pucmm.proyectofinal.roomviewmodel.viewmodel.ProductViewModel;
import com.pucmm.proyectofinal.roomviewmodel.viewmodel.ProductViewModelFactory;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoryMainActivity extends AppCompatActivity {

    private ActivityCategoryMainBinding mBinding;
    private FloatingActionButton categoryFloatingActionButton;
    private RecyclerView mReciclerView;
    private CategoryAdaptor mCategoryAdaptor;
    private CategoryDao categoryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCategoryMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        categoryDao = AppDataBase.getInstance(getApplicationContext()).categoryDao();

        categoryFloatingActionButton = mBinding.addCategoryFAB;
        categoryFloatingActionButton.setOnClickListener(view -> startActivity(new Intent(CategoryMainActivity.this, CategoryRoomActivity.class)));

        mReciclerView = mBinding.recyclerView;

        mReciclerView.setLayoutManager(new LinearLayoutManager(this));

        mCategoryAdaptor = new CategoryAdaptor(this);
        mReciclerView.setAdapter(mCategoryAdaptor);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(() -> {
                    final int position = viewHolder.getBindingAdapterPosition();
                    final Category category = mCategoryAdaptor.getCategory(position);
                });
            }
        });

        touchHelper.attachToRecyclerView(mReciclerView);
        retrieveTasks();
    }

    private void retrieveTasks() {
        CategoryViewModel categoryViewModel = new ViewModelProvider(this, new CategoryViewModelFactory(categoryDao)).get(CategoryViewModel.class);

        categoryViewModel.getCategoryListLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) { mCategoryAdaptor.setCategories(categories); }
        });
    }
}