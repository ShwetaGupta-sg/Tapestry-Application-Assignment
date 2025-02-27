package org.data.services;

import org.data.entities.Employee;
import org.data.entities.User;
import org.data.repositories.EmployeeRepository;
import org.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {

  @Autowired
  private UserRepository userRepository;


    @Override
    @Transactional
    public User isValidUser(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user; // Return the authenticated employee object
        }
        return null; //  Return null if authentication fails
    }

}
