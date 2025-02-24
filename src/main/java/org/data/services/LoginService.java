package org.data.services;

import org.data.entities.Role;
import org.data.entities.User;

import java.util.Optional;

public interface LoginService {
    boolean isValidUser(String username, String password);
    Optional<Role> getUserRole(String username);
    User authenticate(String username, String password);
}