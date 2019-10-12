package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.olkowskidaniel.teammanager.repositories.UserRepository;

public class RegisterViewModel extends AndroidViewModel {
    //TODO: put messages from RegisterRepository inside RegisterViewModel
    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public void onRegisterSendBtnClicked(String email, String password, String rePassword) {
        if(password.equals(rePassword)) {
            UserRepository.getInstance().requestRegisterWithEmail(email, password);

        }
    }
}
