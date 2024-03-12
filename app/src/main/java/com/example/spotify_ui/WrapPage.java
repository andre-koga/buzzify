package com.example.spotify_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotify_ui.databinding.FragmentHomeBinding;
import com.example.spotify_ui.ui.home.HomeViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class WrapPage extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);




        View view = inflater.inflate(R.layout.wrap_page, container,
                false);

        
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}