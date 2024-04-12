

package com.example.spotify_ui.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotify_ui.Content;
import com.example.spotify_ui.R;
import com.example.spotify_ui.Wraps;
import com.example.spotify_ui.databinding.FragmentHomeBinding;
import com.example.spotify_ui.utils.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    //    public AppCompatButton test;
    public Button homeBttn;
    public Button dashboardBttn;

    private String title;


    // spotify api
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private okhttp3.Call mCall;
    //
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //onGetUserProfileClicked();
        //Toast.makeText(getActivity(), Content.mAccessToken, Toast.LENGTH_SHORT).show();
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final LinearLayout main = binding.main;

//        generate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Wraps wrap = new Wraps(YOU, new JSONObject());
////                wrap_list.add(wrap);
////                wrap.createWidget(main, wrap, HomeFragment.this);
//                onGetTopTracks(TimeFrame.short_term, main);
//            }
//        });

        Wraps.createStoredWidgets(main);

        Wraps.createStoredFriendsWraps(main);

        // Dropdown
        Spinner spinner = root.findViewById(R.id.time_picker);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.time_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                if (selected.equals("Select wrap timeframe")) {
                    return;
                } else if (selected.equals("1 month wrap"))
                    onGetTopTracks(TimeFrame.short_term, main);
                else if (selected.equals("6 months wrap"))
                    onGetTopTracks(TimeFrame.medium_term, main);
                else if (selected.equals("1 year wrap"))
                    onGetTopTracks(TimeFrame.long_term, main);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        return root;
}

    public void onViewCreated(@NonNull View view, Bundle SavedInstance) {

        homeBttn = view.findViewById(R.id.button2);
        homeBttn.setVisibility(View.VISIBLE);

        dashboardBttn = view.findViewById(R.id.button3);
        dashboardBttn.setVisibility(View.VISIBLE);


        Activity activity = getActivity();
        (Content.getButton()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_navigation_home_to_navigation_user_activity);;
            }
        });



        dashboardBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_navigation_home_to_navigation_dashboard);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // spotify stuff
    /**
     * KOGA - I'm going to create the getWrap(initial time, end time) => JSONObject function here.
     */
    public enum TimeFrame {
        short_term, medium_term, long_term
    }

    public void onGetTopTracks(TimeFrame timeFrame, LinearLayout main) {
        if (Content.mAccessToken == null) {
            Toast.makeText(getActivity(), "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }

        String term = "medium_term";
        if (timeFrame == TimeFrame.long_term) {
            term = "long_term";
        } else if (timeFrame == TimeFrame.short_term) {
            term = "short_term";
        }

        // Create a request to get the user profile
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/tracks?limit=5&time_range=" + term)
                .addHeader("Authorization", "Bearer " + Content.mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(getActivity(), "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject topTracks = null;
                try {
                    topTracks = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                onGetTopArtists(timeFrame, main, topTracks);
            }
        });
    }

    public void onGetTopArtists(TimeFrame timeFrame, LinearLayout main, JSONObject topTracks) {
        if (Content.mAccessToken == null) {
            Toast.makeText(getActivity(), "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }

        String term = "medium_term";
        if (timeFrame == TimeFrame.long_term) {
            term = "long_term";
        } else if (timeFrame == TimeFrame.short_term) {
            term = "short_term";
        }

        // Create a request to get the user profile
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/artists?limit=5&time_range=" + term)
                .addHeader("Authorization", "Bearer " + Content.mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(getActivity(), "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            JSONObject temp = new JSONObject(response.body().string());
                            Map<String, Object> test = new HashMap<>();
                            test.put("Tracks", topTracks.toString());

                            test.put("Artists", temp.toString());

                            test.put("TimeFrame", timeFrame.toString());

                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
                            String date = sdf.format(new Date()).toString();

                            if (timeFrame == HomeFragment.TimeFrame.long_term) {
                                title =  date + " - 1YR";
                            } else if (timeFrame == HomeFragment.TimeFrame.medium_term) {
                                title = date + " - 6M";
                            } else {
                                title =  date + " - 1M";
                            }
                            test.put("Title", title);



                            FirebaseUtil.addWraptoCollection(timeFrame.toString()).set(test);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Wraps.createNewWidget(main, title, timeFrame.toString(), FirebaseAuth.getInstance().getUid());
                    }
                });
            }
        });
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
    //



}
