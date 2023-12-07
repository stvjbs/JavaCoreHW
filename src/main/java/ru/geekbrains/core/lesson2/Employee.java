package ru.geekbrains.core.lesson2;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private final String name;
    private String surname;
    public static final List<Employee> employeeList = new ArrayList<>();

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    protected Employee(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

}
