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
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DuoWrapPage extends Fragment {
    public Button homeBttn;
    public Button dashboardBttn;


    private JSONObject artistJSON;
    private JSONObject trackJSON;

    private JSONObject artistJSON2;
    private JSONObject trackJSON2;

    private String artists;

    private String tracks;
    private String title;

    private String whatUser;

    private String timeFrame;

    private String email;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.duo_wrap, container,
                false);
        String name = getArguments().getString("Name");

        CollectionReference userWraps = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection("duowraps");
        Task<DocumentSnapshot> indSnapShot = userWraps.document(name).get();
        indSnapShot.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot indDoc = indSnapShot.getResult();
                artists = (String) indDoc.get("artists");
                tracks = (String) indDoc.get("tracks");
                title = (String) indDoc.get("title");
                email = (String) indDoc.get("email");

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



                try {
                    JSONArray songArrays = trackJSON.getJSONArray("items");
                    ((TextView) view.findViewById(R.id.song_1)).setText(songArrays.getJSONObject(0).getString("name"));
                    ((TextView) view.findViewById(R.id.song_2)).setText(songArrays.getJSONObject(1).getString("name"));
                    ((TextView) view.findViewById(R.id.song_3)).setText(songArrays.getJSONObject(2).getString("name"));
                    ((TextView) view.findViewById(R.id.song_4)).setText(songArrays.getJSONObject(3).getString("name"));
                    ((TextView) view.findViewById(R.id.song_5)).setText(songArrays.getJSONObject(4).getString("name"));
                } catch (JSONException e){
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

                artists = (String) indDoc.get("artists2");
                tracks = (String) indDoc.get("tracks2");
                title = (String) indDoc.get("title2");
                email = (String) indDoc.get("email2");

                try {
                    artistJSON2 = new JSONObject(artists);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    trackJSON2 = new JSONObject(tracks);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }



                try {
                    JSONArray songArray2 = trackJSON2.getJSONArray("items");
                    ((TextView) view.findViewById(R.id.Asong_1)).setText(songArray2.getJSONObject(0).getString("name"));
                    ((TextView) view.findViewById(R.id.Asong_2)).setText(songArray2.getJSONObject(1).getString("name"));
                    ((TextView) view.findViewById(R.id.Asong_3)).setText(songArray2.getJSONObject(2).getString("name"));
                    ((TextView) view.findViewById(R.id.Asong_4)).setText(songArray2.getJSONObject(3).getString("name"));
                    ((TextView) view.findViewById(R.id.Asong_5)).setText(songArray2.getJSONObject(4).getString("name"));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {

                    JSONArray artistArray2 = artistJSON2.getJSONArray("items");




                    ((TextView) view.findViewById(R.id.Aartist_1)).setText(artistArray2.getJSONObject(0).getString("name"));
                    ((TextView) view.findViewById(R.id.Aartist_2)).setText(artistArray2.getJSONObject(1).getString("name"));
                    ((TextView) view.findViewById(R.id.Aartist_3)).setText(artistArray2.getJSONObject(2).getString("name"));
                    ((TextView) view.findViewById(R.id.Aartist_4)).setText(artistArray2.getJSONObject(3).getString("name"));
                    ((TextView) view.findViewById(R.id.Aartist_5)).setText(artistArray2.getJSONObject(4).getString("name"));

                    ((TextView) view.findViewById(R.id.Atop_genre)).setText(artistArray2.getJSONObject(0).getJSONArray("genres").get(0).toString());

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Log.d("This is stupid", "Stupid");
            }
        });


        return view;
    }
    public void onViewCreated(@NonNull View view, Bundle SavedInstance) {

        homeBttn = view.findViewById(R.id.button2);
        homeBttn.setVisibility(View.VISIBLE);

        dashboardBttn = view.findViewById(R.id.button3);
        dashboardBttn.setVisibility(View.VISIBLE);




        (Content.getButton()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(DuoWrapPage.this)
                        .navigate(R.id.action_navigation_duo_wrap_page_to_navigation_user_activity);
                ;
            }
        });
        homeBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(DuoWrapPage.this)
                        .navigate(R.id.action_navigation_duo_wrap_page_to_navigation_home);
            }
        });

        dashboardBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(DuoWrapPage.this)
                        .navigate(R.id.action_navigation_duo_wrap_page_to_navigation_dashboard);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
