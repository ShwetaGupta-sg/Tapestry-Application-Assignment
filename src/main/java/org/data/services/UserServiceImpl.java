package org.data.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.data.entities.User;
import org.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user); // New User
        } else {
            entityManager.merge(user);   // Update User
        }
    }

    @Override
    public boolean hasPermission(String username, String permission) {
        User user = userRepository.findByUsername(username);
        // Check if the user has the given permission
        return user.getPermissions().stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(permission));
    }

//    @Override
//    public User getUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }

    @Override
    public Set<String> getAllPermissions() {
        return Set.of("VIEW_EMPLOYEES", "EDIT_EMPLOYEES", "DELETE_EMPLOYEES", "ADD_EMPLOYEES");
    }
}
