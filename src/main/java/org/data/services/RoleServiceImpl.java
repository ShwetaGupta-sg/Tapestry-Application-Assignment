package org.data.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.data.entities.Role;
import org.data.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public List<Role> getAllRoles() {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r", Role.class);
        return query.getResultList();
    }

    @Override
    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.getRoleById(id);
    }
//    @Override
//    public List<String> getAllRoles() {
//        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r", Role.class);
//        List<Role> roles = query.getResultList();
//
//        // Convert Role entities to List of role names (Strings)
//        return roles.stream().map(Role::getName).collect(Collectors.toList());
//    }

//    @Override
//    public Role findByName(String roleName) {
//        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :roleName", Role.class);
//        query.setParameter("roleName", roleName);
//        return query.getSingleResult();
//    }



}