package org.pages;

import net.bytebuddy.asm.Advice;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.http.services.RequestGlobals;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.data.entities.User;
import org.data.services.EmployeeService;
import org.data.services.PermissionService;

import java.util.List;

public class EmployeeList {
    @Inject
    private EmployeeService employeeService;

    @Inject
    private PermissionService permissionService;
    @Inject
    private RequestGlobals requestGlobals;

    @Property
    private List<Employee> employees;

    @Property
    private Employee employee;

    @Property
    private User user;
    @Property
    private boolean isAdmin;

    @OnEvent(component = "addNewEmployee")
    Object onAddEmployee() {
        return AddNewEmployee.class;
    }

        @SetupRender
        public void setupRender() {
            User loggedInUser = (User) requestGlobals.getRequest().getSession(true).getAttribute("loggedInUser");

            if (loggedInUser == null) {
                System.out.println("No logged-in user found in session.");
                return;
            }

            System.out.println("User in session: " + loggedInUser.getUsername());

            // Fetch employee record based on logged-in user
            employee = employeeService.getEmployeeById(loggedInUser.getId());

            System.out.println("Employee Role: " + employee.getRole().getName());

            // Check if user is Admin
            isAdmin = "ADMIN".equals(employee.getRole().getName());

            // Fetch employees
            employees = employeeService.getAllEmployees();

            if (employees == null || employees.isEmpty()) {
                System.out.println("No employees found in the database.");
            }
        }


    public boolean canEdit(Employee employee) {
        if (employee == null) {
            return false;
        }

        // Admin can edit all employees
        if ("ADMIN".equalsIgnoreCase(employee.getRole().getName())) {
            return true;
        }

        // Check if the employee has the 'EDIT_EMPLOYEES' permission
        return employee.getPermissions().stream()
                .anyMatch(permission -> "EDIT_EMPLOYEES".equalsIgnoreCase(permission.getName()));
    }
    public boolean CanDelete(Employee employee) {   // Only admins can delete employees
        if ("ADMIN".equalsIgnoreCase(employee.getRole().getName())) {
            return true;
        }
        return employee.getPermissions().stream()
                .anyMatch(permission -> "DELETE_EMPLOYEES".equalsIgnoreCase(permission.getName()));
    }
}
