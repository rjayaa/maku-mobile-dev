package com.project.maku_mobile_based;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;



public class LoginActivity extends AppCompatActivity {


    private Button btnViewRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
    }
}