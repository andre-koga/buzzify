package com.example.spotify_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.spotify_ui.databinding.FragmentHomeBinding;
import com.example.spotify_ui.ui.home.HomeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class WrapPage extends Fragment {

    public Button homeBttn;
    public Button dashboardBttn;
    public Button notificationBttn;

    public Button backButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View view = inflater.inflate(R.layout.wrap_page, container,
                false);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle SavedInstance) {

        homeBttn = view.findViewById(R.id.button2);
        homeBttn.setVisibility(View.VISIBLE);

        dashboardBttn = view.findViewById(R.id.button3);
        dashboardBttn.setVisibility(View.VISIBLE);

        notificationBttn = view.findViewById(R.id.button4);
        notificationBttn.setVisibility(View.VISIBLE);

        backButton = view.findViewById(R.id.back_btn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(WrapPage.this).navigate(R.id.action_navigation_wrap_page_to_navigation_home);
//                NavHostFragment.findNavController(WrapPage.this).popBackStack();
            }
        });

        homeBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(WrapPage.this).navigate(R.id.action_navigation_wrap_page_to_navigation_home);
            }
        });

        dashboardBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(WrapPage.this).navigate(R.id.action_navigation_wrap_page_to_navigation_dashboard);
            }
        });

        notificationBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(WrapPage.this).navigate(R.id.action_navigation_wrap_page_to_navigation_notifications);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}