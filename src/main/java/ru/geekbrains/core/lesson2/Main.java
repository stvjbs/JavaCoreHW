package ru.geekbrains.core.lesson2;

public class Main {
    public static void main(String[] args) {
        Worker worker1 = Worker.createWorker("Steve", "Jobs");
        Freelancer freelancer1 = Freelancer.createFreelancer("Steve", "Wozniak");
        System.out.println(Employee.employeeList);
        System.out.println(worker1.monthlySalaryCounter());
        System.out.println(freelancer1.monthlySalaryCounter());
    }
}
