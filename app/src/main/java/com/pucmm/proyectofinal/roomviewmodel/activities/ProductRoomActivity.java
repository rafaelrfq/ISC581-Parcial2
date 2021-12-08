package com.pucmm.proyectofinal.roomviewmodel.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.pucmm.proyectofinal.databinding.ActivityProductEditRoomBinding;
import com.pucmm.proyectofinal.roomviewmodel.database.AppDataBase;
import com.pucmm.proyectofinal.roomviewmodel.database.AppExecutors;
import com.pucmm.proyectofinal.roomviewmodel.database.ProductDao;
import com.pucmm.proyectofinal.roomviewmodel.models.Product;
import com.pucmm.proyectofinal.roomviewmodel.models.User;

import org.jetbrains.annotations.NotNull;

public class ProductRoomActivity extends AppCompatActivity {

    private ActivityProductEditRoomBinding mBinding;
    private EditText description, price;
    private Button save;
    private int productUuid;
    private Intent intent;
    private ProductDao productDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProductEditRoomBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        productDao = AppDataBase.getInstance(getApplicationContext()).productDao();
        intent = getIntent();

        productUuid = intent.getIntExtra("update", -1);

        initViews();

        if (productUuid != -1) {
            save.setText("UPDATE");
            AppExecutors.getInstance().diskIO().execute(() -> {
                Product product = productDao.find(productUuid);
                populateUI(product);
            });
        }
    }

    private void populateUI(Product product) {
        if(product != null) {
            mBinding.editDescription.setText(product.getDescription());
            mBinding.editPrice.setText(Double.toString(product.getPrice()));
        }
    }

    private void initViews() {
        description = mBinding.editDescription;
        price = mBinding.editPrice;

        save = mBinding.saveProduct;
        save.setOnClickListener(v -> onSaveButtonClicked());
    }

    private void onSaveButtonClicked() {
        Product product = new Product(description.getText().toString().trim(),
                Double.parseDouble(price.getText().toString().trim()));
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (intent.hasExtra("update")) {
                product.setUuid(productUuid);
                productDao.update(product);
            } else {
                productDao.insert(product);
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