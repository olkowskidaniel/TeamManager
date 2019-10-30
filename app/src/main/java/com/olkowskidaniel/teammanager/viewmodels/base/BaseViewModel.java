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
    private MutableLiveData<String> startFragmentEvent;
    private User currentUser;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        startActivityEvent = new MutableLiveData<>();
        startFragmentEvent = new MutableLiveData<>();
    }

    public void onOptionsButtonClicked(int itemId) {
        switch (itemId) {
            case R.id.baseOptionsMenu_logout:
                UserManager.getInstance().logout();
                Log.d(TAG, "onLogoutButtonClicked");
                break;
            case R.id.baseOptionsMenu_deleteAccount:
                UserRepository.getInstance().deleteUserFromDatabase(currentUser.getEmail());
                UserManager.getInstance().deleteAccount();
                break;
        }
    }

    public void onBottomNavItemClicked(int itemId) {
        switch(itemId) {
            case R.id.baseBottomNav_home:
                startFragmentEvent.setValue("HomeFragment");
                break;
            case R.id.baseBottomNav_personnel:
                startFragmentEvent.setValue("PersonnelFragment");
                break;
            case R.id.baseBottomNav_tasks:
                startFragmentEvent.setValue("TasksFragment");
                break;
            case R.id.baseBottomNav_schedule:
                startFragmentEvent.setValue("ScheduleFragment");
                break;
        }
    }

    public LiveData<Boolean> getIsUserLoggedLiveData() {
        return isUserLoggedLiveData;
    }

    public LiveData<String> getStartActivityEvent() {
        return startActivityEvent;
    }

    public LiveData<String> getStartFragmentEvent() {
        return startFragmentEvent;
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
