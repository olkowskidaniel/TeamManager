package com.olkowskidaniel.teammanager.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.olkowskidaniel.teammanager.model.Employee;
import com.olkowskidaniel.teammanager.model.User;
import com.olkowskidaniel.teammanager.remotedata.Firestore;

import java.util.List;

public class EmployeesRepository {

    private static EmployeesRepository instance = null;
    private static final String TAG = "EmployeesRepository";
    private LiveData<List<Employee>> employeesListLiveData;
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
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        Log.d(TAG, "current user set to: " + currentUser.getEmail());
    }

    public void addEmplToDatabase(Employee employee) {
        firestore.addEmployeeToUsersEmployeesCollection(currentUser.getEmail(), employee);
    }

    public LiveData<List<Employee>> getEmployeesListLiveData() {
        return employeesListLiveData;
    }

    public void getAllEmployees() {
        firestore.getAllEmployees(currentUser.getEmail());
    }

    public void deleteEmployeeFromDatabase(String currentEmpId) {
        firestore.deleteEmplFromDatabase(currentUser.getEmail(), currentEmpId);
    }
}
