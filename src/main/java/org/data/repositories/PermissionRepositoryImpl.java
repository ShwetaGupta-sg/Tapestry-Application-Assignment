package org.data.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public boolean hasEditPermission(Long employeeId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Permission> root = cq.from(Permission.class);

        Predicate employeePredicate = cb.equal(root.get("employee").get("id"), employeeId);
        Predicate permissionPredicate = cb.equal(root.get("name"), "EDIT");

        cq.select(cb.count(root))
                .where(cb.and(employeePredicate, permissionPredicate));

        Long count = entityManager.createQuery(cq).getSingleResult();
        return count > 0;
    }
}


