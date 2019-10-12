package com.olkowskidaniel.teammanager.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.olkowskidaniel.teammanager.remotedata.FirebaseLogin;

public class UserRepository {

    private static UserRepository instance = null;

    private FirebaseLogin firebaseLogin;
    private MutableLiveData<String> firebaseFailureMessage;
    private MutableLiveData<String> firebaseMessage;


    private final Observer<String> firebaseMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            getFirebaseMessage().setValue(s);
        }
    };

    private final Observer<String> firebaseFailureMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            getFirebaseFailureMessage().setValue(s);
        }
    };


    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        firebaseLogin = new FirebaseLogin();
        firebaseMessage = new MutableLiveData<>();
        firebaseFailureMessage = new MutableLiveData<>();
        firebaseLogin.getFirebaseMessage().observeForever(firebaseMessageObserver);
        firebaseLogin.getFirebaseFailureMessage().observeForever(firebaseFailureMessageObserver);
    }

    public MutableLiveData<String> getFirebaseMessage() {
        return firebaseMessage;
    }

    public MutableLiveData<String> getFirebaseFailureMessage() {
        return firebaseFailureMessage;
    }

    public void requestRegisterWithEmail(String email, String password) {
        firebaseLogin.registerWithEmail(email, password);
    }

    public void requestLoginWithEmail(String email, String password) {
        firebaseLogin.signInWithEmail(email, password);
    }

    public void removeObservers() {
        firebaseLogin.getFirebaseMessage().removeObserver(firebaseMessageObserver);
        firebaseLogin.getFirebaseFailureMessage().removeObserver(firebaseFailureMessageObserver);
        Log.d("RegisterRepository", "observers removed");
    }
}
