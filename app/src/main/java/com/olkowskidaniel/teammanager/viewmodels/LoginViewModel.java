package com.olkowskidaniel.teammanager.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.olkowskidaniel.teammanager.repositories.LoginRepository;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> firebaseLoginMessageLiveData;
    private MutableLiveData<String> firebaseLoginFailureMesageLiveData;
    private MutableLiveData<String> loginViewModelMessage;

    //observers
    private final Observer<String> firebaseLoginMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            getFirebaseLoginMessageLiveData().setValue(s);
            if(s.equals("userLoginFailure")) {
                onLoginFailure();
            }

            if(s.equals("userLoginSuccess")) {
                onLoginSuccess();
            }
        }
    };

    private final Observer<String> firebaseLoginFailureMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            getFirebaseLoginFailureMesageLiveData().setValue(s);
        }
    };

    public LoginViewModel(@NonNull Application application) {
        super(application);
        firebaseLoginMessageLiveData = new MutableLiveData<>();
        firebaseLoginFailureMesageLiveData = new MutableLiveData<>();
        loginViewModelMessage = new MutableLiveData<>();
        LoginRepository.getInstance().getFirebaseLoginMessage().observeForever(firebaseLoginMessageObserver);
        LoginRepository.getInstance().getFirebaseLoginFailureMessage().observeForever(firebaseLoginFailureMessageObserver);
        getLoginViewModelMessage().setValue("defaultLoginViewModelMessage");
    }

    public void onLoginSendButtonClicked(String email, String password) {
        LoginRepository.getInstance().onLoginRequest(email, password);
    }

    public void onActivityDestroy() {
        LoginRepository.getInstance().removeObservers();
        removeObservers();
    }

    private void removeObservers() {
        LoginRepository.getInstance().getFirebaseLoginMessage().removeObserver(firebaseLoginMessageObserver);
        LoginRepository.getInstance().getFirebaseLoginFailureMessage().removeObserver(firebaseLoginFailureMessageObserver);
        Log.d("LoginViewModel", "observers removed");
    }

    private void onLoginFailure() {
        getLoginViewModelMessage().setValue("userLoginFailure");
    }

    private void onLoginSuccess() {
        getLoginViewModelMessage().setValue("userLoginSuccess");
    }

    public MutableLiveData<String> getFirebaseLoginMessageLiveData() {
        return firebaseLoginMessageLiveData;
    }

    public MutableLiveData<String> getFirebaseLoginFailureMesageLiveData() {
        return firebaseLoginFailureMesageLiveData;
    }

    public MutableLiveData<String> getLoginViewModelMessage() {
        return loginViewModelMessage;
    }
}
