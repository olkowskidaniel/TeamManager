package com.olkowskidaniel.teammanager.model;

public class Employee {
    private String name;
    private String lastName;

    public Employee() {

    }

    public Employee(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
