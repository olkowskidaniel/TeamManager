package com.olkowskidaniel.teammanager.viewmodels.base;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.managers.UserManager;

public class BaseViewModel extends AndroidViewModel {
    private static final String TAG = "BaseViewModel";
    private LiveData<Boolean> isUserLoggedLiveData = Transformations.map(UserManager.getInstance().getIsUserLoggedLiveData(), bool -> bool);
    private MutableLiveData<String> startActivityEvent;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        startActivityEvent = new MutableLiveData<>();
    }

    public void onLogoutButtonClicked() {
        UserManager.getInstance().logout();
        Log.d("BaseViewModel", "onLogoutButtonClicked");
    }


    public void onBottomNavItemClicked(int itemId) {
        switch(itemId) {
            case R.id.baseBottomNav_home:
                break;
            case R.id.baseBottomNav_personnel:
                break;
            case R.id.baseBottomNav_tasks:
                break;
            case R.id.baseBottomNav_schedule:
                break;
        }
    }

    public LiveData<Boolean> getIsUserLoggedLiveData() {
        return isUserLoggedLiveData;
    }

    public MutableLiveData<String> getStartActivityEvent() {
        return startActivityEvent;
    }

    public void isUserLogged(Boolean bool) {
        if(!bool) {
            startActivityEvent.setValue("MainActivity");
            Log.d(TAG, "Starting Main Activity");
        }
    }
}
