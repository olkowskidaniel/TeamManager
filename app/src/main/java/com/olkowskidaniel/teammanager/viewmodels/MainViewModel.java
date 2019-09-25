package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.olkowskidaniel.teammanager.repositories.MainRepository;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<String> mainViewModelMessage;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainViewModelMessage = new MutableLiveData<>();
    }

    public void onActivityCreated() {
        if(MainRepository.getInstance().getCurrentUser() == null) {
            getMainViewModelMessage().setValue("startLoginActivity");
        }
    }

    public MutableLiveData<String> getMainViewModelMessage() {
        return mainViewModelMessage;
    }
}
