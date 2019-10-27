package com.olkowskidaniel.teammanager.remotedata;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Firestore {
    private FirebaseFirestore firebaseFirestore;

    public Firestore() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void addToUsersCollection(Map<String, Object> userMap) {
        firebaseFirestore.collection("users").document(userMap.get("email").toString())
                .set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Firestore", userMap.get("email").toString() + " added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Firestore", e.getMessage());
            }
        });
    }
}