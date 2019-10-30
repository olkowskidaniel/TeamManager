package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.olkowskidaniel.teammanager.managers.UserManager;
import com.olkowskidaniel.teammanager.utils.Activities;


public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";
    private LiveData<Boolean> isUserLoggedLiveData = Transformations.map(UserManager.getInstance().getIsUserLoggedLiveData(), bool -> bool);
    private LiveData<String> loginFailureMessageLiveData = Transformations.map(UserManager.getInstance().getLoginFailureMessageLiveData(), message -> message);

    private MutableLiveData<Activities> startActivityEvent;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        startActivityEvent = new MutableLiveData<>();
    }

    public void onLoginSendButtonClicked(String email, String password) {
        UserManager.getInstance().signInWithEmail(email, password);
    }

    public void onLoginRegisterButtonClicked() {
        startActivityEvent.setValue(Activities.Register);
        UserManager.getInstance().resetFailureMessages();
    }

    public LiveData<Boolean> getIsUserLoggedLiveData() {
        return isUserLoggedLiveData;
    }

    public LiveData<String> getLoginFailureMessageLiveData() {
        return loginFailureMessageLiveData;
    }

    public LiveData<Activities> getStartActivityEvent() {
        return startActivityEvent;
    }

    public void isUserLogged(Boolean bool) {
        if(bool) {
            startActivityEvent.setValue(Activities.Main);
            Log.d(TAG, "Starting MainActivity");
        }
    }
}
