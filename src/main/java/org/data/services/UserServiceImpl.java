package org.data.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.data.entities.User;
import org.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }
    @Transactional
    @Override
        public void save(User user) {
            if (user == null) {
                throw new IllegalArgumentException("User cannot be null");
            }
       userRepository.saveUser(user);
        }

}
