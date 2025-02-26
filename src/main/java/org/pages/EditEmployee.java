package org.pages;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.commons.Messages;
import org.apache.tapestry5.http.services.Request;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.SelectModelFactory;
import org.data.entities.Employee;
import org.data.entities.Role;
import org.data.services.EmployeeService;
import org.data.services.RoleService;

import java.util.List;

public class EditEmployee {

    @Inject
    private EmployeeService employeeService;

    @Inject
    private RoleService roleService;

    @Inject
    private Messages messages;

    @Property
    private String errorMessage;

    @Inject
    private Request request;

    @Inject
    private SelectModelFactory selectModelFactory;
    @Property
    private Employee employee;

    @Property
    private SelectModel RoleOptions;

    @Property
    private Long employeeId;

    @Property
    private String name, address;

    @Property
    private int age;

    @Inject
    PageRenderLinkSource pageRenderLinkSource;

    @Property
    private Role selectedRole;

    public Long onPassivate() {
        return employeeId;
    }

    void setupRender() {
        List<Role> allRoles = roleService.getAllRoles();
        RoleOptions = selectModelFactory.create(allRoles, "name"); // Display role names in dropdown

        if (employeeId > 0) {
            employee = employeeService.getEmployeeById(employeeId);
            if (employee != null) {
                name = employee.getName();
                age = employee.getAge();
                address = employee.getAddress();
                selectedRole = employee.getRole();
            }else {
                errorMessage = messages.get("not-found");
            }
        }
    }
        public void onActivate(Long employeeId) {
            this.employeeId = employeeId;
            if (employeeId != null) {
                employee = employeeService.getEmployeeById(employeeId);
            }
        }
public void onValidateFromEditEmployeeForm() {
    if (name == null || name.trim().isEmpty() || age <= 0 || address == null || address.trim().isEmpty() || selectedRole == null) {
        throw new RuntimeException("Invalid input");
    }
}
    public Object onSuccessFromEditEmployeeForm() {
        System.out.println("💾 Saving Employee: " + name);

        if (employee != null && isValidInput()) {
            employee.setName(name);
            employee.setAge(age);
            employee.setAddress(address);
            employee.setRole(selectedRole);

            employeeService.saveEmployee(employee);
            System.out.println("✅ Employee updated successfully!");
            return pageRenderLinkSource.createPageRenderLink(EmployeeList.class);
        }

        System.out.println("❌ ERROR: Employee update failed!");
        return this;
    }
    public ValueEncoder<Role> getRoleEncoder() {
        return new ValueEncoder<Role>() {
            @Override
            public String toClient(Role role) {
                return String.valueOf(role.getId()); // Convert Role to String (ID)
            }

            @Override
            public Role toValue(String id) {
                return roleService.getRoleById(Long.parseLong(id)); // Convert String to Role
            }
        };
    }
    /** 🟢 Validates employee form input */
    private boolean isValidInput() {
        return name != null && !name.trim().isEmpty() &&
                age > 0 &&
                address != null && !address.trim().isEmpty() &&
                selectedRole != null;
    }
}
