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
import com.olkowskidaniel.teammanager.repositories.EmployeesRepository;
import com.olkowskidaniel.teammanager.repositories.UserRepository;
import com.olkowskidaniel.teammanager.utils.Activities;
import com.olkowskidaniel.teammanager.utils.Fragments;

public class BaseViewModel extends AndroidViewModel {
    private static final String TAG = "BaseViewModel";
    private LiveData<Boolean> isUserLoggedLiveData = Transformations.map(UserManager.getInstance().getIsUserLoggedLiveData(), bool -> bool);
    private LiveData<FirebaseUser> currentFirebaseUserLiveData = Transformations.map(UserManager.getInstance().getCurrentFirebaseUserLiveData(), user -> user);
    private MutableLiveData<Activities> startActivityEvent;
    private MutableLiveData<Fragments> startFragmentEvent;
    private MutableLiveData<Boolean> deleteUserConfirmationRequestEvent;
    private User currentUser;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        startActivityEvent = new MutableLiveData<>();
        startFragmentEvent = new MutableLiveData<>();
        deleteUserConfirmationRequestEvent = new MutableLiveData<>();
        setCurrentUser(UserManager.getInstance().getCurrentFirebaseUser());
    }

    public void onOptionsButtonClicked(int itemId) {
        switch (itemId) {
            case R.id.baseOptionsMenu_logout:
                UserManager.getInstance().logout();
                Log.d(TAG, "onLogoutButtonClicked");
                break;
            case R.id.baseOptionsMenu_deleteAccount:
                deleteUserConfirmationRequestEvent.setValue(true);
                break;
        }
    }

    public void onBottomNavItemClicked(int itemId) {
        switch(itemId) {
            case R.id.baseBottomNav_home:
                startFragmentEvent.setValue(Fragments.Home);
                break;
            case R.id.baseBottomNav_personnel:
                startFragmentEvent.setValue(Fragments.Personnel);
                break;
            case R.id.baseBottomNav_tasks:
                startFragmentEvent.setValue(Fragments.Tasks);
                break;
            case R.id.baseBottomNav_schedule:
                startFragmentEvent.setValue(Fragments.Schedule);
                break;
        }
    }

    public LiveData<Boolean> getIsUserLoggedLiveData() {
        return isUserLoggedLiveData;
    }

    public LiveData<Activities> getStartActivityEvent() {
        return startActivityEvent;
    }

    public LiveData<Fragments> getStartFragmentEvent() {
        return startFragmentEvent;
    }

    public LiveData<Boolean> getDeleteUserConfirmationRequestEvent() {
        return deleteUserConfirmationRequestEvent;
    }

    public LiveData<FirebaseUser> getCurrentFirebaseUserLiveData() {
        return currentFirebaseUserLiveData;
    }

    public void isUserLogged(Boolean bool) {
        if(!bool) {
            startActivityEvent.setValue(Activities.Main);
            Log.d(TAG, "Starting Main Activity");
        }
    }

    public void setCurrentUser(FirebaseUser user) {
        currentUser = new User(user.getEmail());
        UserRepository.getInstance().setCurrentUser(currentUser);
        EmployeesRepository.getInstance().setCurrentUser(currentUser);
        Log.d(TAG, "Current user set to: " + currentUser.getEmail());
    }

    public void onDeleteAccountConfirmedByUser(Boolean confirmationResult) {
        if(confirmationResult) {
            UserRepository.getInstance().deleteUserFromDatabase(currentUser.getEmail());
            UserManager.getInstance().deleteAccount();
            deleteUserConfirmationRequestEvent.setValue(false);
        } else {
            deleteUserConfirmationRequestEvent.setValue(false);
        }

    }
}
