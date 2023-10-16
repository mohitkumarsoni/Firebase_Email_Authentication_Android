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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText passwordEt, emailEt;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getUid() != null){
            // it will check if there is any current user logged in, n pass directly to its main activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        progressBar = findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();

    }

    public void loginBtnClicked(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();

        // email should not be empty
        if (TextUtils.isEmpty(email)){
            emailEt.setError("enter email");
            progressBar.setVisibility(View.GONE);
            return;
        }

        //password should not be empty
        if (TextUtils.isEmpty(password)){
            emailEt.setError("enter password");
            progressBar.setVisibility(View.GONE);
            return;
        }

        // logging in user, if account is registered
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void gotoRegisterAccountActivity(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }
}

//login activity is launcher activity
