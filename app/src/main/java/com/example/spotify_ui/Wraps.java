
package com.example.spotify_ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Wraps {


    public static void createNewWidget(LinearLayout main, String title, String timeFrame, String whatUser) {

        View view = LayoutInflater.from(main.getContext()).inflate(R.layout.wrap_widget, null, false);
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
                    friendsWraps.get().addOnCompleteListener(tasks -> {
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

    public static void createDuoWrapWidget(LinearLayout main) {

        CollectionReference refss = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection("duowraps");
        refss.get().addOnCompleteListener(task -> {
           if (task.isSuccessful())  {
               for (DocumentSnapshot doc : task.getResult()) {
                   String name = doc.getId();
                   View view = LayoutInflater.from(main.getContext()).inflate(R.layout.wrap_widget, null, false);
                   Button btn = (Button) ((ViewGroup) view).getChildAt(0);
                   btn.setBackgroundColor(Color.parseColor("#1DB954"));
                   btn.setText(name);
                   btn.setTextSize(30);
                   View details = ((ViewGroup) view).getChildAt(1);
                   details.setBackgroundColor(Color.parseColor("#1DB954"));
                   TextView title = (TextView) ((ViewGroup) details).getChildAt(0);

                   title.setText("");
                   btn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Bundle bundle = new Bundle();
                           bundle.putString("Name", name);



                           NavController navController = Navigation.findNavController(v);

                           navController.navigate(R.id.navigation_duo_wrap_page, bundle);

                       }
                   });

                   main.addView(view);
               }
           }
        });



    }


}
