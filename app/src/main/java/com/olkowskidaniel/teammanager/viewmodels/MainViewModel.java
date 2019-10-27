package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.olkowskidaniel.teammanager.managers.UserManager;


public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";
    private LiveData<Boolean> isUserLoggedLiveData = Transformations.map(UserManager.getInstance().getIsUserLoggedLiveData(), bool -> bool);
    private MutableLiveData<String> startActivityEvent;

    public MainViewModel(@NonNull Application application) {
        super(application);
        startActivityEvent = new MutableLiveData<>();
    }

    public void onActivityStarted() {
        if(UserManager.getInstance().getIsUserLogged()) {
            startActivityEvent.setValue("BaseActivity");
        } else if (!UserManager.getInstance().getIsUserLogged()) {
            startActivityEvent.setValue("LoginActivity");
        }
    }

    public LiveData<Boolean> getIsUserLoggedLiveData() {
        return isUserLoggedLiveData;
    }

    public MutableLiveData<String> getStartActivityEvent() {
        return startActivityEvent;
    }

    public void isUserLogged(Boolean bool) {
        if(bool) {
            startActivityEvent.setValue("BaseActivity");
        }
        else {
            startActivityEvent.setValue("LoginActivity");
        }
    }
}
