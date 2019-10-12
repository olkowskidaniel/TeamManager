package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.olkowskidaniel.teammanager.repositories.UserRepository;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> firebaseMessageLiveData;
    private MutableLiveData<String> firebaseFailureMesageLiveData;
    private MutableLiveData<String> loginViewModelMessage;

    //observers
    private final Observer<String> firebaseMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            getFirebaseMessageLiveData().setValue(s);
            if(s.equals("userLoginFailure")) {
                onLoginFailure();
            }

            if(s.equals("userLoginSuccess")) {
                onLoginSuccess();
            }
        }
    };

    private final Observer<String> firebaseFailureMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            getFirebaseFailureMesageLiveData().setValue(s);
        }
    };

    public LoginViewModel(@NonNull Application application) {
        super(application);
        firebaseMessageLiveData = new MutableLiveData<>();
        firebaseFailureMesageLiveData = new MutableLiveData<>();
        loginViewModelMessage = new MutableLiveData<>();
        UserRepository.getInstance().getFirebaseMessage().observeForever(firebaseMessageObserver);
        UserRepository.getInstance().getFirebaseFailureMessage().observeForever(firebaseFailureMessageObserver);
        getLoginViewModelMessage().setValue("defaultLoginViewModelMessage");
    }

    public void onLoginSendButtonClicked(String email, String password) {
        UserRepository.getInstance().requestLoginWithEmail(email, password);
    }

    public void onLoginRegisterButtonClicked() {
        getLoginViewModelMessage().setValue("startRegisterActivity");
    }

    public void onActivityDestroy() {
        UserRepository.getInstance().removeObservers();
        removeObservers();
    }

    private void removeObservers() {
        UserRepository.getInstance().getFirebaseMessage().removeObserver(firebaseMessageObserver);
        UserRepository.getInstance().getFirebaseFailureMessage().removeObserver(firebaseFailureMessageObserver);
        Log.d("LoginViewModel", "observers removed");
    }

    private void onLoginFailure() {
        getLoginViewModelMessage().setValue("userLoginFailure");
    }

    private void onLoginSuccess() {
        getLoginViewModelMessage().setValue("userLoginSuccess");
    }

    public MutableLiveData<String> getFirebaseMessageLiveData() {
        return firebaseMessageLiveData;
    }

    public MutableLiveData<String> getFirebaseFailureMesageLiveData() {
        return firebaseFailureMesageLiveData;
    }

    public MutableLiveData<String> getLoginViewModelMessage() {
        return loginViewModelMessage;
    }

}
