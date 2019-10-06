package com.olkowskidaniel.teammanager.repositories;

        import com.olkowskidaniel.teammanager.remotedata.FirebaseLogin;

public class BaseRepository {
    private static BaseRepository instance = null;
    FirebaseLogin firebaseLogin;

    public static BaseRepository getInstance() {
        if(instance == null) {
            instance = new BaseRepository();
        }
        return instance;
    }

    private BaseRepository () {
        firebaseLogin = new FirebaseLogin();
    }

    public void onLogoutRequest() {
        firebaseLogin.logout();
    }
}
