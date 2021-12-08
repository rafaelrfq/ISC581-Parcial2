package com.pucmm.proyectofinal.roomviewmodel.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.pucmm.proyectofinal.R;
import com.pucmm.proyectofinal.databinding.ActivityEditRoomBinding;
import com.pucmm.proyectofinal.roomviewmodel.database.AppDataBase;
import com.pucmm.proyectofinal.roomviewmodel.database.AppExecutors;
import com.pucmm.proyectofinal.roomviewmodel.database.UserDao;
import com.pucmm.proyectofinal.roomviewmodel.models.User;

public class EditRoomActivity extends AppCompatActivity {

    private ActivityEditRoomBinding mBinding;
    private EditText name, email, username, password;
    private Button save;
    private int userId;
    private Intent intent;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityEditRoomBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        userDao = AppDataBase.getInstance(getApplicationContext()).userDao();
        intent = getIntent();

        userId = intent.getIntExtra("update", -1);

        initViews();

        if (userId != -1) {
            save.setText("UPDATE");
            AppExecutors.getInstance().diskIO().execute(() -> {
                    User user = userDao.find(userId);
                    populateUI(user);
            });
        }
    }

    private void populateUI(User user) {
        if(user != null) {
            mBinding.editEmail.setText(user.getEmail());
            mBinding.editName.setText(user.getName());
            mBinding.editUsername.setText(user.getUsername());
        }
    }

    private void initViews() {
        name = mBinding.editName;
        email = mBinding.editEmail;
        username = mBinding.editUsername;
        password = mBinding.editPassword;

        save = mBinding.save;
        save.setOnClickListener(v -> onSaveButtonClicked());
    }

    private void onSaveButtonClicked() {
        User user = new User(name.getText().toString().trim(),
                             email.getText().toString().trim(),
                             username.getText().toString().trim(),
                             password.getText().toString().trim());
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (intent.hasExtra("update")) {
                user.setId(userId);
                userDao.update(user);
            } else {
                userDao.insert(user);
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