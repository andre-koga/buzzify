
package com.example.spotify_ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.spotify_ui.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;


public class UserActivity extends AppCompatActivity {

    Button btnLogOut;
    Button btnDelete;
    Button btnResetPassword;
    Button btnFriends;

    Button btnBack;
    TextView txtUser;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    final String TAG = "UserActivity";

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firebaseAuth = FirebaseAuth.getInstance();
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnDelete = (Button) findViewById(R.id.btnDeleteAccount);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);
        btnBack = (Button) findViewById(R.id.back_button);
        txtUser = (TextView) findViewById(R.id.txtUser);
        user = firebaseAuth.getCurrentUser();
        txtUser.setText(user.getEmail());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.title_main);
        View v = actionBar.getCustomView();
        Button btn = v.findViewById(R.id.user_button);
        btn.setText(user.getEmail());


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Content.class);
                startActivity(intent);
                finish();
            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                //Intent I = new Intent(UserActivity.this, LoginActivity.class);
                //startActivity(I);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User account deleted.");
                                }
                            }
                        });
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = "SOME-SECURE-PASSWORD";
                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User password updated.");
                                }
                            }
                        });
            }
        });

    }

}
