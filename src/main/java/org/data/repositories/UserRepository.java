package org.data.repositories;

import org.data.entities.Employee;
import org.data.entities.User;

public interface UserRepository{

    void saveUser(User user);

    User findByUsername(String username);
}
