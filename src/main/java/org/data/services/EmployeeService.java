package org.data.services;

import org.data.entities.Employee;
import java.util.List;
public interface EmployeeService {

    void saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    void updateEmployee(Employee employee);


    void deleteEmployee(Long employeeId);
}
