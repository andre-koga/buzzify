package com.example.spotify_ui.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.example.spotify_ui.utils.FirebaseUtil;


public class Users {
    private String username;
    private Timestamp createdTimestamp;
    private String userId;
    private String fcmToken;
    private CollectionReference subCollectionRef;

    public Users() {
    }

    public Users(String username, Timestamp createdTimestamp, String userId) {
        this.username = username;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
        this.subCollectionRef = FirebaseUtil.createFriendsCollection();
    }
    public CollectionReference getSubCollectionRef() {
        return subCollectionRef;
    }
    public void setSubCollectionRef(CollectionReference subCollectionRef1) {
        subCollectionRef = subCollectionRef1;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}