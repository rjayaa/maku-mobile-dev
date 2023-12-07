package com.project.maku_mobile_based;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName, etPhoneNumber, etEmail, etPassword, etConfirmPassword;


    private ProgressBar progressBar;

    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);

//        getSupportActionBar().setTitle("Register");

        Toast.makeText(RegisterActivity.this, "You can register now", Toast.LENGTH_LONG).show();
        progressBar = findViewById(R.id.progressBar);
        etFullName = findViewById(R.id.txtFullName);
        etPhoneNumber = findViewById(R.id.txtPhoneNumber);
        etEmail = findViewById(R.id.txtEmail);
        etPassword = findViewById(R.id.txtPassword);
        etConfirmPassword = findViewById(R.id.txtConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtFullName = etFullName.getText().toString();
                String txtPhoneNumber = etPhoneNumber.getText().toString();
                String txtEmail = etEmail.getText().toString();
                String txtPassword = etPassword.getText().toString();
                String txtConfirmPassword = etConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(txtFullName)) {
                    etFullName.setError("Username cannot be empty");
                    etFullName.requestFocus();
                } else if (TextUtils.isEmpty(txtPhoneNumber)) {
                    etPhoneNumber.setError("Phone number cannot be empty");
                    etPhoneNumber.requestFocus();
                } else if (txtPhoneNumber.length() < 12) {
                    etPhoneNumber.setError("Enter a valid phone number");
                    etPhoneNumber.requestFocus();
                } else if (TextUtils.isEmpty(txtEmail)) {
                    etEmail.setError("Email cannot be empty");
                    etEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                    etEmail.setError("Please enter a valid email address");
                    etEmail.requestFocus();
                } else if (TextUtils.isEmpty(txtPassword)) {
                    etPassword.setError("Password cannot be empty");
                    etPassword.requestFocus();
                } else if (txtPassword.length() < 6) {
                    etPassword.setError("Password too weak");
                    etPassword.requestFocus();
                } else if (TextUtils.isEmpty(txtConfirmPassword)) {
                    etConfirmPassword.setError("Confirm password cannot be empty");
                    etConfirmPassword.requestFocus();
                } else if (!txtPassword.equals(txtConfirmPassword)) {
                    etConfirmPassword.setError("Password Confirmation is required");
                    etConfirmPassword.requestFocus();
                    etPassword.clearComposingText();
                    etConfirmPassword.clearComposingText();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(txtFullName, txtPhoneNumber, txtEmail, txtPassword);
                }
            }
        });

    }

    // Register User using the credentials given
    private void registerUser(String txtFullName, String txtPhoneNumber, String txtEmail, String txtPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User successfully registered!", Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();


                    // send verification email
                    firebaseUser.sendEmailVerification();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    // to prevent user from returning bacck to register activity on pressing back button after registration
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}