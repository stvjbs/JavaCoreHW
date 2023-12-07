package ru.geekbrains.core.lesson2;

public class Worker extends Employee implements Payable{

    private Worker(String name, String surname) {
        super(name, surname);
    }
    public static Worker createWorker(String name, String surname){
        Worker e = new Worker(name,surname);
        Employee.employeeList.add(e);
        return e;
    }
    private double salary = 1000.0;

    @Override
    public double monthlySalaryCounter() {
        return salary;
    }
}
