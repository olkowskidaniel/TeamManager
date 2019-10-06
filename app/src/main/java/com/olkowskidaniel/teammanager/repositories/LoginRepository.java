package com.olkowskidaniel.teammanager.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.olkowskidaniel.teammanager.remotedata.FirebaseLogin;

public class LoginRepository {
    private static LoginRepository instance = null;
    private FirebaseLogin firebaseLogin;
    private MutableLiveData<String> firebaseLoginFailureMessage;
    private MutableLiveData<String> firebaseLoginMessage;

    //observers
    private final Observer<String> firebaseLoginFailureMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            getFirebaseLoginFailureMessage().setValue(s);
            Log.d("LoginRepository", s);
        }
    };

    private final Observer<String> firebaseLoginMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            getFirebaseLoginMessage().setValue(s);
        }
    };

    public static LoginRepository getInstance() {
        if(instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    private LoginRepository () {
        firebaseLogin = new FirebaseLogin();
        firebaseLoginFailureMessage = new MutableLiveData<>();
        firebaseLoginMessage = new MutableLiveData<>();

        firebaseLogin.getFirebaseLoginFailureMessage().observeForever(firebaseLoginFailureMessageObserver);

        firebaseLogin.getFirebaseLoginMessage().observeForever(firebaseLoginMessageObserver);
    }

    private String mapFunction(String message) {
        Log.d("LoginRepository", message);
        return message;
    }


    public void onLoginRequest(String email, String password) {
        firebaseLogin.signInWithEmail(email, password);
    }

    public MutableLiveData<String> getFirebaseLoginMessage() {
        return firebaseLoginMessage;
    }

    public MutableLiveData<String> getFirebaseLoginFailureMessage() {
        return firebaseLoginFailureMessage;
    }

    public void removeObservers() {
        firebaseLogin.getFirebaseLoginMessage().removeObserver(firebaseLoginMessageObserver);
        firebaseLogin.getFirebaseLoginFailureMessage().removeObserver(firebaseLoginFailureMessageObserver);
        Log.d("LoginRepository", "observers removed");
    }
}
