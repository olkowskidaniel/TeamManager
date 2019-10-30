package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.olkowskidaniel.teammanager.managers.UserManager;
import com.olkowskidaniel.teammanager.model.User;
import com.olkowskidaniel.teammanager.repositories.UserRepository;

public class RegisterViewModel extends AndroidViewModel {

    private LiveData<String> registerFailureMessageLiveData = Transformations.map(UserManager.getInstance().getRegisterFailuerMessageLiveData(), message -> message);
    private LiveData<Boolean> userRegisteredEventLiveData = Transformations.map(UserManager.getInstance().getUserRegisteredEventLiveData(), bool -> bool);
    private MutableLiveData<String> startActivityEvent;
    private User registeringUser;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        startActivityEvent = new MutableLiveData<>();
    }

    public void onRegisterSendBtnClicked(String email, String password, String rePassword) {
        UserManager.getInstance().resetFailureMessages();
        if (password.equals(rePassword)) {
            registeringUser = new User(email);
            UserManager.getInstance().registerWithEmail(email, password);
        } else {
            UserManager.getInstance().requestPasswordMismatchMessage();
        }
    }

    public LiveData<String> getRegisterFailureMessageLiveData() {
        return registerFailureMessageLiveData;
    }

    public LiveData<String> getStartActivityEvent() {
        return startActivityEvent;
    }

    public LiveData<Boolean> getUserRegisteredEventLiveData() {
        return userRegisteredEventLiveData;
    }

    public void onUserRegisterSuccess() {
        UserRepository.getInstance().addUserToDatabase(registeringUser.getEmail());
        startActivityEvent.setValue("MainActivity");
        UserManager.getInstance().resetEvents();
    }
}