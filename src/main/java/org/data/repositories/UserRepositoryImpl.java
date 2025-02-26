package org.data.repositories;

import org.data.entities.Employee;
import org.data.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();  // Ensures using the same session
    }
    @Override
    public User findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {  // Open session explicitly
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }
    @Transactional
    @Override
    public void saveUser(User user) {
//        getSession().saveOrUpdate(user);
        Transaction transaction = null;

        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();  // Start a transaction

            session.saveOrUpdate(user);

            transaction.commit();  // Commit transaction
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();  // Rollback on error
            }
            ex.printStackTrace();
        }
    }
}
