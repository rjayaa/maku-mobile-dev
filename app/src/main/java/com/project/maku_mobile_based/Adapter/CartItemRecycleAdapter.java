package com.project.maku_mobile_based.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.maku_mobile_based.OnChangeQuantity;
import com.project.maku_mobile_based.R;
import com.project.maku_mobile_based.model.Food;

import java.util.ArrayList;

public class CartItemRecycleAdapter extends RecyclerView.Adapter<CartItemRecycleAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Food> cartitems;
    private OnChangeQuantity listener;

    public CartItemRecycleAdapter(Context context, ArrayList<Food> cartitems, OnChangeQuantity listener) {
        this.context = context;
        this.cartitems = cartitems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartItemRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemRecycleAdapter.ViewHolder holder, int position) {
        Food food = cartitems.get(position);
        holder.textfoodname.setText(food.getFoodName());
        holder.textfooddesc.setText(food.getFoodDesc());
        holder.textfoodprice.setText(food.getFoodPrice());
        holder.textQuantity.setText(String.valueOf(food.getQuantity()));
        Glide.with(context).load(food.getFoodUrlImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cartitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Button btnDecrease, btnIncrease;

        ImageView imageView;
        TextView textfoodname, textfooddesc, textfoodprice, textQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            textQuantity =itemView.findViewById(R.id.txtQuantity);
            textfoodname = itemView.findViewById(R.id.txtfoodname);
            textfooddesc = itemView.findViewById(R.id.txtfooddesc);
            textfoodprice = itemView.findViewById(R.id.txtfoodprice);
            imageView = itemView.findViewById(R.id.imageFood);

            btnIncrease.setOnClickListener(v -> {
                try {
                    int position = getAdapterPosition();
                    Food food = cartitems.get(position);
                    food.setQuantity(food.getQuantity() + 1);
                    textQuantity.setText(String.valueOf(food.getQuantity()));
                    listener.onQuantityChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            btnDecrease.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Food food = cartitems.get(position);
                if (food.getQuantity() > 0) {
                    food.setQuantity(food.getQuantity() - 1);
                    textQuantity.setText(String.valueOf(food.getQuantity()));
                    listener.onQuantityChanged();
                }
            });




        }
    }
}
