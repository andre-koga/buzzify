package com.example.spotify_ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.spotify.sdk.android.auth.AuthorizationClient;




public class UserActivity extends Fragment {


    Button btnLogOut;
    Button btnDelete;
    Button btnResetPassword;
    Button btnFriends;
    Button resetPassword;
    Button btnBack;
    TextView txtUser;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    EditText resetEditor;
    Button resetEmail;








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
        btnBack = (Button) view.findViewById(R.id.back_button);
        txtUser = (TextView) view.findViewById(R.id.txtUser);
        user = firebaseAuth.getCurrentUser();
        txtUser.setText(user.getEmail());
        resetPassword = (Button) view.findViewById(R.id.resetPass);
        resetEmail = view.findViewById(R.id.resetEmail);
        resetEditor = view.findViewById(R.id.enter);




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


                //AuthorizationClient.stopLoginActivity(getActivity(), Content.AUTH_TOKEN_REQUEST_CODE);




                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().getCurrentUser().delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    FirebaseFirestore.getInstance().collection("users").document(user.getUid()).delete();
                                    Log.d(TAG, "User account deleted.");
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    AuthorizationClient.stopLoginActivity(getActivity(), Content.AUTH_CODE_REQUEST_CODE);
                                    startActivity(intent);


                                    getActivity().finish();




                                }
                            }
                        });
            }
        });






        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = resetEditor.getText().toString();
                FirebaseAuth.getInstance().getCurrentUser().updatePassword(newPassword)


                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User password updated.");
                                }
                            }
                        });
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = user.getEmail();;


                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Log.d(TAG, "Email sent.");
                                }
                            }
                        });
            }
        });


        resetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = resetEditor.getText().toString();


                user.updateEmail(newPassword)


                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User Email updated.");
                                    txtUser.setText(newPassword);
                                }
                            }
                        });
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = user.getEmail();;


                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                }
                            }
                        });
            }


        });


    }


}
