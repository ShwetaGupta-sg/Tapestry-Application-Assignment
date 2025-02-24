package org.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.http.services.RequestGlobals;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.data.entities.User;
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

    @Inject
    private RequestGlobals requestGlobals;

        @InjectPage
        private EmployeeList employeeList;
    void onSuccessFromEmployeeForm() {
        User loggedInUser = (User) requestGlobals.getHTTPServletRequest().getSession().getAttribute("loggedInUser");

        if (loggedInUser != null) {
            employeeService.addEmployee(name, age, address, loggedInUser);
        }
    }
        void onValidateFromAddEmployeeForm() {
            if (name == null || name.isEmpty() || age < 18 || address == null || address.isEmpty()) {
                errorMessage = "All fields are required!";
            }
        }

}
