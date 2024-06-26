

package com.example.spotify_ui.adapter;

import static com.example.spotify_ui.utils.FirebaseUtil.currentUserId;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify_ui.MainActivity;
import com.example.spotify_ui.R;
import com.example.spotify_ui.model.Users;
import com.example.spotify_ui.ui.home.HomeFragment;
import com.example.spotify_ui.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AdapterPackage extends FirestoreRecyclerAdapter<Users, AdapterPackage.UserModelViewHolder> {

    Context context;
    Button addButton;
    Button duoWrapped;

    public AdapterPackage(@NonNull FirestoreRecyclerOptions<Users> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserModelViewHolder holder, int position, @NonNull Users model) {
        holder.usernameText.setText(model.getUsername());
        if(model.getUserId().equals(currentUserId())){
            holder.usernameText.setText(model.getUsername()+" (Me)");
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUtil.addFriendtoCollection(model).set(model);
                FirebaseUtil.addOtherFriendtoCollection(model).set(MainActivity.userModel);
            }
        });

        duoWrapped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDuoWrapJSONHelper(model.getUserId());
            }
        });
    }

    @NonNull
    @Override
    public UserModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.firebase_profiles,parent,false);
        return new UserModelViewHolder(view);
    }

    class UserModelViewHolder extends RecyclerView.ViewHolder{
        TextView usernameText;
        ImageView profilePic;

        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
            addButton = itemView.findViewById(R.id.addButton);
            duoWrapped = itemView.findViewById(R.id.duoWrapped);

        }
    }
    public void CreateDuoWrapJSONHelper(String otherId) {
        // retrieve wrap from friend, and retrieve wrap from myself
        HashMap<String, String> data = new HashMap<>();
        HashMap<String, String> otherData = new HashMap<>();
        CollectionReference allWraps = FirebaseUtil.getAllWraps(FirebaseAuth.getInstance().getUid());
        allWraps.get().addOnCompleteListener(task ->  {
            if (task.isSuccessful()) {
                for (DocumentSnapshot doc : task.getResult()) {
                    String title = (String) doc.get("Title");
                    String artists = (String) doc.get("Artists");
                    String tracks = (String) doc.get("Tracks");

                    data.put("title", title);
                    data.put("artists", artists);
                    data.put("tracks", tracks);
                    data.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    break;
                }
                CollectionReference allWraps2 = FirebaseUtil.getAllWraps(otherId);
                allWraps2.get().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        for (DocumentSnapshot doc : task1.getResult()) {
                            String title = (String) doc.get("Title");
                            String artists = (String) doc.get("Artists");
                            String tracks = (String) doc.get("Tracks");
                            DocumentReference ref = FirebaseFirestore.getInstance().collection("users").document(otherId);
                            ref.get().addOnCompleteListener(task3 -> {
                                if (task3.isSuccessful()) {
                                    DocumentSnapshot otherAccount = task3.getResult();
                                    String otherEmail = (String) otherAccount.get("username");
                                    data.put("title2", title);
                                    data.put("artists2", artists);
                                    data.put("tracks2", tracks);
                                    data.put("email2", otherEmail);
                                    data.put("otherId", otherEmail);


                                    HomeFragment.CreateDuoWrapJSON(data, otherData, otherId);
                                }
                            });
                        }



                    }
                });
            }
            });


    }
//    public JSONObject CreateDuoWrapJSON(HashMap<String, String> yourData, HashMap<String, String> otherData, String otherId) {
//        // retrieve wrap from friend, and retrieve wrap from myself
//
//
//        DocumentReference ref1 = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection("duowraps").document(yourData.get("email") + otherData.get("email"));
//        ref1.set(yourData);
//        ref1.set(otherData);
//
//        DocumentReference ref2 = FirebaseFirestore.getInstance().collection("users").document(otherId).collection("duowraps").document(otherData.get("email") + yourData.get("email"));
//        ref2.set(otherData);
//        ref2.set(yourData);
//
//        Wraps.createDuoWrapWidget();
//
//
//
//    }

}
