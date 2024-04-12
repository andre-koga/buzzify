
package com.example.spotify_ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.spotify_ui.ui.home.HomeFragment;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Wraps extends Activity {
    public static ArrayList<Wraps> wrap_list = new ArrayList<>(0);
    private final Visibility visible;

    private final HomeFragment.TimeFrame timeFrame;
    private final String wrap_name;
    private final JSONObject trackJSON;
    private final JSONObject artistJSON;

    public Wraps(Visibility visible, JSONObject topTracks, JSONObject topArtists, HomeFragment.TimeFrame timeFrame) {
        this.visible = visible;
        this.timeFrame = timeFrame;
        this.trackJSON = topTracks;
        this.artistJSON = topArtists;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        String date = sdf.format(new Date()).toString();

        if (timeFrame == HomeFragment.TimeFrame.long_term) {
            this.wrap_name = date + " - 1YR";
        } else if (timeFrame == HomeFragment.TimeFrame.medium_term) {
            this.wrap_name = date + " - 6M";
        } else {
            this.wrap_name = date + " - 1M";
        }
    }

    public Visibility getVisible() {
        return visible;
    }

    public void createWidget(LinearLayout main, Wraps wrap, Fragment frag) {
        View view = LayoutInflater.from(main.getContext()).inflate(R.layout.wrap_widget,null, false);
        Button btn = (Button) ((ViewGroup) view).getChildAt(0);
        btn.setText(wrap_name);
        View details = ((ViewGroup) view).getChildAt(1);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("artist", artistJSON.toString());
                bundle.putString("track", trackJSON.toString());
                bundle.putString("title", wrap_name);
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.navigation_wrap_page, bundle);
////                NavHostFragment.findNavController(new WrapPage()).navigate(R.id.navigation_wrap_page);
//                Fragment fragment = new WrapPage();
//                FragmentManager fragmentManager = frag.getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                fragmentTransaction.replace(R.id.container, fragment);
//                fragmentTransaction.addToBackStack("returnable");
//                fragmentTransaction.commit();
            }
        });

//        instructor.setText("Instructor: " + (wrap.getArtists()));
//        location.setText("Location: " + wrap.getUser());
//        time.setText("Date/Time: " + wrap.getWrap_name());

        main.addView(view);
    }
}
