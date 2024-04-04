package com.example.spotify_ui.ui.home;

import static com.example.spotify_ui.Visibility.YOU;
import static com.example.spotify_ui.Wraps.wrap_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotify_ui.R;
import com.example.spotify_ui.Wraps;
import com.example.spotify_ui.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

//    public AppCompatButton test;
    public Button homeBttn;
    public Button dashboardBttn;
    public Button notificationBttn;

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

    public void onViewCreated(@NonNull View view, Bundle SavedInstance) {

        homeBttn = view.findViewById(R.id.button2);
        homeBttn.setVisibility(View.VISIBLE);

        dashboardBttn = view.findViewById(R.id.button3);
        dashboardBttn.setVisibility(View.VISIBLE);

        notificationBttn = view.findViewById(R.id.button4);
        notificationBttn.setVisibility(View.VISIBLE);


        dashboardBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_navigation_home_to_navigation_dashboard);
            }
        });

        notificationBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_navigation_home_to_navigation_notifications);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}