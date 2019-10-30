package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.olkowskidaniel.teammanager.managers.UserManager;
import com.olkowskidaniel.teammanager.utils.Activities;


public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";
    private LiveData<Boolean> isUserLoggedLiveData = Transformations.map(UserManager.getInstance().getIsUserLoggedLiveData(), bool -> bool);
    private MutableLiveData<Activities> startActivityEvent;

    public MainViewModel(@NonNull Application application) {
        super(application);
        startActivityEvent = new MutableLiveData<>();
    }

    public void onActivityStarted() {
        if(UserManager.getInstance().getIsUserLogged()) {
            startActivityEvent.setValue(Activities.Base);
        } else if (!UserManager.getInstance().getIsUserLogged()) {
            startActivityEvent.setValue(Activities.Login);
        }
    }

    public LiveData<Boolean> getIsUserLoggedLiveData() {
        return isUserLoggedLiveData;
    }

    public LiveData<Activities> getStartActivityEvent() {
        return startActivityEvent;
    }

    public void isUserLogged(Boolean bool) {
        if(bool) {
            startActivityEvent.setValue(Activities.Base);
        }
        else {
            startActivityEvent.setValue(Activities.Login);
        }
    }
}
