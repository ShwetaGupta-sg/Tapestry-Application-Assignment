package org.data.repositories;

import org.data.entities.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Autowired
    private SessionFactory sessionFactory;


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

}