package com.example.spotify_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify_ui.adapter.AdapterPackage;
import com.example.spotify_ui.model.Users;
import com.example.spotify_ui.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class FriendsList extends Fragment {

    EditText searchInput;
    ImageButton searchButton;
    ImageButton backButton;
    RecyclerView recyclerView;

    AdapterPackage adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.search_activity, container, false);

        (Content.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FriendsList.this).navigate(R.id.action_navigation_friends_list_to_navigation_user_activity);;
            }
        });


        
        searchInput = v.findViewById(R.id.seach_username_input);
        searchButton = v.findViewById(R.id.search_user_btn);
        backButton = v.findViewById(R.id.back_btn);
        recyclerView = v.findViewById(R.id.search_user_recycler_view);

        searchInput.requestFocus();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FriendsList.this).navigate(R.id.action_navigation_friends_list_to_navigation_friends);
            }
        });
        searchButton.setOnClickListener(vi -> {
            String searchTerm = searchInput.getText().toString();
            if(searchTerm.isEmpty() || searchTerm.length()<3){
                searchInput.setError("Invalid Username");
                return;
            }
            setupSearchRecyclerView(searchTerm);
        });
        return v;
    }

    void setupSearchRecyclerView(String searchTerm){

        Query query = FirebaseUtil.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("username",searchTerm)
                .whereLessThanOrEqualTo("username",searchTerm+'\uf8ff');

        FirestoreRecyclerOptions<Users> options = new FirestoreRecyclerOptions.Builder<Users>()
                .setQuery(query, Users.class).build();

        adapter = new AdapterPackage(options,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(adapter!=null)
//            adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(adapter!=null)
//            adapter.stopListening();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(adapter!=null)
//            adapter.startListening();
//    }
}












