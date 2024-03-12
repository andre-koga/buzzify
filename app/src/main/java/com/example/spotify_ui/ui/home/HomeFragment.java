package com.example.spotify_ui.ui.home;

import static com.example.spotify_ui.Visibility.YOU;
import static com.example.spotify_ui.Wraps.wrap_list;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotify_ui.MainActivity;
import com.example.spotify_ui.Wraps;
import com.example.spotify_ui.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        final Button generate = binding.generateWraps;
        final LinearLayout main = binding.main;
        for (int i = 0; i < wrap_list.size(); i++) {
            Wraps wrap = wrap_list.get(i);
            wrap.createWidget(main, wrap, HomeFragment.this);
        }
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wraps wrap = new Wraps(YOU, "a", "z", "L");
                wrap_list.add(wrap);
                wrap.createWidget(main, wrap, HomeFragment.this);
            }

        });
        return root;



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}