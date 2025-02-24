package org.pages;

import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.http.services.RequestGlobals;
import org.apache.tapestry5.http.services.Session;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.data.entities.Role;
import org.data.entities.User;
import org.data.services.EmployeeService;

import java.util.List;

import org.apache.tapestry5.annotations.Property;

public class EmployeeList {
                @Inject
                private EmployeeService employeeService;

                @Inject
                private RequestGlobals requestGlobals;

                @Property
                private List<Employee> employees;

                @Property
                private Employee employee;

                @Property
                private boolean isAdmin; // ✅ Used for Add Employee button visibility

                void setupRender() {
                        // Retrieve logged-in user from session
                        User loggedInUser = (User) requestGlobals.getRequest().getSession(true).getAttribute("loggedInUser");

                        if (loggedInUser == null) {
                                System.out.println("No logged-in user found in session.");
                                return;
                        }

                        System.out.println("User in session: " + loggedInUser.getUsername() + " | Role: " + loggedInUser.getRole());

                        // Check if user is admin
                        isAdmin = loggedInUser.getRole().equals(Role.ADMIN);

                        // Fetch employees from DB
                        employees = employeeService.getAllEmployees();

                        if (employees == null || employees.isEmpty()) {
                                System.out.println("No employees found in the database.");
                        }
                }
        }
