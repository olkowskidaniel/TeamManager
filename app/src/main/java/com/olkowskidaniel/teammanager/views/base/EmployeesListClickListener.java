package com.olkowskidaniel.teammanager.views.base;

import com.olkowskidaniel.teammanager.model.Employee;

public interface EmployeesListClickListener {
    void onDeleteButtonClicked(String emplId);
    void onListItemClicked(Employee employee);
}