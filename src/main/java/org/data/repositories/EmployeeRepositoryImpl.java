package org.data.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.data.entities.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveEmployee(Employee employee) {
        Transaction transaction = null;

        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();  // Start a transaction

            session.saveOrUpdate(employee);

            transaction.commit();  // Commit transaction
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();  // Rollback on error
            }
            ex.printStackTrace();
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }
    @Override
    public Employee getEmployeeById(Long id) {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is NULL. Check Hibernate setup in OrgModule.");
        }

        try (Session session = sessionFactory.openSession()) {
            return (Employee) session.createCriteria(Employee.class)
                    .add(Restrictions.eq("id", id))
                    .uniqueResult();
        }
    }

    @Override
    public void delete(Employee employee) {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(employee);
            tx.commit();
            session.close();
        }
    }