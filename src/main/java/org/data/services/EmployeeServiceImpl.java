package org.data.services;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.data.repositories.EmployeeRepository;
import org.data.repositories.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
//    @Inject
    private EmployeeRepositoryImpl employeeRepositoryImpl;

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepositoryImpl.saveEmployee(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepositoryImpl.getAllEmployees();
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepositoryImpl.getEmployeeById(id);
    }
}

