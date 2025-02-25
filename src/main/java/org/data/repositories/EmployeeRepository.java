package org.data.repositories;
import org.data.entities.Employee;

import java.util.List;

public interface EmployeeRepository {
    void saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(int id);


}
