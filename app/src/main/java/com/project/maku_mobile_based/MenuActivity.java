package com.project.maku_mobile_based;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.project.maku_mobile_based.model.Tenants;
import android.util.Log;

import java.util.ArrayList;


public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private DatabaseReference database;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<Tenants> tenantlist;
    private Context tcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_view);

        recyclerView = findViewById(R.id.tenantsList);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        database = FirebaseDatabase.getInstance().getReference();

        tenantlist = new ArrayList<>();

        // Clear Array List
        clearAll();


        // get data from firebase;
        GetDatFromFirebase();
    }

    private void GetDatFromFirebase() {
        Query query = database.child("tenants");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Tenants tenants = new Tenants();
                    tenants.setTenantsUrlImage(snapshot1.child("tenantsUrlImage").getValue().toString());
                    tenants.setTenantsName(snapshot1.child("tenantsName").getValue().toString());
                    tenants.setTenantsCategory(snapshot1.child("tenantsCategory").getValue().toString());

                    tenantlist.add(tenants);
                    Log.d("FirebaseData", "Tenant added: " + tenants.getTenantsName());
                }

                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), tenantlist);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void clearAll() {
        if (tenantlist != null) {
            tenantlist.clear();

            if(recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }

        tenantlist = new ArrayList<>();
    }
}