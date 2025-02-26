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
//        if (sessionFactory == null) {
//            throw new IllegalStateException("SessionFactory is NULL. Check Hibernate setup in OrgModule.");
//        }
//
//        try (Session session = sessionFactory.openSession()) {
//            return session.createCriteria(Employee.class).list();
//        }
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
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) { // Open a session
            transaction = session.beginTransaction(); // Start transaction
            if (employee != null) {
                System.out.println("🗑️ Deleting Employee: " + employee.getId() + " - " + employee.getName());

                // Remove Employee from Role Association
                if (employee.getRole() != null) {
                    employee.setRole(null); // Detach role association
                }
                if (employee.getPermissions() != null) {
                    employee.getPermissions().clear();
                }

                // Remove Employee from employee_permissions table (Manual deletion as it's a separate table)
                session.createNativeQuery("DELETE FROM employee_permissions WHERE employee_id = :employeeId")
                        .setParameter("employeeId", employee.getId())
                        .executeUpdate();

                // Delete the employee
                session.delete(employee);
                System.out.println("✅ Employee deleted successfully!");

            } else {
                System.out.println("❌ ERROR: No Employee found with ID: " + employee.getId());
            }

            transaction.commit(); // Commit transaction

        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of failure
            }
            System.out.println("❌ ERROR deleting employee: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

//    @Override
//    public void delete(Employee employee) {
//        System.out.println("🗑️ Deleting Employee: " + employee.getId() + " - " + employee.getName());
//        try (Session session = sessionFactory.openSession()) { // Use openSession() and try-with-resources
//            Transaction transaction = session.beginTransaction(); // Start a transaction
//            session.delete(employee); // Delete employee
//            transaction.commit(); // Commit the transaction
//            System.out.println("✅ Employee deleted: " + employee.getName());
//        } catch (Exception e) {
//            System.out.println("❌ ERROR: Failed to delete employee!");
//            e.printStackTrace();
//        }
//    }

}