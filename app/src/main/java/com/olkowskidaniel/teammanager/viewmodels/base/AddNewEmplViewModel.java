package com.olkowskidaniel.teammanager.viewmodels.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.olkowskidaniel.teammanager.model.Employee;
import com.olkowskidaniel.teammanager.repositories.EmployeesRepository;

public class AddNewEmplViewModel extends AndroidViewModel {
    public AddNewEmplViewModel(@NonNull Application application) {
        super(application);
    }

    public void onAddNewEmplBtnClicked(String name, String lastName) {
        Employee employee = new Employee(name, lastName);
        EmployeesRepository.getInstance().addEmplToDatabase(employee);
    }
}
