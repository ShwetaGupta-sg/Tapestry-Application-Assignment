package org.data.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;
import org.data.entities.Employee;
import org.data.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @PersistenceContext
    private EntityManager entityManager;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        employeeRepository.saveEmployee(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.getEmployeeById(id);
    }

    @Override
    public void updateEmployee(Employee employee) {
        if (employee != null) {
            employeeRepository.saveEmployee(employee);
        }
    }

//    @Override
//    public void delete(Long id) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaDelete<Employee> delete = cb.createCriteriaDelete(Employee.class);
//        Root<Employee> root = delete.from(Employee.class);
//        delete.where(cb.equal(root.get("id"), id));
//        entityManager.createQuery(delete).executeUpdate();
//    }
@Override
public void deleteEmployee(Long employeeId) {
    System.out.println("🔄 Attempting to delete employee with ID: " + employeeId);
    Employee employee = employeeRepository.getEmployeeById(employeeId);
    if (employee != null) {
        employeeRepository.delete(employee);
        System.out.println("✅ Employee deleted: " + employee.getName());
    } else {
        System.out.println("❌ ERROR: Employee not found!");
    }
}

}

