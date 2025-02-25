package org.data.services;

import org.data.entities.User;

public interface LoginService {
    User isValidUser(String username, String password);
}