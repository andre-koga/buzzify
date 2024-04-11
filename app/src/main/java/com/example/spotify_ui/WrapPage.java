package com.example.spotify_ui;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WrapPage extends Fragment {

    public Button homeBttn;
    public Button dashboardBttn;
    public Button notificationBttn;

    private JSONObject artistJSON;
    private JSONObject trackJSON;
    private String title;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        String artistString = getArguments().getString("artist");
        String trackString = getArguments().getString("track");
        title = getArguments().getString("title");
        try {
            artistJSON = new JSONObject(artistString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        try {
            trackJSON = new JSONObject(trackString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Log.d("ARTIST", artistString);
        Log.d("TRACK", trackString);

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View view = inflater.inflate(R.layout.wrap_page, container,
                false);

        ((TextView) view.findViewById(R.id.title)).setText(title);

        try {
            JSONArray songArray = trackJSON.getJSONArray("items");
            ((TextView) view.findViewById(R.id.song_1)).setText(songArray.getJSONObject(0).getString("name"));
            ((TextView) view.findViewById(R.id.song_2)).setText(songArray.getJSONObject(1).getString("name"));
            ((TextView) view.findViewById(R.id.song_3)).setText(songArray.getJSONObject(2).getString("name"));
            ((TextView) view.findViewById(R.id.song_4)).setText(songArray.getJSONObject(3).getString("name"));
            ((TextView) view.findViewById(R.id.song_5)).setText(songArray.getJSONObject(4).getString("name"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        try {
            JSONArray artistArray = artistJSON.getJSONArray("items");
            ((TextView) view.findViewById(R.id.artist_1)).setText(artistArray.getJSONObject(0).getString("name"));
            ((TextView) view.findViewById(R.id.artist_2)).setText(artistArray.getJSONObject(1).getString("name"));
            ((TextView) view.findViewById(R.id.artist_3)).setText(artistArray.getJSONObject(2).getString("name"));
            ((TextView) view.findViewById(R.id.artist_4)).setText(artistArray.getJSONObject(3).getString("name"));
            ((TextView) view.findViewById(R.id.artist_5)).setText(artistArray.getJSONObject(4).getString("name"));

            ((TextView) view.findViewById(R.id.top_genre)).setText(artistArray.getJSONObject(0).getJSONArray("genres").get(0).toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle SavedInstance) {

        homeBttn = view.findViewById(R.id.button2);
        homeBttn.setVisibility(View.VISIBLE);

        dashboardBttn = view.findViewById(R.id.button3);
        dashboardBttn.setVisibility(View.VISIBLE);

        notificationBttn = view.findViewById(R.id.button4);
        notificationBttn.setVisibility(View.VISIBLE);

        (Content.getButton()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(WrapPage.this)
                        .navigate(R.id.action_navigation_wrap_page_to_navigation_user_activity);
                ;
            }
        });
        homeBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(WrapPage.this)
                        .navigate(R.id.action_navigation_wrap_page_to_navigation_home);
            }
        });

        dashboardBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(WrapPage.this)
                        .navigate(R.id.action_navigation_wrap_page_to_navigation_dashboard);
            }
        });

        notificationBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(WrapPage.this)
                        .navigate(R.id.action_navigation_wrap_page_to_navigation_notifications);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}