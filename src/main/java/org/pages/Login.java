package org.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.http.services.RequestGlobals;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.data.entities.User;
import org.data.services.LoginService;

public class Login {

    @Property
    private String username;

    @Property
    private String password;

    @Component
    private Form loginForm;

    @Inject
    private LoginService loginService;

    @Property
    private String loginMessage;
    @Inject
    private RequestGlobals requestGlobals;

    void onValidateFromLoginForm() {
        User loggedInUser = loginService.isValidUser(username, password);

        if (loggedInUser == null) {
            loginMessage = "Invalid username or password!";
            loginForm.recordError("Invalid username or password.");
        } else {
            // Store user in Tapestry's session
            requestGlobals.getRequest().getSession(true).setAttribute("loggedInUser", loggedInUser);
        }
    }

    Object onSuccessFromLoginForm() {
        return EmployeeList.class; // Redirect to EmployeeList page after successful login
    }

}
