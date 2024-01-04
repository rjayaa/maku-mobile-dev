package com.project.maku_mobile_based.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.project.maku_mobile_based.Adapter.TenantRecycleAdapter;
import com.project.maku_mobile_based.R;
import com.project.maku_mobile_based.TenantRecyclerViewInterface;
import com.project.maku_mobile_based.model.Tenants;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;


public class MenuActivity extends AppCompatActivity implements TenantRecyclerViewInterface {
    RecyclerView recyclerView;
    private DatabaseReference database;
    private TenantRecycleAdapter tenantRecycleAdapter;
    private ArrayList<Tenants> tenantlist;
    private Context tcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        recyclerView = findViewById(R.id.tenantsList);

        Spinner customSpinner = findViewById(R.id.customSpinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.SpinnerSort, R.layout.custom_spinner);
        customSpinner.setAdapter(adapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        database = FirebaseDatabase.getInstance().getReference();

        tenantlist = new ArrayList<>();

        // Clear Array List
        clearAll();


        // get data from firebase;
        GetDataFromFirebase();
    }

    private void GetDataFromFirebase() {
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
                    tenants.setTenantsDescription(snapshot1.child("tenantsDescription").getValue().toString());

                    tenantlist.add(tenants);
                    Log.d("FirebaseData", "Tenant added: " + tenants.getTenantsName());
                }

                tenantRecycleAdapter = new TenantRecycleAdapter(getApplicationContext(), tenantlist, MenuActivity.this);
                recyclerView.setAdapter(tenantRecycleAdapter);
                tenantRecycleAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void clearAll() {
        if (tenantlist != null) {
            tenantlist.clear();

            if(tenantRecycleAdapter != null){
                tenantRecycleAdapter.notifyDataSetChanged();
            }
        }

        tenantlist = new ArrayList<>();
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(MenuActivity.this,TenantFoodActivity.class);


        intent.putExtra("TenantName",tenantlist.get(pos).getTenantsName());
        intent.putExtra("IMAGE",tenantlist.get(pos).getTenantsUrlImage());
        intent.putExtra("DESCRIPTION", tenantlist.get(pos).getTenantsDescription());
        startActivity(intent);
    }
}