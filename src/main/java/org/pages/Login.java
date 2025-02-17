package org.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
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

    void onValidateFromLoginForm() {
        if (!loginService.isValidUser(username, password)) {
            loginMessage = "Invalid Credentials.";
            loginForm.recordError(loginMessage);
        }
    }

    Object onSuccessFromLoginForm() {
        return EmployeeList.class;
}
}
