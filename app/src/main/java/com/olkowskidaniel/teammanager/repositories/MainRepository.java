package com.olkowskidaniel.teammanager.repositories;


import com.olkowskidaniel.teammanager.model.User;
import com.olkowskidaniel.teammanager.remotedata.FirebaseLogin;

public class MainRepository {

    private static MainRepository instance = null;

    private FirebaseLogin firebaseLogin;

    public static MainRepository getInstance() {
        if(instance == null) {
            instance = new MainRepository();
        }
        return instance;
    }
    private  MainRepository() {
        firebaseLogin = new FirebaseLogin();
    }

    public User getCurrentUser() {
        if(firebaseLogin.getCurrentFirebaseUser() == null) {
            return null;
        } else {
            User currentUser = new User (firebaseLogin.getCurrentFirebaseUser().getEmail());
            return currentUser;
        }
    }
}
