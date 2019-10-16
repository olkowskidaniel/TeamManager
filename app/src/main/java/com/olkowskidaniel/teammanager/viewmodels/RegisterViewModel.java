package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.olkowskidaniel.teammanager.repositories.UserRepository;

public class RegisterViewModel extends AndroidViewModel {

    public MutableLiveData<String> firebaseMessageLiveData;
    public MutableLiveData<String> firebaseFailureMessageLiveData;
    public MutableLiveData<String> registerViewModelMessageLiveData;

    private final Observer<String> firebaseMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            if(s.equals("registerSuccess")) {
                getRegisterViewModelMessageLiveData().setValue("startLoginActivity");
            }
       }
    };

    private final Observer<String> firebaseFailureMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            getRegisterViewModelMessageLiveData().setValue(s);
        }
    };

    public RegisterViewModel(@NonNull Application application) {
        super(application);

        firebaseMessageLiveData = new MutableLiveData<>();
        firebaseFailureMessageLiveData = new MutableLiveData<>();
        registerViewModelMessageLiveData = new MutableLiveData<>();
        UserRepository.getInstance().getFirebaseMessage().observeForever(firebaseMessageObserver);
        UserRepository.getInstance().getFirebaseFailureMessage().observeForever(firebaseFailureMessageObserver);
    }

    public void onRegisterSendBtnClicked(String email, String password, String rePassword) {
        if(password.equals(rePassword)) {
            getRegisterViewModelMessageLiveData().setValue("clearMessageTV");
            UserRepository.getInstance().requestRegisterWithEmail(email, password);
        } else {
            getRegisterViewModelMessageLiveData().setValue("PasswordMissmatch");
        }
    }

    public MutableLiveData<String> getFirebaseMessageLiveData() {
        return firebaseMessageLiveData;
    }

    public MutableLiveData<String> getFirebaseFailureMessageLiveData() {
        return firebaseFailureMessageLiveData;
    }

    public MutableLiveData<String> getRegisterViewModelMessageLiveData() {
        return registerViewModelMessageLiveData;
    }
}
