package com.example.firebase_email_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText registerEmailEt, registerPasswordEt;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmailEt = findViewById(R.id.registerEmailEt);
        registerPasswordEt = findViewById(R.id.registerPasswordEt);
        progressBar = findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();
    }

    public void registerButtonClicked(View view) {
        progressBar.setVisibility(View.VISIBLE);

        String email = registerEmailEt.getText().toString().trim();
        String password = registerPasswordEt.getText().toString().trim();

        // email should not be empty
        if (TextUtils.isEmpty(email)){
            registerEmailEt.setError("enter email");
            progressBar.setVisibility(View.GONE);
            return;
        }

        //password should not be empty
        if (TextUtils.isEmpty(password)){
            registerPasswordEt.setError("enter password");
            progressBar.setVisibility(View.GONE);
            return;
        }

       // registering new user email & logging in
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this, "registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void gotoLoginAccountActivity(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}