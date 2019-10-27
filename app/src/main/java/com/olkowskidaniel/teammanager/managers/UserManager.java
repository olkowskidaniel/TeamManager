package com.olkowskidaniel.teammanager.managers;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserManager {

    private static final String TAG = "UserManager";

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentFirebaseUser;
    private MutableLiveData<FirebaseUser> currentFirebaseUserLiveData;
    private MutableLiveData<Boolean> isUserLoggedLiveData;
    private MutableLiveData<String> loginFailureMessageLiveData;
    private Boolean isUserLogged;


    private static UserManager instance = null;

    public static UserManager getInstance() {
        if(instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private UserManager() {
        firebaseAuth = FirebaseAuth.getInstance();
        currentFirebaseUser = firebaseAuth.getCurrentUser();
        currentFirebaseUserLiveData = new MutableLiveData<>();
        isUserLoggedLiveData = new MutableLiveData<>();
        loginFailureMessageLiveData = new MutableLiveData<>();
        if(currentFirebaseUser == null) {
            getIsUserLoggedLiveData().setValue(false);
            isUserLogged = false;
        } else {
            getIsUserLoggedLiveData().setValue(true);
            isUserLogged = true;
        }
    }

    public void signInWithEmail(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, email + " logged in");
                currentFirebaseUser = firebaseAuth.getCurrentUser();
                getCurrentFirebaseUserLiveData().setValue(currentFirebaseUser);
                getIsUserLoggedLiveData().setValue(true);
                isUserLogged = true;
                loginFailureMessageLiveData.setValue("");
            } else {
                loginFailureMessageLiveData.setValue(task.getException().getMessage());
            }
        });
    }

    public void registerWithEmail(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "User " + email + " created");
            }
        });
    }

    public void logout() {
        firebaseAuth.signOut();
        currentFirebaseUser = null;
        getIsUserLoggedLiveData().setValue(false);
        isUserLogged = false;
        Log.d(TAG, "Logged out");
    }

    public MutableLiveData<FirebaseUser> getCurrentFirebaseUserLiveData() {
        return currentFirebaseUserLiveData;
    }
    public MutableLiveData<Boolean> getIsUserLoggedLiveData() {
        return isUserLoggedLiveData;
    }

    public MutableLiveData<String> getLoginFailureMessageLiveData() {
        return loginFailureMessageLiveData;
    }

    public Boolean getIsUserLogged() {
        return isUserLogged;
    }

    public void resetFailureMessages() {
        loginFailureMessageLiveData.setValue("");
    }
}
