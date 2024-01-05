package com.project.maku_mobile_based.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.project.maku_mobile_based.R;

import java.text.NumberFormat;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private TextView textTotalPrice;
    private String tempTotal;

    private Button buttonCancelOrder, buttonCheckStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        textTotalPrice = findViewById(R.id.txtTotalPrice);
        tempTotal = getIntent().getStringExtra("totalPrice");
        textTotalPrice.setText(tempTotal);


        buttonCancelOrder = findViewById(R.id.btnCancelOrder);

        buttonCancelOrder.setOnClickListener(v->{
                Intent intent = new Intent(CheckoutActivity.this,MenuActivity.class);
                startActivity(intent);

        });

        buttonCheckStatus = findViewById(R.id.btnCheckStatus);

        buttonCheckStatus.setOnClickListener(v->{
            Intent intent = new Intent(CheckoutActivity.this,SuccessActivity.class);
            startActivity(intent);
        });





    }
}