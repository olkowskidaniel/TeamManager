package com.olkowskidaniel.teammanager.managers;

import android.util.Log;

import androidx.lifecycle.LiveData;
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
    private MutableLiveData<String> registerFailureMessageLiveData;
    private MutableLiveData<Boolean> userRegisteredEventLiveData;
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
        isUserLoggedLiveData = new MutableLiveData<>();
        currentFirebaseUserLiveData = new MutableLiveData<>();
        loginFailureMessageLiveData = new MutableLiveData<>();
        registerFailureMessageLiveData = new MutableLiveData<>();
        userRegisteredEventLiveData = new MutableLiveData<>();

        if(currentFirebaseUser == null) {
            getIsUserLoggedLiveData().setValue(false);
            isUserLogged = false;
        } else {
            getIsUserLoggedLiveData().setValue(true);
            isUserLogged = true;
        }
        resetFailureMessages();
    }

    public void signInWithEmail(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, email + " logged in");
                currentFirebaseUser = firebaseAuth.getCurrentUser();
                currentFirebaseUserLiveData.setValue(currentFirebaseUser);
                getIsUserLoggedLiveData().setValue(true);
                isUserLogged = true;
                userRegisteredEventLiveData.setValue(false);
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
                userRegisteredEventLiveData.setValue(true);
            } else {
                registerFailureMessageLiveData.setValue(task.getException().getMessage());
            }
        });
    }

    public void deleteAccount() {
        currentFirebaseUser.delete().addOnCompleteListener(task -> {
            Log.d(TAG, "Account " + currentFirebaseUser.getEmail() + " deleted");
            isUserLoggedLiveData.setValue(false);
        });
    }

    public void logout() {
        firebaseAuth.signOut();
        currentFirebaseUser = null;
        isUserLoggedLiveData.setValue(false);
        isUserLogged = false;
        Log.d(TAG, "Logged out");
    }

    public LiveData<FirebaseUser> getCurrentFirebaseUserLiveData() {
        return currentFirebaseUserLiveData;
    }
    public MutableLiveData<Boolean> getIsUserLoggedLiveData() {
        return isUserLoggedLiveData;
    }

    public MutableLiveData<String> getLoginFailureMessageLiveData() {
        return loginFailureMessageLiveData;
    }

    public MutableLiveData<String> getRegisterFailureMessageLiveData() {
        return registerFailureMessageLiveData;
    }

    public MutableLiveData<Boolean> getUserRegisteredEventLiveData() {
        return  userRegisteredEventLiveData;
    }

    public Boolean getIsUserLogged() {
        return isUserLogged;
    }

    public void resetFailureMessages() {
        loginFailureMessageLiveData.setValue("");
        registerFailureMessageLiveData.setValue("");
    }

    public void resetEvents() {
        userRegisteredEventLiveData.setValue(false);
    }

    public void requestPasswordMismatchMessage() {
        registerFailureMessageLiveData.setValue("Passwords don't match");
    }

    public FirebaseUser getCurrentFirebaseUser() {
        return currentFirebaseUser;
    }
}
