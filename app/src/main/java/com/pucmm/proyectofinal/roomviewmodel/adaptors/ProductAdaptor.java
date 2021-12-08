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

import com.pucmm.proyectofinal.databinding.ProductItemBinding;
import com.pucmm.proyectofinal.roomviewmodel.activities.ProductRoomActivity;
import com.pucmm.proyectofinal.roomviewmodel.models.Product;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductAdaptor extends RecyclerView.Adapter<ProductAdaptor.MyViewHolder> {

    private Context mContext;
    private List<Product> mProductList;
    private ProductItemBinding mBinding;

    public ProductAdaptor(Context context) { this.mContext = context; }

    @NonNull
    @NotNull
    @Override
    public ProductAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        mBinding = ProductItemBinding.inflate(layoutInflater, parent, false);

        return new MyViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdaptor.MyViewHolder holder, int position) {
        final Product product = mProductList.get(position);
        holder.description.setText(product.getDescription());
        holder.price.setText(Double.toString(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        if(mProductList == null) {
            return 0;
        }
        return mProductList.size();
    }

    public void setProducts(List<Product> products) {
        this.mProductList = products;
        notifyDataSetChanged();
    }

    public Product getProduct(int position) { return mProductList.get(position); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView description, price;
        private ImageView saveBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            description = mBinding.productDescription;
            price = mBinding.productPrice;

            saveBtn = mBinding.editImage;
            saveBtn.setOnClickListener(view -> {
                int uuid = mProductList.get(getAdapterPosition()).getUuid();

                Intent intent = new Intent(mContext, ProductRoomActivity.class);
                intent.putExtra("update", uuid);
                mContext.startActivity(intent);
            });
        }
    }
}
