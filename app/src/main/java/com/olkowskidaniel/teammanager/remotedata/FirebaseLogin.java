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
    private MutableLiveData<String> firebaseLoginMessage;
    private MutableLiveData<String> firebaseLoginFailureMessage;


    public FirebaseLogin() {
        firebaseAuth = FirebaseAuth.getInstance();
        currentFirebaseUser = firebaseAuth.getCurrentUser();
        firebaseLoginMessage = new MutableLiveData<>();
        firebaseLoginFailureMessage = new MutableLiveData<>();
        firebaseLoginMessage.setValue("defaultMessage");
        firebaseLoginFailureMessage.setValue("defaultMessage");
    }

    public void signInWithEmail(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("FirebaseLogin", "Logged in successfully");
                    getFirebaseLoginMessage().setValue("userLoginSuccess");
                } else {
                    getFirebaseLoginMessage().setValue("userLoginFailure");
                    getFirebaseLoginFailureMessage().setValue(task.getException().getMessage());
                    Log.d("FirebaseLogin", getFirebaseLoginFailureMessage().getValue());
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
        getFirebaseLoginMessage().setValue("userLoggedOut");
    }

    public MutableLiveData<String> getFirebaseLoginMessage() {
        return firebaseLoginMessage;
    }

    public MutableLiveData<String> getFirebaseLoginFailureMessage() {
        return firebaseLoginFailureMessage;
    }
}
