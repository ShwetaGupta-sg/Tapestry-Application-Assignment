package org.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.data.entities.Employee;
import org.data.entities.Role;
import org.data.entities.User;
import org.data.services.EmployeeService;
import org.data.services.RoleService;
import org.data.services.UserService;

import java.util.List;

public class AddNewEmployee {

    @Property
    private String name;

    @Property
    private int age;

    @Property
    private String address;

    @Property
    private String errorMessage;
//    @Inject
//    private EmployeeService employeeService;

    @Inject
    private UserService userService;

    @Property
    private Employee employee;

    @Property
    private User user;
    @Inject
    private EmployeeService employeeService;
    @InjectPage
    private EmployeeList employeeList;
    @Inject
    private RoleService roleService;
    @Property
    private String selectedRole;
    @Property
    private List<Role> roleOptions;
    @Inject
    PageRenderLinkSource pageRenderLinkSource;
    @InjectPage
    private Login loginPage;  // For logout redirection

    Object onSuccessFromAddNewEmployeeForm() {
        Long userId = 1L;
        Role role = roleService.findByName(selectedRole);
        User user = userService.findById(userId); // Fetch the user from DB

        if (role == null || user == null) {
            errorMessage = "Invalid Role or User";
            return errorMessage;
        }

        // Ensure employee is initialized
        if (employee == null) {
            employee = new Employee();
        }

        employee.setName(name);
        employee.setAge(age);
        employee.setAddress(address);
        employee.setRole(role);

        userService.save(user);  // Ensure user is saved if modified
        employeeService.saveEmployee(employee); //

        System.out.println("Employee added successfully: " + employee.getName());
        return pageRenderLinkSource.createPageRenderLink(EmployeeList.class);
    }

    @SetupRender
    public void setupRender() {
        employee = new Employee();
        // Fetch roles from database
        roleOptions = roleService.getAllRoles();
    }

}
