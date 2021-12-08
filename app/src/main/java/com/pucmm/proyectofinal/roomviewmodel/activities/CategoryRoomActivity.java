package com.pucmm.proyectofinal.roomviewmodel.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.pucmm.proyectofinal.databinding.ActivityCategoryEditRoomBinding;
import com.pucmm.proyectofinal.databinding.ActivityCategoryMainBinding;
import com.pucmm.proyectofinal.databinding.ActivityProductEditRoomBinding;
import com.pucmm.proyectofinal.roomviewmodel.database.AppDataBase;
import com.pucmm.proyectofinal.roomviewmodel.database.AppExecutors;
import com.pucmm.proyectofinal.roomviewmodel.database.CategoryDao;
import com.pucmm.proyectofinal.roomviewmodel.models.Category;
import com.pucmm.proyectofinal.roomviewmodel.models.Product;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class CategoryRoomActivity extends AppCompatActivity {

    private ActivityCategoryEditRoomBinding mBinding;
    private EditText name;
    private ImageView image;
    private Button save;
    private int categoryId;
    private Intent intent;
    private CategoryDao categoryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCategoryEditRoomBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        categoryDao = AppDataBase.getInstance(getApplicationContext()).categoryDao();
        intent = getIntent();

        categoryId = intent.getIntExtra("update", -1);

        initViews();

        if (categoryId != -1) {
            save.setText("UPDATE");
            AppExecutors.getInstance().diskIO().execute(() -> {
                Category category = categoryDao.find(categoryId);
                populateUI(category);
            });
        }
    }

    private void populateUI(Category category) {
        if (category != null) {
            mBinding.categoryName.setText(category.getName());
            mBinding.categoryImage.setImageBitmap(BitmapFactory.decodeByteArray(category.getImage(),0,category.getImage().length));
        }
    }

    protected void initViews() {
        name = mBinding.categoryName;
        image = mBinding.categoryImage;

        save = mBinding.saveCategory;
        save.setOnClickListener(v -> onSaveButtonClicked());
    }

    private void onSaveButtonClicked() {
        Category category;
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();

        if (drawable == null) {
            category = new Category(name.getText().toString().trim());
        } else {
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,blob);
            byte[] bArray = blob.toByteArray();
            category = new Category(name.getText().toString().trim(), bArray);
        }

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (intent.hasExtra("update")) {
                category.setId(categoryId);
                categoryDao.update(category);
            } else {
                categoryDao.insert(category);
            }
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}