package com.olkowskidaniel.teammanager.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.olkowskidaniel.teammanager.remotedata.FirebaseLogin;

public class LoginRepository {
    private static LoginRepository instance = null;
    private FirebaseLogin firebaseLogin;
    private LiveData<String> firebaseLoginFailureMessage;
    //private final LiveData<String> firebaseLoginMessage = Transformations.map(firebaseLogin.getFirebaseLoginMessage(), message -> message);;


    public static LoginRepository getInstance() {
        if(instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    private LoginRepository () {
        firebaseLogin = new FirebaseLogin();
        firebaseLoginFailureMessage = Transformations.map(firebaseLogin.getFirebaseLoginFailureMessage(), this::mapFunction);
    }

    private String mapFunction(String message) {
        Log.d("LoginRepository", message);
        return message;
    }


    public void onLoginRequest(String email, String password) {
        firebaseLogin.signInWithEmail(email, password);
    }
//
//    public LiveData<String> getFirebaseLoginMessage() {
//        return firebaseLoginMessage;
//    }

    public LiveData<String> getFirebaseLoginFailureMessage() {
        return firebaseLoginFailureMessage;
    }
}
