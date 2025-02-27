package org.pages;

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


//    public boolean isCanEdit(Employee employee) {
    public boolean isCanEdit() {
        if (employee == null) {
            return false;
        }
        if(employee.getPermissions().stream()
                .anyMatch(permission -> "EDIT_EMPLOYEES".equalsIgnoreCase(permission.getName()))){
            return true;
        }
        return isAdmin;
    }

    public boolean isCanDelete() {   // Only admins can delete employees
            return isAdmin;
    }

    public Object onActionFromDeleteEmployee(long employeeId){
            employeeService.deleteEmployee(employeeId);
            return this;
    }
}
