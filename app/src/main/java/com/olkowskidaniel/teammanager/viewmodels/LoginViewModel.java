package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.olkowskidaniel.teammanager.repositories.LoginRepository;

public class LoginViewModel extends AndroidViewModel {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void onLoginSendButtonClicked(String email, String password) {
        LoginRepository.getInstance().onLoginRequest(email, password);
    }
}
