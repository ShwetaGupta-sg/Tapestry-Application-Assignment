package org.data.repositories;

import org.data.entities.User;

public interface UserRepository {
    User findByUsername(String username);
    User checkCredentials(String username, String password);
}
