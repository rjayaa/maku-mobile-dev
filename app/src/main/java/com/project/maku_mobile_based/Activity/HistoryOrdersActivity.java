package com.project.maku_mobile_based.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.maku_mobile_based.Adapter.CartItemRecycleAdapter;
import com.project.maku_mobile_based.Adapter.HistoryItemRecycleAdapter;
import com.project.maku_mobile_based.R;
import com.project.maku_mobile_based.model.Food;
import com.project.maku_mobile_based.model.Orders;

import java.util.ArrayList;

public class HistoryOrdersActivity extends AppCompatActivity {

    private ArrayList<Orders> orderItems;
    private RecyclerView recyclerView;
    private HistoryItemRecycleAdapter historyItemRecycleAdapter;
    private Context context;
    private DatabaseReference databaseReference;


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        recyclerView = findViewById(R.id.recyclehistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        orderItems = new ArrayList<>();
        historyItemRecycleAdapter = new HistoryItemRecycleAdapter(this, orderItems, /*listener*/ null);
        recyclerView.setAdapter(historyItemRecycleAdapter);

        // Mendapatkan referensi database
        databaseReference = FirebaseDatabase.getInstance().getReference("orders"); // Sesuaikan dengan struktur database Anda

        // Query berdasarkan username pengguna yang sedang login
        String currentUsername = currentUser.getDisplayName(); // atau mendapatkan username dari sumber lain
        Query query = databaseReference.orderByChild("username").equalTo(currentUsername);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Orders order = snapshot.getValue(Orders.class);
                    orderItems.add(order);
                }
                historyItemRecycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("DBError", "loadPost:onCancelled", databaseError.toException());
            }
        });


    }


}