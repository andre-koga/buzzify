
package com.example.spotify_ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotify_ui.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserActivity extends Fragment {

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
    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View v = inflater.inflate(R.layout.activity_user, container, false);
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle SavedInstance) {
        firebaseAuth = FirebaseAuth.getInstance();
        btnLogOut = (Button) view.findViewById(R.id.btnLogOut);
        btnDelete = (Button) view.findViewById(R.id.btnDeleteAccount);
        btnResetPassword = (Button) view.findViewById(R.id.btnResetPassword);
        btnBack = (Button) view.findViewById(R.id.back_button);
        txtUser = (TextView) view.findViewById(R.id.txtUser);
        user = firebaseAuth.getCurrentUser();
        txtUser.setText(user.getEmail());

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setCustomView(R.layout.title_main);
//        View v = actionBar.getCustomView();
//        Button btn = v.findViewById(R.id.user_button);
//        btn.setText(user.getEmail());


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(UserActivity.this).navigate(R.id.action_navigation_activity_user_to_navigation_home);
            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                //Intent I = new Intent(UserActivity.this, LoginActivity.class);
                //startActivity(I);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

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
