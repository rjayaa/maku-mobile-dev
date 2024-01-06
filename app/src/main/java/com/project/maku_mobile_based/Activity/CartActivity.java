package com.project.maku_mobile_based.Activity;

import androidx.annotation.NonNull;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.project.maku_mobile_based.Adapter.CartItemRecycleAdapter;
import com.project.maku_mobile_based.Adapter.FoodRecycleAdapter;
import com.project.maku_mobile_based.OnChangeQuantity;
import com.project.maku_mobile_based.R;
import com.project.maku_mobile_based.model.Food;
import com.project.maku_mobile_based.model.OrderItem;
import com.project.maku_mobile_based.model.Orders;
import com.project.maku_mobile_based.model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartActivity extends AppCompatActivity implements OnChangeQuantity {

    private ArrayList<Food> cartItems;
    private RecyclerView recyclerView;
    private CartItemRecycleAdapter cartItemRecycleAdapter;
    private Context context;
    private DatabaseReference databaseReference;
    private int lastOrderId;
    private Button btnshowBackButton,btnCancelOrder,btnCheckoutOrder;
    private EditText deliveryLocation;
    private TextView totalPrice;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();


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
        deliveryLocation = findViewById(R.id.txtInputLocation);


        cartItems = getIntent().getParcelableArrayListExtra("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        cartItemRecycleAdapter = new CartItemRecycleAdapter(this, cartItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartItemRecycleAdapter);

        updateTotalPrice();
        updateCartVisibility();
        initializeOrderId();
        btnshowBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, MenuActivity.class);
            startActivity(intent);
        });
        btnCancelOrder.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        btnCheckoutOrder.setOnClickListener(v -> {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        // Membuat OrderItems dari cartItems
                        List<OrderItem> orderItems = new ArrayList<>();
                        for (Food food : cartItems) {
                            orderItems.add(new OrderItem(food.getFoodName(), (int) Double.parseDouble(food.getFoodPrice())));
                        }

                        // Menentukan ID order dan status order
                        int orderId = generateOrderId();
                        String orderStatus = "pending";

                        // Membersihkan format total harga
                        String cleanNumber = totalPrice.getText().toString().replaceAll("[^\\d.,]", "");

                        // Membuat objek Orders
                        Orders order = new Orders(orderId, orderItems, orderStatus, user.getFullName(), deliveryLocation.getText().toString(), cleanNumber, user.getPhoneNumber());

                        // Menyimpan ke Firebase
                        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
                        ordersRef.child("order_" + orderId).setValue(order)
                                .addOnSuccessListener(aVoid -> {
                                    // Berhasil menyimpan order
                                    Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                                    intent.putExtra("totalPrice", cleanNumber);
                                    startActivity(intent);
                                })
                                .addOnFailureListener(e -> {
                                    // Gagal menyimpan order
                                    Log.e("CartActivity", "Failed to save order", e);
                                });
                        saveNewOrderId();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("CartActivity", "Error loading user data", databaseError.toException());
                }
            });
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
        totalPrice.setText("Total: Rp " + String.format("%,.2f", total));
    }

    private void initializeOrderId() {
        databaseReference = FirebaseDatabase.getInstance().getReference("orders");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int maxId = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    if (key != null && key.startsWith("order_")) {
                        try {
                            int id = Integer.parseInt(key.substring(6)); // Mengambil angka dari "order_x"
                            if (id > maxId) {
                                maxId = id;
                            }
                        } catch (NumberFormatException e) {
                            Log.e("CartActivity", "Error parsing order ID: " + key, e);
                        }
                    }
                }
                lastOrderId = maxId; // Setelah loop, maxId adalah ID order tertinggi
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CartActivity", "Error loading orders", error.toException());
            }
        });
    }


    private int generateOrderId() {
        return ++lastOrderId;
    }

    private void saveNewOrderId() {
        databaseReference.child("lastorderId").setValue(lastOrderId);
    }



}