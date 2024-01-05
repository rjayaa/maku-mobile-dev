package com.project.maku_mobile_based.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.project.maku_mobile_based.Adapter.CartItemRecycleAdapter;
import com.project.maku_mobile_based.Adapter.FoodRecycleAdapter;
import com.project.maku_mobile_based.OnChangeQuantity;
import com.project.maku_mobile_based.R;
import com.project.maku_mobile_based.model.Food;
import java.util.ArrayList;
import java.util.Iterator;

public class CartActivity extends AppCompatActivity implements OnChangeQuantity {

    private ArrayList<Food> cartItems;
    private RecyclerView recyclerView;
    private CartItemRecycleAdapter cartItemRecycleAdapter;
    private Context context;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        cartItems = getIntent().getParcelableArrayListExtra("cartItems"); // Perbaikan di sini
        if (cartItems != null) {
            // Tampilkan item keranjang
            recyclerView = findViewById(R.id.recyclecart); // Ganti dengan ID RecyclerView Anda
            cartItemRecycleAdapter = new CartItemRecycleAdapter(this, cartItems, this); // Asumsi FoodReycleAdapter bisa menangani ArrayList<Food>
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(cartItemRecycleAdapter);
        }

    }

    @Override
    public void onQuantityChanged() {
//        updateCartItems();
        // Menggunakan Iterator untuk menghindari ConcurrentModificationException saat menghapus item
        Iterator<Food> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            Food food = iterator.next();
            if (food.getQuantity() == 0) {
                iterator.remove(); // Menghapus item dari daftar
            }
        }
        cartItemRecycleAdapter.notifyDataSetChanged(); // Memperbarui adapter setelah menghapus item
    }


    private void clearAll() {
        if (cartItems != null) {
            cartItems.clear();
            if (cartItemRecycleAdapter != null) {
                cartItemRecycleAdapter.notifyDataSetChanged();
            }
        }
        cartItems = new ArrayList<>();
    }


    private void updateCartItems() {
        cartItems.clear();
        for (Food food : cartItems) {
            if (food.getQuantity() > 0) {
                cartItems.add(food);
            }
        }
    }
}