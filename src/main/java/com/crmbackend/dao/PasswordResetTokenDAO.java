package com.crmbackend.dao;

import com.crmbackend.entity.PasswordResetToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordResetTokenDAO {

    private final SessionFactory sessionFactory;

    public PasswordResetTokenDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveToken(PasswordResetToken token) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.persist(token);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public PasswordResetToken getByToken(String token) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            PasswordResetToken result = session.createQuery("FROM PasswordResetToken WHERE token = :token", PasswordResetToken.class)
                    .setParameter("token", token)
                    .uniqueResult();
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void deleteToken(PasswordResetToken token) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.remove(session.merge(token)); 
        tx.commit();
        session.close();
    }

    
    public PasswordResetToken getByUserId(int userId) {
        Session session = sessionFactory.openSession();
        PasswordResetToken token = session.createQuery("FROM PasswordResetToken WHERE user.id = :userId", PasswordResetToken.class)
            .setParameter("userId", userId)
            .uniqueResult();
        session.close();
        return token;
    }

}
