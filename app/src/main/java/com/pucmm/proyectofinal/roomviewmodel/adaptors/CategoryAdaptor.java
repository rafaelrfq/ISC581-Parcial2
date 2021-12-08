package com.pucmm.proyectofinal.roomviewmodel.adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pucmm.proyectofinal.databinding.CategoryItemBinding;
import com.pucmm.proyectofinal.roomviewmodel.activities.CategoryMainActivity;
import com.pucmm.proyectofinal.roomviewmodel.activities.ProductRoomActivity;
import com.pucmm.proyectofinal.roomviewmodel.models.Category;
import com.pucmm.proyectofinal.roomviewmodel.models.Product;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.List;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.MyViewHolder> {

    private Context mContext;
    private List<Category> mCategoryList;
    private CategoryItemBinding mBinding;

    public CategoryAdaptor(Context context) { this.mContext = context; }

    @NonNull
    @NotNull
    @Override
    public CategoryAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        mBinding = CategoryItemBinding.inflate(layoutInflater, parent, false);

        return new MyViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdaptor.MyViewHolder holder, int position) {
        final Category category = mCategoryList.get(position);
        holder.name.setText(category.getName());
        try {
            holder.image.setImageBitmap(BitmapFactory.decodeByteArray(category.getImage(),0,category.getImage().length));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(mCategoryList == null) {
            return 0;
        }
        return mCategoryList.size();
    }

    public void setCategories(List<Category> categories) {
        this.mCategoryList = categories;
        notifyDataSetChanged();
    }

    public Category getCategory(int position) { return mCategoryList.get(position); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;
        private ImageView saveBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = mBinding.categoryName;
            image = mBinding.categoryImage;

            saveBtn = mBinding.editImage;
            saveBtn.setOnClickListener(view -> {
                int id = mCategoryList.get(getAdapterPosition()).getId();

                Intent intent = new Intent(mContext, CategoryMainActivity.class);
                intent.putExtra("update", id);
                mContext.startActivity(intent);
            });
        }
    }
}
