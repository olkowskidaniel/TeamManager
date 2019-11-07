package com.olkowskidaniel.teammanager.model;

import java.io.Serializable;

public class Employee implements Serializable {
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
