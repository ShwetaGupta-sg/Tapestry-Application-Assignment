package org.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.http.services.RequestGlobals;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.data.entities.User;
import org.data.services.EmployeeService;
import org.data.services.UserService;

import java.util.List;

public class EmployeeList {
    @Inject
    private EmployeeService employeeService;

    @Inject
    private UserService userService;

    @Inject
    private RequestGlobals requestGlobals;

    private User user;
    @Property
    private List<Employee> employees;

    @Property
    private Employee employee;
    private boolean isAdmin;
    private boolean hasEditAccess;
    private boolean hasDeleteAccess;

    @SetupRender
    public void setupRender() {
        user = (User) requestGlobals.getRequest().getSession(true).getAttribute("loggedInUser");

        if (user == null) {
            System.out.println("No logged-in user found in session.");
            return;
        }

        System.out.println("User in session: " + user.getUsername() + " | Role: " + user.getRole().getName());

        // Check if user is Admin
        isAdmin = "ADMIN".equals(user.getRole().getName());

        // Fetch employees from the database
        employees = employeeService.getAllEmployees();

        if (employees == null || employees.isEmpty()) {
            System.out.println("No employees found in the database.");
        }

        // Check user permissions
        hasEditAccess = userService.hasPermission(user.getUsername(), "EDIT_EMPLOYEES");
        hasDeleteAccess = userService.hasPermission(user.getUsername(), "DELETE_EMPLOYEES");
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public boolean getCanEdit() {
        return hasEditAccess;
    }

    public boolean getCanDelete() {
        return hasDeleteAccess;
    }
}
