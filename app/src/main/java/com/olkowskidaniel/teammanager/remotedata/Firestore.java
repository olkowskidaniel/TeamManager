package com.olkowskidaniel.teammanager.remotedata;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.olkowskidaniel.teammanager.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Firestore {

    private static final String TAG = "Firestore";
    private FirebaseFirestore firebaseFirestore;
    private MutableLiveData<List<Employee>> employeesListLiveData;



    public Firestore() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        employeesListLiveData = new MutableLiveData<>();
    }

    public void addToUsersCollection(Map<String, Object> userMap) {
        firebaseFirestore.collection("users").document(userMap.get("email").toString())
                .set(userMap).addOnSuccessListener(aVoid -> Log.d(TAG, userMap.get("email").toString() + " added")).addOnFailureListener(e -> Log.d("Firestore", e.getMessage()));
    }

    public void deleteUserFromCollection(Map<String, Object> userMap) {
        firebaseFirestore.collection("users").document(userMap.get("email").toString()).delete()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document " + userMap.get("email").toString() + " successfully deleted"))
                .addOnFailureListener(aVoid -> Log.d(TAG, aVoid.getMessage()));
    }

    public void addEmployeeToUsersEmployeesCollection(String currentUserEmail, Map<String, Object> emplMap) {
        firebaseFirestore.collection("users")
                .document(currentUserEmail)
                .collection("employees")
                .document(emplMap.get("name").toString() + emplMap.get("lastName")).set(emplMap)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document " + emplMap.get("name") + emplMap.get("lastName") + " added"))
                .addOnFailureListener(aVoid -> Log.d(TAG, aVoid.getMessage()));
    }

    public void getAllEmployees(String currentUserEmail) {
        firebaseFirestore.collection("users")
                .document(currentUserEmail)
                .collection("employees").get()
                .addOnCompleteListener(task -> {
                    List<Employee> employeeList = new ArrayList<>();
                    employeesListLiveData.setValue(employeeList);
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Employee employee = documentSnapshot.toObject(Employee.class);
                        employeeList.add(employee);
                        employeesListLiveData.setValue(employeeList);
                        Log.d(TAG, "Employee: " + employee.getName());
                    }
                });
    }

    public LiveData<List<Employee>> getEmployeeListLiveData() {
        return employeesListLiveData;
    }
}