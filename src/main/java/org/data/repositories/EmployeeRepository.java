package org.data.repositories;
import org.data.entities.Employee;
import org.data.entities.User;

import java.util.List;

public interface EmployeeRepository {
    void saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(int id);


    User checkCredentials(String username, String password);

}
