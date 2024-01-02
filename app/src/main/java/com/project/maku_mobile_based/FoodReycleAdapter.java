package com.project.maku_mobile_based;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.maku_mobile_based.model.Food;

import org.w3c.dom.Text;

public class FoodReycleAdapter extends RecyclerView.Adapter<FoodReycleAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Food> foodlist;

    public FoodReycleAdapter(Context context, ArrayList<Food> foodlist) {
        this.context = context;
        this.foodlist = foodlist;
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

        ImageView imageView;
        TextView textfoodname, textfoodprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textfoodname = itemView.findViewById(R.id.txtfoodname);
            textfoodprice = itemView.findViewById(R.id.txtfoodprice);
            imageView = itemView.findViewById(R.id.imageFood);

        }
    }
}
