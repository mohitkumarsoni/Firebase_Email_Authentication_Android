package com.example.firebase_email_authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextView userInfoTv;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            try {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }catch (Exception e){
                Log.d("UserLoginError", Objects.requireNonNull(e.getMessage()));
            }
        }else {
            userInfoTv.setText(user.getEmail());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInfoTv = findViewById(R.id.userInfoTv);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user == null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }else {
            userInfoTv.setText(user.getEmail());
        }
    }

    public void logoutBtnClicked(View view) {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}