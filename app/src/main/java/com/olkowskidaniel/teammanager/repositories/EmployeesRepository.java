package com.olkowskidaniel.teammanager.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.auth.FirebaseUser;
import com.olkowskidaniel.teammanager.model.Employee;
import com.olkowskidaniel.teammanager.model.User;
import com.olkowskidaniel.teammanager.remotedata.Firestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, Object> emplMap = new HashMap<>();
        emplMap.put("name", employee.getName());
        emplMap.put("lastName", employee.getLastName());
        firestore.addEmployeeToUsersEmployeesCollection(currentUser.getEmail(), emplMap);
    }

    public LiveData<List<Employee>> getEmployeesListLiveData() {
        return employeesListLiveData;
    }

    public void getAllEmployees() {
        firestore.getAllEmployees(currentUser.getEmail());
    }
}
