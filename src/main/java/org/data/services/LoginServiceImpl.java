package org.data.services;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.data.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    @Inject
    private EmployeeRepository empRepository;

    @Override
    public boolean isValidUser(String username, String password) {
        return empRepository.checkCredentials(username, password);
    }
}
