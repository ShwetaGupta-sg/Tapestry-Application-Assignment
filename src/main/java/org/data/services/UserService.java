package org.data.services;

import org.data.entities.User;

import java.util.Set;

public interface UserService {

    boolean hasPermission(String username, String permission);

//    User getUserByUsername(String username);

    User findById(Long id);
    void save(User user);
    Set<String> getAllPermissions();


}
