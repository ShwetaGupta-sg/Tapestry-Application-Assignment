package org.data.services;

import org.data.entities.Role;
import org.data.entities.User;
import org.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User authenticate(String username, String password) {
        return userRepository.checkCredentials(username, password);
    }

    @Override
    public boolean isValidUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password); // Use hashing in production
    }

    @Override
    public Optional<Role> getUserRole(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .map(User::getRole);
    }

}

