package com.olkowskidaniel.teammanager.model;

public class Employee {
    private String name;
    private String lastName;
    private String emplId;

    public Employee() {}

    public Employee(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }


    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmplId() {
        return emplId;
    }

    public void setEmplId(String id) {
        this.emplId = id;
    }
}
