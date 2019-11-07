package com.olkowskidaniel.teammanager.repositories;

import android.database.sqlite.SQLiteBlobTooBigException;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.olkowskidaniel.teammanager.model.Employee;
import com.olkowskidaniel.teammanager.model.NewEmployee;
import com.olkowskidaniel.teammanager.model.User;
import com.olkowskidaniel.teammanager.remotedata.Firestore;

import java.util.List;

public class EmployeesRepository {

    private static EmployeesRepository instance = null;
    private static final String TAG = "EmployeesRepository";
    private LiveData<List<Employee>> employeesListLiveData;
    private LiveData<Boolean> employeeListChangedEvent;
    private LiveData<String> employeeNameUpdatedEvent;
    private Firestore firestore;
    private User currentUser;

    public static EmployeesRepository getInstance() {
        if (instance == null) {
            instance = new EmployeesRepository();
        }
        return instance;
    }

    private EmployeesRepository() {
        firestore = new Firestore();
        employeesListLiveData = Transformations.map(firestore.getEmployeeListLiveData(), list -> list);
        employeeListChangedEvent = Transformations.map(firestore.getEmployeeListChangedEvent(), bool -> bool);
        employeeNameUpdatedEvent = Transformations.map(firestore.getEmployeeNameUpdatedEvent(), name -> name);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        Log.d(TAG, "current user set to: " + currentUser.getEmail());
    }

    public void addEmplToDatabase(String name, String lastName) {
        NewEmployee newEmployee = new NewEmployee(name, lastName);
        firestore.addEmployeeToUsersEmployeesCollection(currentUser.getEmail(), newEmployee);
    }

    public LiveData<List<Employee>> getEmployeesListLiveData() {
        return employeesListLiveData;
    }

    public LiveData<Boolean> getEmployeeListChangedEvent() {
        return employeeListChangedEvent;
    }

    public LiveData<String> getEmployeeNameUpdatedEvent() {
        return employeeNameUpdatedEvent;
    }

    public void getAllEmployees() {
        firestore.getAllEmployees(currentUser.getEmail());
    }

    public void deleteEmployeeFromDatabase(String currentEmpId) {
        firestore.deleteEmplFromDatabase(currentUser.getEmail(), currentEmpId);
    }

    public void updateEmployeeName(String emplId, String name) {
        firestore.updateEmployeeName(currentUser.getEmail(), emplId, name);
    }
}
