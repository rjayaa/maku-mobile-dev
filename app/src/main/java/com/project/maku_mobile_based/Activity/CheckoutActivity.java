package com.project.maku_mobile_based.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.maku_mobile_based.R;

public class CheckoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_view);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }
}