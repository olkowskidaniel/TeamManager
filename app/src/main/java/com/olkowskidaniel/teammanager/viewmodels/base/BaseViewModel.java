package com.olkowskidaniel.teammanager.viewmodels.base;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.model.User;
import com.olkowskidaniel.teammanager.repositories.UserRepository;

public class BaseViewModel extends AndroidViewModel {

    private MutableLiveData<String> baseViewModelMessage;
    private MutableLiveData<User> currentUserLiveData;

    private final Observer<String> firebaseMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            if(s.equals("userLoggedOut")) {
                startMainActivity();
                Log.d("BaseViewModel", "starting MainActivity");
            }
        }
    };

    public BaseViewModel(@NonNull Application application) {
        super(application);
        baseViewModelMessage = new MutableLiveData<>();
        currentUserLiveData = new MutableLiveData<>();
        UserRepository.getInstance().getFirebaseMessage().observeForever(firebaseMessageObserver);
    }

    public void onLogoutButtonClicked() {
        UserRepository.getInstance().onLogoutRequest();
    }

    public MutableLiveData<String> getBaseViewModelMessage() {
        return baseViewModelMessage;
    }

    public void onActivityStarted() {
        getBaseViewModelMessage().setValue("baseActivityStarted");
        getCurrentUserLiveData().setValue(UserRepository.getInstance().getCurrentUser());
    }

    private void startMainActivity() {
        getBaseViewModelMessage().setValue("startMainActivity");
    }

    public MutableLiveData<User> getCurrentUserLiveData() {
        return currentUserLiveData;
    }

    public void removeObservers() {
        UserRepository.getInstance().getFirebaseMessage().removeObserver(firebaseMessageObserver);
        UserRepository.getInstance().removeObservers();
    }

    public void onBottomNavItemClicked(int itemId) {
        switch(itemId) {
            case R.id.baseBottomNav_home:
                getBaseViewModelMessage().setValue("startHomeFragment");
                break;
            case R.id.baseBottomNav_personnel:
                getBaseViewModelMessage().setValue("startPersonnelFragment");
                break;
            case R.id.baseBottomNav_tasks:
                getBaseViewModelMessage().setValue("startTasksFragment");
                break;
            case R.id.baseBottomNav_schedule:
                getBaseViewModelMessage().setValue("startScheduleFragment");
                break;
        }
    }
}
