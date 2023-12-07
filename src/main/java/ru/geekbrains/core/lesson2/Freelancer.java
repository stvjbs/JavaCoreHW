package ru.geekbrains.core.lesson2;

public class Freelancer extends Employee implements Payable{

    private Freelancer(String name, String surname) {
        super(name, surname);
    }
    public static Freelancer createFreelancer(String name, String surname){
        Freelancer e = new Freelancer(name,surname);
        Employee.employeeList.add(e);
        return e;
    }
    private double hourSalary = 12;
    @Override
    public double monthlySalaryCounter() {
        return 20.8 * 8 * hourSalary;
    }
}
