package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.olkowskidaniel.teammanager.repositories.UserRepository;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<String> mainViewModelMessage;
    private MutableLiveData<String> userEmailLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainViewModelMessage = new MutableLiveData<>();
        userEmailLiveData = new MutableLiveData<>();
    }

    public void onActivityStarted() {
        getMainViewModelMessage().setValue("activityStarted");
        if(UserRepository.getInstance().getCurrentUser() == null) {
            getMainViewModelMessage().setValue("startLoginActivity");
        } else {
            getMainViewModelMessage().setValue("startBaseActivity");
            getUserEmailLiveData().setValue(UserRepository.getInstance().getCurrentUser().getEmail());
        }
    }

    public MutableLiveData<String> getMainViewModelMessage() {
        return mainViewModelMessage;
    }
    public MutableLiveData<String> getUserEmailLiveData() {return userEmailLiveData;}
}
