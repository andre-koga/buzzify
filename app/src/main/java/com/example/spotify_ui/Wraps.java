
package com.example.spotify_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Wraps {
    public static void createNewWidget(LinearLayout main, String title, String timeFrame) {
        View view = LayoutInflater.from(main.getContext()).inflate(R.layout.wrap_widget,null, false);
        Button btn = (Button) ((ViewGroup) view).getChildAt(0);
        btn.setText(title);
        View details = ((ViewGroup) view).getChildAt(1);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("timeFrame", timeFrame);
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
                    createNewWidget(main, title, timeFrame);
                }

            }
        });
    }


}
