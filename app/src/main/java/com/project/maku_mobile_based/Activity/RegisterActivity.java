package com.project.maku_mobile_based.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.maku_mobile_based.R;
import com.project.maku_mobile_based.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName, etPhoneNumber, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister, btnBack;

    private TextView txtSignInTextView;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);
        getSupportActionBar().hide();

        btnBack = findViewById(R.id.backBtn);

        etFullName = findViewById(R.id.txtFullName);
        etPhoneNumber = findViewById(R.id.txtPhoneNumber);
        etEmail = findViewById(R.id.txtEmail);
        etPassword = findViewById(R.id.txtPassword);
        etConfirmPassword = findViewById(R.id.txtConfirmPassword);
        txtSignInTextView = findViewById(R.id.txtSignIn);
        btnRegister = findViewById(R.id.btnRegister);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // move to login activity
        txtSignInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtFullName = etFullName.getText().toString();
                String txtPhoneNumber = etPhoneNumber.getText().toString();
                String txtEmail = etEmail.getText().toString();
                String txtPassword = etPassword.getText().toString();
                String txtConfirmPassword = etConfirmPassword.getText().toString();
                String mobileRegex = "(\\+62|62|0)8[1-9][0-9]{6,}$";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(txtPhoneNumber);

                if (TextUtils.isEmpty(txtFullName)) {
                    etFullName.setError("Username cannot be empty");
                    etFullName.requestFocus();
                } else if (TextUtils.isEmpty(txtPhoneNumber)) {
                    etPhoneNumber.setError("Phone number cannot be empty");
                    etPhoneNumber.requestFocus();
                } else if (txtPhoneNumber.length() < 12) {
                    etPhoneNumber.setError("Enter a valid phone number");
                    etPhoneNumber.requestFocus();
                } else if(!mobileMatcher.find()){
                    etPhoneNumber.setError("Enter a valid phone number");
                    etPhoneNumber.requestFocus();
                }
                else if (TextUtils.isEmpty(txtEmail)) {
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
                    registerUser(txtFullName, txtPhoneNumber, txtEmail, txtPassword);
                }
            }
        });
    }


    private void registerUser(String txtFullName, String txtPhoneNumber, String txtEmail, String txtPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        // create user
        auth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User successfully registered!", Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    // generate user object
                    User usr = new User(txtFullName, txtPhoneNumber, txtEmail);
                    db.collection("Users").document(firebaseUser.getUid()).set(usr).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "User registration failed, Please try again!",Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        etPassword.setError("Your password is too weak");
                        etPassword.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        etPassword.setError("Your email is invalid or already in use");
                        etPassword.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        etPassword.setError("User is already registered with this email, use another email");
                        etPassword.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}