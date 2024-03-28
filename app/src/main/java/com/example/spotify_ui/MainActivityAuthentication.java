package com.example.spotify_ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityAuthentication extends AppCompatActivity {
    public EditText signupEmail, signupPassword;
    Button btnSignUp;
    TextView loginRedirectText;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        firebaseAuth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.edEmail);
        signupPassword = findViewById(R.id.ConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        loginRedirectText = findViewById(R.id.txt);
        //
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailID = signupEmail.getText().toString();
                String paswd = signupPassword.getText().toString();
                if (emailID.isEmpty()) {
                    signupEmail.setError("Provide your Email first!");
                    signupEmail.requestFocus();
                } else if (paswd.isEmpty()) {
                    signupPassword.setError("Set your password");
                    signupPassword.requestFocus();
                } else if (emailID.isEmpty() && paswd.isEmpty()) {
                    Toast.makeText(MainActivityAuthentication.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
                    firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(MainActivityAuthentication.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivityAuthentication.this.getApplicationContext(),
                                        "SignUp unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(MainActivityAuthentication.this, UserActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivityAuthentication.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivityAuthentication.this, LoginActivity.class);
                startActivity(I);
            }
        });

    }
}