package com.olkowskidaniel.teammanager.viewmodels.base;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.auth.FirebaseUser;
import com.olkowskidaniel.teammanager.R;
import com.olkowskidaniel.teammanager.managers.UserManager;
import com.olkowskidaniel.teammanager.model.User;
import com.olkowskidaniel.teammanager.repositories.UserRepository;

public class BaseViewModel extends AndroidViewModel {
    private static final String TAG = "BaseViewModel";
    private LiveData<Boolean> isUserLoggedLiveData = Transformations.map(UserManager.getInstance().getIsUserLoggedLiveData(), bool -> bool);
    private LiveData<FirebaseUser> currentFirebaseUserLiveData = Transformations.map(UserManager.getInstance().getCurrentFirebaseUserLiveData(), user -> user);
    private MutableLiveData<String> startActivityEvent;
    private User currentUser;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        startActivityEvent = new MutableLiveData<>();
    }

    public void onOptionsButtonClicked(int itemId) {
        switch (itemId) {
            case R.id.baseOptionsMenu_logout:
                UserManager.getInstance().logout();
                Log.d(TAG, "onLogoutButtonClicked");
                break;
            case R.id.baseOptionsMenu_deleteAccount:
                UserManager.getInstance().deleteAccount();
                UserManager.getInstance().logout();
                UserRepository.getInstance().deleteUserFromDatabase(currentUser.getEmail());
                break;
        }
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

    public LiveData<FirebaseUser> getCurrentFirebaseUserLiveData() {
        return currentFirebaseUserLiveData;
    }

    public void isUserLogged(Boolean bool) {
        if(!bool) {
            startActivityEvent.setValue("MainActivity");
            Log.d(TAG, "Starting Main Activity");
        }
    }

    public void setCurrentUser(FirebaseUser user) {
        currentUser = new User(user.getEmail());
        Log.d(TAG, "Current user set to: " + currentUser.getEmail());
    }
}
