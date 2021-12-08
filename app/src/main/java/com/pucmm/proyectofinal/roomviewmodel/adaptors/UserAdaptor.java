package com.pucmm.proyectofinal.roomviewmodel.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pucmm.proyectofinal.databinding.UserItemBinding;
import com.pucmm.proyectofinal.roomviewmodel.activities.EditRoomActivity;
import com.pucmm.proyectofinal.roomviewmodel.models.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.MyViewHolder> {

    private Context mContext;
    private List<User> mUserList;
    private UserItemBinding mBinding;

    public UserAdaptor(Context context) { this.mContext = context; }

    @NonNull
    @NotNull
    @Override
    public UserAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        mBinding = UserItemBinding.inflate(layoutInflater, parent, false);

        return new MyViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdaptor.MyViewHolder holder, int position) {
        final User user = mUserList.get(position);
        holder.email.setText(user.getEmail());
        holder.name.setText(user.getName());
        holder.username.setText(user.getUsername());
    }

    @Override
    public int getItemCount() {
        if (mUserList == null) {
            return 0;
        }
        return mUserList.size();
    }

    public void setUsers(List<User> users) {
        this.mUserList = users;
        notifyDataSetChanged();
    }

    public User getUser(int position) { return mUserList.get(position); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView email, name, username;
        private ImageView saveBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = mBinding.userEmail;
            name = mBinding.userName;
            username = mBinding.userUsername;

            saveBtn = mBinding.editImage;
            saveBtn.setOnClickListener(view -> {
                int id = mUserList.get(getAdapterPosition()).getId();

                Intent intent = new Intent(mContext, EditRoomActivity.class);
                intent.putExtra("update", id);
                mContext.startActivity(intent);
            });
        }
    }
}
