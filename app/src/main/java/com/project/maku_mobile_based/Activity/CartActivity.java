package com.project.maku_mobile_based.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


    private Button btnshowBackButton,btnCancelOrder,btnCheckoutOrder;
    private EditText deliveryLocation;
    private TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnshowBackButton = findViewById(R.id.btnShowCart);
        deliveryLocation = findViewById(R.id.txtInputLocation);
        totalPrice = findViewById(R.id.txtTotalPrice);
        btnCancelOrder = findViewById(R.id.btnCancelOrder);
        btnCheckoutOrder = findViewById(R.id.btnCheckoutOrder);
        recyclerView = findViewById(R.id.recyclecart);

        cartItems = getIntent().getParcelableArrayListExtra("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        cartItemRecycleAdapter = new CartItemRecycleAdapter(this, cartItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartItemRecycleAdapter);

        updateTotalPrice();
        updateCartVisibility();
        btnshowBackButton.setOnClickListener(v->{
            Intent intent = new Intent(CartActivity.this, MenuActivity.class);
            startActivity(intent);
        });
        btnCancelOrder.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, MenuActivity.class);
            startActivity(intent);
        });



    }

    @Override
    public void onQuantityChanged() {
        // Menggunakan Iterator untuk menghindari ConcurrentModificationException saat menghapus item
        Iterator<Food> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            Food food = iterator.next();
            if (food.getQuantity() == 0) {
                iterator.remove(); // Menghapus item dari daftar
            }
        }
        cartItemRecycleAdapter.notifyDataSetChanged(); // Memperbarui adapter setelah menghapus item
        updateTotalPrice();
        updateCartVisibility();
    }

    private void updateCartVisibility() {
        Log.d("CartActivity", "Cart items size: " + cartItems.size());
        if (cartItems.isEmpty()) {
            btnshowBackButton.setVisibility(View.VISIBLE);
            deliveryLocation.setVisibility(View.GONE);
            totalPrice.setVisibility(View.GONE);
            btnCancelOrder.setVisibility(View.GONE);
            btnCheckoutOrder.setVisibility(View.GONE);
        } else {
            btnshowBackButton.setVisibility(View.GONE);
            deliveryLocation.setVisibility(View.VISIBLE);
            totalPrice.setVisibility(View.VISIBLE);
            btnCancelOrder.setVisibility(View.VISIBLE);
            btnCheckoutOrder.setVisibility(View.VISIBLE);
        }
    }

    private void updateTotalPrice() {
        Log.d("CartActivity", "updateTotalPrice called");
        double total = 0;
        for (Food food : cartItems) {
            try {
                double price = Double.parseDouble(food.getFoodPrice());
                int quantity = food.getQuantity();
                total += price * quantity;
            } catch (NumberFormatException e) {
                Log.e("CartActivity", "Invalid price format: " + food.getFoodPrice(), e);

            }
        }
        totalPrice.setText("Total: Rp" + String.format("%,.2f", total));
    }






}