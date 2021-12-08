package com.pucmm.proyectofinal.roomviewmodel.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.pucmm.proyectofinal.R;
import com.pucmm.proyectofinal.databinding.ActivityMainRoomBinding;
import com.pucmm.proyectofinal.roomviewmodel.adaptors.UserAdaptor;
import com.pucmm.proyectofinal.roomviewmodel.database.AppDataBase;
import com.pucmm.proyectofinal.roomviewmodel.database.AppExecutors;
import com.pucmm.proyectofinal.roomviewmodel.database.UserDao;
import com.pucmm.proyectofinal.roomviewmodel.models.User;
import com.pucmm.proyectofinal.roomviewmodel.viewmodel.UserViewModel;
import com.pucmm.proyectofinal.roomviewmodel.viewmodel.UserViewModelFactory;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainRoomActivity extends AppCompatActivity {

    private ActivityMainRoomBinding mBinding;
    private FloatingActionButton userFloatingActionButton;
    private Button productActivityButton;
    private Button categoryActivityButton;
    private RecyclerView mRecyclerView;
    private UserAdaptor mUserAdaptor;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainRoomBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        userDao = AppDataBase.getInstance(getApplicationContext()).userDao();

        userFloatingActionButton = mBinding.addFAB;
        userFloatingActionButton.setOnClickListener(view -> startActivity(new Intent(MainRoomActivity.this, EditRoomActivity.class)));

        productActivityButton = mBinding.productActivityButton;
        productActivityButton.setOnClickListener(view -> startActivity(new Intent(MainRoomActivity.this, ProductMainActivity.class)));

        categoryActivityButton = mBinding.categoryActivityButton;
        categoryActivityButton.setOnClickListener(view -> startActivity(new Intent(MainRoomActivity.this, CategoryMainActivity.class)));

        mRecyclerView = mBinding.recyclerView;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUserAdaptor = new UserAdaptor(this);

        mRecyclerView.setAdapter(mUserAdaptor);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(() -> {
                    final int position = viewHolder.getBindingAdapterPosition();
                    final User user = mUserAdaptor.getUser(position);
                });
            }
        });

        touchHelper.attachToRecyclerView(mRecyclerView);
        retrieveTasks();
    }

    private void retrieveTasks() {
        UserViewModel userViewModel = new ViewModelProvider(this, new UserViewModelFactory(userDao)).get(UserViewModel.class);

        userViewModel.getUserListLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                mUserAdaptor.setUsers(users);
            }
        });
    }
}