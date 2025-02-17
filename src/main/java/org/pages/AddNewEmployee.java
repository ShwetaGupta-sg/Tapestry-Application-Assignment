package org.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.data.services.EmployeeService;

public class AddNewEmployee {

        @Property
        private String name;

        @Property
        private int age;

        @Property
        private String address;

        @Property
        private String errorMessage;
        @Inject
        private EmployeeService employeeService;

        @InjectPage
        private EmployeeList employeeList;

        void onValidateFromAddEmployeeForm() {
            if (name == null || name.isEmpty() || age < 18 || address == null || address.isEmpty()) {
                errorMessage = "All fields are required!";
            }
        }

        Object onSuccessFromAddEmployeeForm() {
            if (errorMessage == null) { // Only save if there's no error
                Employee newEmployee = new Employee();
                newEmployee.setName(name);
                newEmployee.setAge(age);
                newEmployee.setAddress(address);

                employeeService.saveEmployee(newEmployee);
                return employeeList;
            }
            return null;
        }
}
