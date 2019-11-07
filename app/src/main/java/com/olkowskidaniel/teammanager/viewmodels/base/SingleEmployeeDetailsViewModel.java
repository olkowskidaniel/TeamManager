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

import java.io.Serializable;

public class SingleEmployeeDetailsViewModel extends AndroidViewModel {
    private static final String TAG = "SingleEmployeeDetailsViewModel";
    private MutableLiveData<String> employeeNameLiveData;
    private MutableLiveData<String> employeeLastNameLiveData;
    private MutableLiveData<String> fragmentTitleLiveData;
    private LiveData<String> employeeNameUpdatedEvent = Transformations.map(EmployeesRepository.getInstance().getEmployeeNameUpdatedEvent(), name -> name);

    private Employee employee;

    public SingleEmployeeDetailsViewModel(@NonNull Application application) {
        super(application);
        employeeNameLiveData = new MutableLiveData<>();
        employeeLastNameLiveData = new MutableLiveData<>();
        fragmentTitleLiveData = new MutableLiveData<>();
    }

    public void onFragmentCreated(Serializable employeeSerializable) {
        employee = (Employee) employeeSerializable;
        setView(employee);
        Log.d(TAG, "Fragment created with employee: " + employee.getName());
    }

    private void setView(Employee employee) {
        fragmentTitleLiveData.setValue(employee.getName() + " " + employee.getLastName());
        employeeNameLiveData.setValue(employee.getName());
        employeeLastNameLiveData.setValue(employee.getLastName());
    }

    public LiveData<String> getEmployeeNameLiveData() {
        return employeeNameLiveData;
    }

    public LiveData<String> getEmployeeLastNameLiveData() {
        return employeeLastNameLiveData;
    }

    public LiveData<String> getFragmentTitleLiveData() {
        return fragmentTitleLiveData;
    }

    public LiveData<String> getEmployeeNameUpdatedEvent() {
        return employeeNameUpdatedEvent;
    }

    public void onNameSaveButtonClicked(String emplId, String name) {
        EmployeesRepository.getInstance().updateEmployeeName(emplId, name);
    }
}
