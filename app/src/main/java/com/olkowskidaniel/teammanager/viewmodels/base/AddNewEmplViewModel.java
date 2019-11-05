package com.olkowskidaniel.teammanager.viewmodels.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.olkowskidaniel.teammanager.model.Employee;
import com.olkowskidaniel.teammanager.repositories.EmployeesRepository;
import com.olkowskidaniel.teammanager.utils.Fragments;

public class AddNewEmplViewModel extends AndroidViewModel {
    private MutableLiveData<Fragments> startFragmentEvent;

    public AddNewEmplViewModel(@NonNull Application application) {
        super(application);
        startFragmentEvent = new MutableLiveData<>();
    }

    public void onAddNewEmplBtnClicked(String name, String lastName) {
        Employee employee = new Employee(name, lastName);
        EmployeesRepository.getInstance().addEmplToDatabase(employee);
        startFragmentEvent.setValue(Fragments.Personnel);
    }

    public LiveData<Fragments> getStartFragmentEvent() {
        return startFragmentEvent;
    }
}
