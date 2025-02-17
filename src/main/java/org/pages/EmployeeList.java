package org.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.data.services.EmployeeService;

import java.util.List;

public class EmployeeList {
    @PageActivationContext  // Automatically gets value from URL context
    @Property
    private int employeeId;
    @Inject
    private EmployeeService employeeService;

    @Property
    private Employee employee;

    public List<Employee> getEmployees() {
        return employeeService.getAllEmployees();
    }

//    @InjectPage
//    private IndividualEmployee individualEmployee;

//    Object onActionFromViewDetails(int id) {
//        individualEmployee.setEmployeeId(id);
//        return individualEmployee;
//    }

    void setupRender() {
        employee = employeeService.getEmployeeById(employeeId);
    }
}


