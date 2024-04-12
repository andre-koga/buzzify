
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

import com.example.spotify_ui.R;
import com.example.spotify_ui.model.Users;
import com.example.spotify_ui.MainActivity;

import com.example.spotify_ui.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;

public class AdapterPackage extends FirestoreRecyclerAdapter<Users, AdapterPackage.UserModelViewHolder> {

    Context context;
    Button addButton;

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

        }
    }
}
