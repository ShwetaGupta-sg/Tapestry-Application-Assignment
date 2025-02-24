package org.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.http.services.RequestGlobals;
import org.apache.tapestry5.http.services.Response;
import org.apache.tapestry5.http.services.Session;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.data.entities.Role;
import org.data.entities.User;
import org.data.services.LoginService;
import javax.servlet.http.HttpSession;

public class Login {
        @Property
        private String username;

        @Property
        private String password;

        @Inject
        private LoginService loginService;

        @Property
        private String loginMessage;

        @Inject
        private PageRenderLinkSource linkSource;

        @Inject
        private RequestGlobals requestGlobals;

        @CommitAfter
        Object onSuccessFromLoginForm() {
            User user = loginService.authenticate(username, password);

            if (user == null) {
                loginMessage = "Invalid username or password!";
                return this; // Stay on login page
            }

            // Debugging: Print user info
            System.out.println("Logged in user: " + user.getUsername() + " | Role: " + user.getRole());

            // Store logged-in user in session
            Session session = requestGlobals.getRequest().getSession(true);
            session.setAttribute("loggedInUser", user);

            return EmployeeList.class; // Redirect to Employee List
        }
    }
