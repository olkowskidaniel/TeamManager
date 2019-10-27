package com.olkowskidaniel.teammanager.repositories;


import com.olkowskidaniel.teammanager.remotedata.Firestore;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static UserRepository instance = null;

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
}