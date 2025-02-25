package org.data.repositories;

import org.data.entities.Employee;
import org.data.entities.User;

public interface UserRepository{

    User findByUsername(String username);
    void saveUser(User user);
}
