package com.crmbackend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.crmbackend.entity.User;
import com.crmbackend.exception.ResourceNotFoundException;

import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;

@Repository
public class UserDAO {

    private final SessionFactory sessionFactory;

    public UserDAO(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    public String createUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
        return "User created successfully.";
    }

    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        List<User> users = session.createQuery("from User", User.class).list();
        session.close();
        return users;
    }

    public User getUserById(int id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        session.close();
        return user;
    }

    public String updateUser(int id, User updatedUser) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        User existing = session.get(User.class, id);
        if (existing == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }

        existing.setUsername(updatedUser.getUsername());
        existing.setPassword(updatedUser.getPassword());
        existing.setRole(updatedUser.getRole());

        tx.commit();
        session.close();
        return "User updated successfully.";
    }

    public String deleteUser(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        User user = session.get(User.class, id);
        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        session.delete(user);
        tx.commit();
        session.close();
        return "User deleted successfully.";
    }
    
    public boolean isUsernameTaken(String username) {
        Session session = sessionFactory.openSession();
        Long count = session.createQuery("SELECT COUNT(u.id) FROM User u WHERE u.username = :username", Long.class)
                            .setParameter("username", username)
                            .uniqueResult();
        
        session.close();
        return count > 0;
    }
    
    public User getUserByUsername(String username) {
        Session session = sessionFactory.openSession();
        User user = session.createQuery("FROM User WHERE username = :username", User.class)
                           .setParameter("username", username)
                           .uniqueResult();
        session.close();

        if (user == null) {
            throw new ResourceNotFoundException("User with username '" + username + "' not found");
        }

        return user;
    }
    
    
    
    public String updateUserByUsername(String username, User updatedUser) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        User existingUser = session.createQuery("FROM User WHERE username = :username", User.class)
                                   .setParameter("username", username)
                                   .uniqueResult();

        if (existingUser == null) {
            session.close();
            throw new ResourceNotFoundException("User with username " + username + " not found.");
        }

        existingUser.setPassword(updatedUser.getPassword());
       

        tx.commit();
        session.close();
        return "User updated successfully.";
    }
    
    public User getUserByEmail(String email) {
        Session session = sessionFactory.openSession();
        Transaction ts = session.beginTransaction();
        User user = session.createQuery("FROM User WHERE email = :email", User.class)
                           .setParameter("email", email)
                           .uniqueResult();
        ts.commit();
        session.close();

        if (user == null) {
            throw new ResourceNotFoundException("User with email '" + email + "' not found.");
        }

        return user;
    }

    
    public void updateUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(user); // or session.update(user)
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }





}

