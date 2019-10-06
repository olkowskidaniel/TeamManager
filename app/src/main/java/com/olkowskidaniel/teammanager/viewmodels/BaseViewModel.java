package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.olkowskidaniel.teammanager.repositories.BaseRepository;

public class BaseViewModel extends AndroidViewModel {
    private MutableLiveData<String> baseViewModelMessage;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        baseViewModelMessage = new MutableLiveData<>();
    }

    public void onLogoutButtonClicked() {
        BaseRepository.getInstance().onLogoutRequest();
    }

    public MutableLiveData<String> getBaseViewModelMessage() {
        return baseViewModelMessage;
    }

    public void onActivityStarted() {
        getBaseViewModelMessage().setValue("baseActivityStarted");
    }
}
