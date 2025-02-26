package org.data.services;

import org.data.entities.User;

import java.util.Set;

public interface UserService {

//    User getUserByUsername(String username);

    User findById(Long id);
    void save(User user);


}
