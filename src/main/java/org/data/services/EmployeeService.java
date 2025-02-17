package org.data.services;

import org.data.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeService {

    void saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int id);
}
