
package com.example.spotify_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.spotify_ui.utils.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Wraps {






    public static void createNewWidget(LinearLayout main, String title, String timeFrame, String whatUser) {

        View view = LayoutInflater.from(main.getContext()).inflate(R.layout.wrap_widget,null, false);
        Button btn = (Button) ((ViewGroup) view).getChildAt(0);
        btn.setText(title);
        View details = ((ViewGroup) view).getChildAt(1);

        TextView name = (TextView) ((ViewGroup) details).getChildAt(0);
        DocumentReference temp = FirebaseFirestore.getInstance().collection("users").document(whatUser);
        temp.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();
                String email = doc.getString("username");
                name.setText(email);
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("timeFrame", timeFrame);
                bundle.putString("whatUser", whatUser);
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.navigation_wrap_page, bundle);

            }
        });



        main.addView(view);
    }

    public static void createStoredWidgets(LinearLayout main) {
        CollectionReference ref = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection("wraps");
        ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String title, timeFrame;
                for (DocumentSnapshot document : task.getResult()) {
                    title = (String) document.get("Title");
                    timeFrame = (String) document.get("TimeFrame");
                    createNewWidget(main, title, timeFrame, FirebaseAuth.getInstance().getUid());
                }

            }
        });
    }

    public static void createStoredFriendsWraps(LinearLayout main) {
        CollectionReference refss = FirebaseFirestore.getInstance().collection("users");
        CollectionReference ref = refss.document(FirebaseAuth.getInstance().getUid()).collection("friends");
        ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot doc : task.getResult()) {
                    String friendsId = doc.getId();
                    CollectionReference friendsWraps = refss.document(friendsId).collection("wraps");
                    friendsWraps.get().addOnCompleteListener(tasks ->{
                        if (tasks.isSuccessful()) {
                            for (DocumentSnapshot wrap : tasks.getResult()) {
                                String title = (String) wrap.get("Title");
                                String timeFrame = (String) wrap.get("TimeFrame");
                                createNewWidget(main, title, timeFrame, friendsId);
                            }
                        }
                    });

                }
            }
        });

    }

    public static HashMap<String, String> getDuoData(String id) {
        HashMap<String, String> data = new HashMap<>();
        CollectionReference allWraps = FirebaseUtil.getAllWraps(id);
        allWraps.get().addOnCompleteListener(task ->  {
            if (task.isSuccessful()) {
                for (DocumentSnapshot doc : task.getResult()) {
                    String title = (String) doc.get("Title");
                    String artists = (String) doc.get("Artists");
                    String tracks = (String) doc.get("Tracks");

                    data.put("title", title);
                    data.put("artists", artists);
                    data.put("tracks", tracks);
                    break;

                }

            }


        });


        return data;
    }






}
