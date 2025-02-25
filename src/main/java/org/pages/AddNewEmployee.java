package org.pages;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
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

    @InjectPage
    private EmployeeList employeeList;
    @Inject
    private RoleService roleService;
    @Property
    private String selectedRole;
    @Property
    private List<String> roleOptions;
    @InjectComponent
    private Zone designationZone;

    @InjectPage
    private Login loginPage;  // For logout redirection

    void onSuccessFromAddEmployeeForm() {
        Long userId = 1L;
        Role role = roleService.findByName(selectedRole);
        User user = userService.findById(userId); // Fetch the user from DB

        user.setRole(role);  // ✅ Correct: Setting role on User, not Employee

        userService.save(user);
//        employee.setRole(role);
//        employeeService.saveEmployee(employee);
    }

    @SetupRender
    public void setupRender() {
        employee = new Employee();
        // Fetch roles from database
        roleOptions = roleService.getAllRoles();  // Ensure this method returns List<String> of role names
    }

    Object onActionFromClose() {
        return loginPage;
    }


//    Object onSuccessFromAddEmployeeForm() {
//        if (errorMessage == null) {
//            Employee newEmployee = new Employee();
//            newEmployee.setName(name);
//            newEmployee.setAge(age);
//            newEmployee.setAddress(address);
//
//            employeeService.saveEmployee(newEmployee);
//            return employeeList;
//        }
//        return null;
//    }


//    void onActivate(int id) {
//        employee = employeeService.getEmployeeById(id);
//    }


}
