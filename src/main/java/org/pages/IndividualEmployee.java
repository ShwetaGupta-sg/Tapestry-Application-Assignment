package org.pages;

import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.data.services.EmployeeService;

import java.util.ArrayList;
import java.util.List;

public class IndividualEmployee {


    @Inject
    private EmployeeService employeeService;

    @Property
    private Employee employee;

    private int employeeId;

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    void onActivate(int id) {
        this.employeeId = id;
        this.employee = employeeService.getEmployeeById(employeeId);
    }

    int onPassivate() {
        return employeeId;
    }
}