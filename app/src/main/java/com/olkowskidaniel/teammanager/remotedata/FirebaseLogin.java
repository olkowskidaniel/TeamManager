package com.olkowskidaniel.teammanager.remotedata;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseLogin {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentFirebaseUser;
    private MutableLiveData<String> firebaseMessage;
    private MutableLiveData<String> firebaseFailureMessage;


    public FirebaseLogin() {
        firebaseAuth = FirebaseAuth.getInstance();
        currentFirebaseUser = firebaseAuth.getCurrentUser();
        firebaseMessage = new MutableLiveData<>();
        firebaseMessage.setValue("defaultMessage");
        firebaseFailureMessage = new MutableLiveData<>();
        firebaseFailureMessage.setValue("defaultMessage");
    }

    public void signInWithEmail(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("FirebaseLogin", email + " logged in successfully");
                    getFirebaseMessage().setValue("userLoginSuccess");
                } else {
                    getFirebaseMessage().setValue("userLoginFailure");
                    getFirebaseFailureMessage().setValue(task.getException().getMessage());
                    Log.d("FirebaseLogin", getFirebaseFailureMessage().getValue());
                }
            }
        });
    }

    public void registerWithEmail(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("FirebaseLogin", "User " + email + " created");
                    getFirebaseMessage().setValue("registerSuccess");
                }
                else {
                    getFirebaseMessage().setValue("registerFailure");
                    getFirebaseFailureMessage().setValue(task.getException().getMessage());
                }
            }
        });
    }

    public FirebaseUser getCurrentFirebaseUser() {
        return currentFirebaseUser;
    }

    public void logout() {
        firebaseAuth.signOut();
        Log.d("FirebaseLogin", "Logged out");
        getFirebaseMessage().setValue("userLoggedOut");
    }

    public MutableLiveData<String> getFirebaseMessage() {
        return firebaseMessage;
    }

    public MutableLiveData<String> getFirebaseFailureMessage() {
        return firebaseFailureMessage;
    }
}
