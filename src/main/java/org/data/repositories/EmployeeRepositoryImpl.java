package org.data.repositories;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

//    @Inject
    @Autowired
    private SessionFactory sessionFactory;

    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("admin", "password123");
        users.put("user", "userpass");
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

//    @Override
//    public void saveEmployee(Employee employee) {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction tx = session.beginTransaction();
//            session.save(employee);
//            tx.commit();
//        }
//    }

    @Override
    public void saveEmployee(Employee employee) {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is null! Check OrgModule configuration.");
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(employee);
            tx.commit();
        }
    }
    @Override
    public List<Employee> getAllEmployees() {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is NULL. Check Hibernate setup in OrgModule.");
        }

        try (Session session = sessionFactory.openSession()) {
            return session.createCriteria(Employee.class).list();
        }
    }

    @Override
    public Employee getEmployeeById(int id) {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is NULL. Check Hibernate setup in OrgModule.");
        }

        try (Session session = sessionFactory.openSession()) {
            return (Employee) session.createCriteria(Employee.class)
                    .add(Restrictions.eq("id", id))
                    .uniqueResult();
        }
    }

//    @Override
//    public List<Employee> getAllEmployees() {
//        try (Session session = sessionFactory.openSession()) {
//            return session.createCriteria(Employee.class).list();
//        }
//    }
//
//    @Override
//    public Employee getEmployeeById(int id) {
//        try (Session session = sessionFactory.openSession()) {
//            return (Employee) session.createCriteria(Employee.class)
//                    .add(Restrictions.eq("id", id))
//                    .uniqueResult();
//        }
//    }
}