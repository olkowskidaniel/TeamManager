package com.olkowskidaniel.teammanager.viewmodels.base;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.olkowskidaniel.teammanager.model.Employee;
import com.olkowskidaniel.teammanager.repositories.EmployeesRepository;
import com.olkowskidaniel.teammanager.utils.Fragments;

import java.util.List;

public class PersonnelViewModel extends AndroidViewModel {

    private static final String TAG = "PersonnelViewModel";
    private MutableLiveData<Fragments> startFragmentEvent;
    private LiveData<List<Employee>> employeesListLiveData = Transformations.map(EmployeesRepository.getInstance().getEmployeesListLiveData(), list -> list);

    public PersonnelViewModel(@NonNull Application application) {
        super(application);
        startFragmentEvent = new MutableLiveData<>();
    }

    public LiveData<Fragments> getStartFragmentEvent() {
        return startFragmentEvent;
    }

    public void onAddEmplBtnClicked() {
        startFragmentEvent.setValue(Fragments.AddNewEmpl);
    }
    
    public LiveData<List<Employee>> getEmployeesListLiveData() {
        return employeesListLiveData;
    }

    public void onFragmentStarted() {
        EmployeesRepository.getInstance().getAllEmployees();
        Log.d(TAG, "Fragment started");
    }
}
