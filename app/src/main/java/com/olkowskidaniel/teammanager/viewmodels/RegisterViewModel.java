package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.olkowskidaniel.teammanager.managers.UserManager;
import com.olkowskidaniel.teammanager.repositories.UserRepository;

public class RegisterViewModel extends AndroidViewModel {


    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public void onRegisterSendBtnClicked(String email, String password, String rePassword) {
        if (password.equals(rePassword)) {
            UserManager.getInstance().registerWithEmail(email, password);
        }
    }

    private void addUserToDatabase(String email) {
        UserRepository.getInstance().addUserToDatabase(email);
    }
}