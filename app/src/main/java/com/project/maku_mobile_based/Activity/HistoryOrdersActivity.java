package com.project.maku_mobile_based.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.project.maku_mobile_based.R;

public class HistoryOrdersActivity extends AppCompatActivity {

    private Button buttonToMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        buttonToMenu = findViewById(R.id.btnBackToMenu);

        buttonToMenu.setOnClickListener(v->{
            Intent intent = new Intent(HistoryOrdersActivity.this,MenuActivity.class);
            startActivity(intent);
        });
    }


}