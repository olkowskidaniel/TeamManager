package com.olkowskidaniel.teammanager.repositories;


import android.util.Log;

import com.olkowskidaniel.teammanager.remotedata.Firestore;

import java.util.HashMap;
import java.util.Map;


public class UserRepository {

    private static UserRepository instance = null;
    private static final String TAG = "UserRepository";

    private Firestore firestore;


    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        firestore = new Firestore();
    }


    public void addUserToDatabase(String email) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", email);
        firestore.addToUsersCollection(userMap);
    }

    public void deleteUserFromDatabase(String email) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", email);
        firestore.deleteUserFromCollection(userMap);
        Log.d(TAG, "Deleting collection: " + email);
    }
}