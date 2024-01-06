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
import com.project.maku_mobile_based.Activity.HistoryOrdersActivity;
import com.project.maku_mobile_based.OnChangeQuantity;
import com.project.maku_mobile_based.R;
import com.project.maku_mobile_based.model.Food;
import com.project.maku_mobile_based.model.OrderItem;
import com.project.maku_mobile_based.model.Orders;

import java.util.ArrayList;

public class HistoryItemRecycleAdapter extends RecyclerView.Adapter<HistoryItemRecycleAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Orders> ordersList;
    private OnChangeQuantity listener;

    public HistoryItemRecycleAdapter(Context context, ArrayList<Orders> ordersList, OnChangeQuantity listener) {
        this.context = context;
        this.ordersList = ordersList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistoryItemRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_item, parent, false);
        return new HistoryItemRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemRecycleAdapter.ViewHolder holder, int position) {
        Orders order = ordersList.get(position);

        holder.txtOrderId.setText("Order ID: #" + order.getOrderId());
        holder.txtOrderStatus.setText("Status: " + order.getOrderStatus());
        holder.txtUsername.setText("Username: " + order.getUsername());
        holder.txtLocation.setText("Location: " + order.getLocation());
        holder.txtTotalPrice.setText("Total Price: " + order.getTotalPrice());
        holder.txtPhoneNumber.setText("Phone: " + order.getPhoneNumber());

        // Menggabungkan informasi makanan
        StringBuilder foodItemsBuilder = new StringBuilder();
        for (OrderItem item : order.getOrderItems()) {
            foodItemsBuilder.append(item.getFoodName()).append(" - Rp ").append(item.getFoodPrice()).append("\n");
        }
        holder.txtOrderItems.setText("Items:\n" + foodItemsBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId, txtOrderStatus, txtUsername, txtLocation, txtOrderItems, txtTotalPrice, txtPhoneNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtOrderItems = itemView.findViewById(R.id.txtOrderItems);
            txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
        }
    }
}
