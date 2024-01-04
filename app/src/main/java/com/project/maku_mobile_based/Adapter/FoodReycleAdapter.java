package com.project.maku_mobile_based.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.maku_mobile_based.OnChangeQuantity;
import com.project.maku_mobile_based.R;
import com.project.maku_mobile_based.model.Food;


public class FoodReycleAdapter extends RecyclerView.Adapter<FoodReycleAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Food> foodlist;
    private OnChangeQuantity listener;
    public FoodReycleAdapter(Context context, ArrayList<Food> foodlist, OnChangeQuantity listener) {
        this.context = context;
        this.foodlist = foodlist;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodReycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tenant_food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodReycleAdapter.ViewHolder holder, int position) {
        holder.textfoodname.setText(foodlist.get(position).getFoodName().toString());
        holder.textfoodprice.setText(foodlist.get(position).getFoodPrice().toString());
        Glide.with(context).load(foodlist.get(position).getFoodUrlImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnDecrease, btnIncrease;

        ImageView imageView;
        TextView textfoodname, textfoodprice, textQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDecrease = itemView.findViewById(R.id.buttonDecrease);
            btnIncrease = itemView.findViewById(R.id.buttonIncrease);
            textQuantity =itemView.findViewById(R.id.txtQuantity);
            textfoodname = itemView.findViewById(R.id.txtfoodname);
            textfoodprice = itemView.findViewById(R.id.txtfoodprice);
            imageView = itemView.findViewById(R.id.imageFood);

            btnIncrease.setOnClickListener(v->{
                int position = getAdapterPosition();
                Food food = foodlist.get(position);
                food.setQuantity(food.getQuantity()+1);
                textQuantity.setText(String.valueOf(food.getQuantity()));
                listener.onQuantityChanged();

            });

            btnDecrease.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Food food = foodlist.get(position);
                if (food.getQuantity() > 0) {
                    food.setQuantity(food.getQuantity() - 1);
                    textQuantity.setText(String.valueOf(food.getQuantity()));
                    listener.onQuantityChanged();
                }
            });



        }
    }
}
