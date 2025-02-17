package org.data.services;

public interface LoginService {
    boolean isValidUser(String username, String password);
}