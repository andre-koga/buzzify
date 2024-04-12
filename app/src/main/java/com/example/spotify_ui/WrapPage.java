
package com.example.spotify_ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotify_ui.ui.home.HomeViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WrapPage extends Fragment {

    public Button homeBttn;
    public Button dashboardBttn;

    public Button backButton;

    private JSONObject artistJSON;
    private JSONObject trackJSON;

    private String artists;

    private String tracks;
    private String title;

    private String whatUser;

    private String timeFrame;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        String artistString = getArguments().getString("artist");
        String trackString = getArguments().getString("track");
        whatUser = getArguments().getString("whatUser");
        timeFrame = getArguments().getString("timeFrame");
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.wrap_page, container,
                false);
        CollectionReference userWraps = FirebaseFirestore.getInstance().collection("users").document(whatUser).collection("wraps");
        Task<DocumentSnapshot> indSnapShot = userWraps.document(timeFrame).get();
        indSnapShot.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot indDoc = indSnapShot.getResult();
                artists = (String) indDoc.get("Artists");
                tracks = (String) indDoc.get("Tracks");
                title = (String) indDoc.get("Title");
                Log.d("AIDS", "aids");
                try {
                    artistJSON = new JSONObject(artists);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    trackJSON = new JSONObject(tracks);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.d("ARTIST", artists);
                Log.d("TRACK", tracks);


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

                    ImageView imageView = view.findViewById(R.id.top_artist_image);

                    String URL = artistArray.getJSONObject(0).getJSONArray("images").getJSONObject(0).getString("url");

                    Picasso.get().load(URL).into(imageView);

                    ((TextView) view.findViewById(R.id.artist_1)).setText(artistArray.getJSONObject(0).getString("name"));
                    ((TextView) view.findViewById(R.id.artist_2)).setText(artistArray.getJSONObject(1).getString("name"));
                    ((TextView) view.findViewById(R.id.artist_3)).setText(artistArray.getJSONObject(2).getString("name"));
                    ((TextView) view.findViewById(R.id.artist_4)).setText(artistArray.getJSONObject(3).getString("name"));
                    ((TextView) view.findViewById(R.id.artist_5)).setText(artistArray.getJSONObject(4).getString("name"));

                    ((TextView) view.findViewById(R.id.top_genre)).setText(artistArray.getJSONObject(0).getJSONArray("genres").get(0).toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Log.d("This is stupid", "Stupid");
            }
        });







//        try {
//            artistJSON = new JSONObject(artists);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            trackJSON = new JSONObject(tracks);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        Log.d("ARTIST", artists);
//        Log.d("TRACK", tracks);
//

//
//        try {
//
//            JSONArray songArray = trackJSON.getJSONArray("items");
//            ((TextView) view.findViewById(R.id.song_1)).setText(songArray.getJSONObject(0).getString("name"));
//            ((TextView) view.findViewById(R.id.song_2)).setText(songArray.getJSONObject(1).getString("name"));
//            ((TextView) view.findViewById(R.id.song_3)).setText(songArray.getJSONObject(2).getString("name"));
//            ((TextView) view.findViewById(R.id.song_4)).setText(songArray.getJSONObject(3).getString("name"));
//            ((TextView) view.findViewById(R.id.song_5)).setText(songArray.getJSONObject(4).getString("name"));
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            JSONArray artistArray = artistJSON.getJSONArray("items");
//            ((TextView) view.findViewById(R.id.artist_1)).setText(artistArray.getJSONObject(0).getString("name"));
//            ((TextView) view.findViewById(R.id.artist_2)).setText(artistArray.getJSONObject(1).getString("name"));
//            ((TextView) view.findViewById(R.id.artist_3)).setText(artistArray.getJSONObject(2).getString("name"));
//            ((TextView) view.findViewById(R.id.artist_4)).setText(artistArray.getJSONObject(3).getString("name"));
//            ((TextView) view.findViewById(R.id.artist_5)).setText(artistArray.getJSONObject(4).getString("name"));
//
//            ((TextView) view.findViewById(R.id.top_genre)).setText(artistArray.getJSONObject(0).getJSONArray("genres").get(0).toString());
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }


//
        ((TextView) view.findViewById(R.id.title)).setText(title);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle SavedInstance) {

        homeBttn = view.findViewById(R.id.button2);
        homeBttn.setVisibility(View.VISIBLE);

        dashboardBttn = view.findViewById(R.id.button3);
        dashboardBttn.setVisibility(View.VISIBLE);

        backButton = view.findViewById(R.id.back_btn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(WrapPage.this).navigate(R.id.action_navigation_wrap_page_to_navigation_home);
//                NavHostFragment.findNavController(WrapPage.this).popBackStack();
            }
        });


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


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
