package org.data.services;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.data.entities.User;
import org.data.repositories.EmployeeRepository;
import org.data.repositories.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
//    @Inject
    private EmployeeRepositoryImpl employeeRepositoryImpl;

    @Override
    @Transactional  // ✅ Ensures data is saved correctly
    public void addEmployee(String name, int age, String address, User user) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setAge(age);
        employee.setAddress(address);
        employee.setUser(user); // ✅ Ensure user is set

        entityManager.persist(employee);
    }


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

