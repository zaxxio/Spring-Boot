package com.avaand.app.criteria;

import com.avaand.app.domain.Employee;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setEmpFirstName("Partha");
        emp1.setAge(5);
        employees.add(emp1);


        Employee emp2 = new Employee();
        //emp2.setEmpFirstName();
        emp2.setAge(12);
        employees.add(emp2);

        final Criteria<Employee> criteria = new CriteriaBuilder<Employee>()
                .where(employee -> employee.getAge() >= 3)
                .build();

        List<Employee> filteredItems = criteria.apply(employees);
        System.out.println(filteredItems);
    }
}

