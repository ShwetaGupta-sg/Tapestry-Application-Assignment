package org.data.repositories;

import org.data.entities.Permission;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Permission> getAllPermissions() {
        return getSession().createQuery("FROM Permission", Permission.class).list();
    }

    @Override
    public Permission getPermissionByName(String name) {
        return getSession().createQuery("FROM Permission WHERE name = :name", Permission.class)
                .setParameter("name", name)
                .uniqueResult();
    }
}


