package org.data.Controller;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.data.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/employees")
public class EmployeeController {

        @Autowired
        @Inject
        private EmployeeService employeeService;

        // API to Add an Employee
        @PostMapping()
        public String addEmployee(@RequestBody Employee employee) {
            employeeService.saveEmployee(employee);
            return "Employee added successfully!";
        }

        // API to Get All Employees
        @GetMapping()
        public List<Employee> getAllEmployees() {
            return employeeService.getAllEmployees();
        }

        // API to Get Employee by ID
        @GetMapping("/{id}")
        public Employee getEmployeeById(@PathVariable int id) {
            return employeeService.getEmployeeById(id);
        }
    }
