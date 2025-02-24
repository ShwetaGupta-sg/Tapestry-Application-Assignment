package org.data.repositories;

import org.data.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    @Override
    public User findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }

    @Override
    public User checkCredentials(String username, String password) {
        User user = null;
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) { // ✅ Auto-closing session
            transaction = session.beginTransaction(); // ✅ Start transaction

            // ✅ Use Criteria API
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);

            Predicate usernamePredicate = criteriaBuilder.equal(root.get("username"), username);
            Predicate passwordPredicate = criteriaBuilder.equal(root.get("password"), password);
            criteriaQuery.select(root).where(criteriaBuilder.and(usernamePredicate, passwordPredicate));

            List<User> resultList = session.createQuery(criteriaQuery).getResultList();

            if (!resultList.isEmpty()) {
                user = resultList.get(0); // ✅ Get first matching user
            }

            transaction.commit(); // ✅ Commit transaction
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // ✅ Rollback if there's an error
            }
            e.printStackTrace();
        }

        return user; // ✅ Return User or null if not found
    }

}
