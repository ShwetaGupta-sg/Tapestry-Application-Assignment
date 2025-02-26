package org.data.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Role findByName(String roleName) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getSingleResult();
    }

    @Override
    public Role getRoleById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Role role = session.get(Role.class, id);
        tx.commit();
        return role;
    }
}

