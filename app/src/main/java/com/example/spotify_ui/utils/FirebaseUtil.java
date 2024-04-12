

package com.example.spotify_ui.utils;

import com.example.spotify_ui.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtil {



    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static boolean isLoggedIn(){
        if(currentUserId()!=null){
            return true;
        }
        return false;
    }

    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }
    public static DocumentReference currentFriendsDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    public static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }


    public static void logout(){
        FirebaseAuth.getInstance().signOut();
    }


    public static CollectionReference createFriendsCollection() {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId()).collection("friends");
    }
    public static DocumentReference addFriendtoCollection(Users user) {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId()).collection("friends").document(user.getUserId());
    }
    public static DocumentReference addOtherFriendtoCollection(Users user) {
        return FirebaseFirestore.getInstance().collection("users").document(user.getUserId()).collection("friends").document(currentUserId());
    }
    public static  DocumentReference addWraptoCollection(String timeFrame) {

        return FirebaseFirestore.getInstance().collection("users").document(currentUserId()).collection("wraps").document(timeFrame);
    }
    public static CollectionReference getAllWraps() {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId()).collection("wraps");
    }
    public static CollectionReference allFriendsCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId()).collection("friends");
    }


}
