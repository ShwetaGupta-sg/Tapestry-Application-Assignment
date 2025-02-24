package org.data.services;

import org.data.entities.Employee;
import org.data.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeService {
        void addEmployee(String name, int age, String address, User user);
    void saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int id);
}
