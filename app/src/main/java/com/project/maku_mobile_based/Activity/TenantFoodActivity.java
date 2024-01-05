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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.maku_mobile_based.Adapter.FoodRecycleAdapter;
import com.project.maku_mobile_based.OnChangeQuantity;
import com.project.maku_mobile_based.R;
import com.project.maku_mobile_based.model.Food;

import java.util.ArrayList;

public class TenantFoodActivity extends AppCompatActivity implements OnChangeQuantity {
    private RecyclerView recyclerView;
    private DatabaseReference database;
    private FoodRecycleAdapter foodRecycleAdapter;
    private ArrayList<Food> foodList;
    private Context context;
    private Button backBtn, btnShowCart;
    private ArrayList<Food> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_food_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        String name = getIntent().getStringExtra("TenantName");
        String image = getIntent().getStringExtra("IMAGE");
        String description = getIntent().getStringExtra("DESCRIPTION");
        TextView nameText = findViewById(R.id.txtTenantName);
        TextView descriptionText = findViewById(R.id.txtDescription);
        ImageView imageView = findViewById(R.id.tenantimage);
        backBtn = findViewById(R.id.backBtn);
        btnShowCart = findViewById(R.id.btnShowCart);
        foodRecycleAdapter = new FoodRecycleAdapter(getApplicationContext(),foodList,this);

        nameText.setText(name);
        descriptionText.setText(description);
        Glide.with(this).load(image).into(imageView);

        recyclerView = findViewById(R.id.recyclefood);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        database = FirebaseDatabase.getInstance().getReference();

        foodList = new ArrayList<>();
        clearAll();
        getDataFromFirebase(name);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TenantFoodActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnShowCart.setOnClickListener(v -> {
            Intent intent = new Intent(TenantFoodActivity.this, CartActivity.class);
            intent.putExtra("cartItems", cartItems);
            startActivity(intent);
        });



    }

    private void getDataFromFirebase(String tenantName) {
        Query query = database.child("tenants").orderByChild("tenantsName").equalTo(tenantName);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for (DataSnapshot tenantSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot foodSnapshot : tenantSnapshot.child("foods").getChildren()) {
                        Food food = new Food();
                        Log.d("FirebaseData", "Food: " + foodSnapshot.getKey());
                        food.setFoodName(foodSnapshot.child("foodName").getValue().toString());
                        food.setFoodPrice(foodSnapshot.child("foodPrice").getValue().toString());
                        food.setFoodDesc(foodSnapshot.child("foodDesc").getValue().toString());
                        food.setFoodUrlImage(foodSnapshot.child("foodUrlImage").getValue().toString());
                        foodList.add(food);
                    }
                }

                foodRecycleAdapter = new FoodRecycleAdapter(getApplicationContext(), foodList,TenantFoodActivity.this);
                recyclerView.setAdapter(foodRecycleAdapter);
                foodRecycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // ... handle error ...
            }
        });
    }

    private void clearAll() {
        if (foodList != null) {
            foodList.clear();
            if (foodRecycleAdapter != null) {
                foodRecycleAdapter.notifyDataSetChanged();
            }
        }
        foodList = new ArrayList<>();
    }

    @Override
    public void onQuantityChanged() {
        updateCartItems();
        if (isAnyFoodQuantityMoreThanZero()) {
            btnShowCart.setVisibility(View.VISIBLE);
        } else {
            btnShowCart.setVisibility(View.GONE);
        }
    }

    private void updateCartItems() {
        cartItems.clear();
        for (Food food : foodList) {
            if (food.getQuantity() > 0) {
                cartItems.add(food);
            }
        }
    }


    private boolean isAnyFoodQuantityMoreThanZero() {
        for (Food food : foodList) {
            if (food.getQuantity() > 0) {
                return true;
            }
        }
        return false;
    }



}