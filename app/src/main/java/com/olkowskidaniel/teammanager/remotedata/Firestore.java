package com.olkowskidaniel.teammanager.remotedata;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.olkowskidaniel.teammanager.model.Employee;
import com.olkowskidaniel.teammanager.model.NewEmployee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Firestore {

    private static final String TAG = "Firestore";
    private FirebaseFirestore firebaseFirestore;
    private MutableLiveData<List<Employee>> employeesListLiveData;
    private MutableLiveData<Boolean> employeeListChangedEvent;
    private MutableLiveData<String> employeeNameUpdatedEvent;

    public Firestore() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        employeesListLiveData = new MutableLiveData<>();
        employeeListChangedEvent = new MutableLiveData<>();
        employeeNameUpdatedEvent = new MutableLiveData<>();
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

    public void addEmployeeToUsersEmployeesCollection(String currentUserEmail, NewEmployee newEmployee) {
        CollectionReference collectionReference = firebaseFirestore.collection("users")
                .document(currentUserEmail).collection("employees");

        collectionReference.document().set(newEmployee)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Document " + newEmployee.getName() + " added");
                    employeeListChangedEvent.setValue(true);
                })
                .addOnFailureListener(aVoid -> Log.d(TAG, aVoid.getMessage()));
    }

    public void getAllEmployees(String currentUserEmail) {
        List<Employee> employeeList = new ArrayList<>();

        CollectionReference employeesCollectionReference = firebaseFirestore.collection("users")
                .document(currentUserEmail)
                .collection("employees");

        employeesCollectionReference.orderBy("lastName").get()
                .addOnCompleteListener(task -> {
                    employeesListLiveData.setValue(employeeList);
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Employee employee = documentSnapshot.toObject(Employee.class);
                        employee.setEmplId(documentSnapshot.getId());
                        employeeList.add(employee);
                        employeesListLiveData.setValue(employeeList);
                        Log.d(TAG, "Employee: " + employee.getName() + " " + employee.getLastName() + " Id: " + employee.getEmplId());
                    }
                });
    }

    public LiveData<List<Employee>> getEmployeeListLiveData() {
        return employeesListLiveData;
    }

    public LiveData<Boolean> getEmployeeListChangedEvent() {
        return employeeListChangedEvent;
    }

    public LiveData<String> getEmployeeNameUpdatedEvent() {
        return employeeNameUpdatedEvent;
    }

    public void deleteEmplFromDatabase(String currentUserEmail, String currentEmpId) {
        firebaseFirestore.collection("users")
                .document(currentUserEmail)
                .collection("employees")
                .document(currentEmpId).delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Employee " + currentEmpId + " deleted");
                    employeeListChangedEvent.setValue(true);
                })
                .addOnFailureListener(aVoid -> Log.d(TAG, aVoid.getMessage()));
    }

    public void updateEmployeeName(String currentUserEmail, String emplId, String name) {
        firebaseFirestore.collection("users")
                .document(currentUserEmail)
                .collection("employees")
                .document(emplId)
                .update("name", name).addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Employee " + currentUserEmail + " name updated to " + name);
                    employeeNameUpdatedEvent.setValue(name);
                });
    }
}