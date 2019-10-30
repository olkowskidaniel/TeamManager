package com.olkowskidaniel.teammanager.remotedata;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Firestore {
    private static final String TAG = "Firestore";
    private FirebaseFirestore firebaseFirestore;

    public Firestore() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void addToUsersCollection(Map<String, Object> userMap) {
        firebaseFirestore.collection("users").document(userMap.get("email").toString())
                .set(userMap).addOnSuccessListener(aVoid -> Log.d(TAG, userMap.get("email").toString() + " added")).addOnFailureListener(e -> Log.d("Firestore", e.getMessage()));
    }

    public void deleteUserFromCollection(Map<String, Object> userMap) {
        firebaseFirestore.collection("users").document(userMap.get("email").toString()).delete().addOnSuccessListener(aVoid -> {
            Log.d(TAG, "Document " + userMap.get("email").toString() + " successfully deleted");
        });
    }
}