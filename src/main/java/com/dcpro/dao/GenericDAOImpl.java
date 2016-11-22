package com.dcpro.dao;

import com.dcpro.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDAOImpl<T, PK extends Serializable> implements GenericDAO {

    @PersistenceContext(unitName = "sample")
    private EntityManager entityManager;

    protected Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public void save(Object entity) {
        /*Session session = this.getSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();*/
        EntityTransaction transaction = entityManager.getTransaction();
        entityManager.persist(entity);
        transaction.commit();
    }

    @Override
    public void update(Object entity) {
        /*Session session = this.getSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();*/
//        entityManager.refresh(entity);
        EntityTransaction transaction = entityManager.getTransaction();
        entityManager.refresh(entity);
        transaction.commit();
    }

    @Override
    public void merge(Object entity) {
        /*Session session = this.getSession();
        session.beginTransaction();
        session.merge(entity);
        session.getTransaction().commit();
        session.close();*/
//        entityManager.merge(entity);
        EntityTransaction transaction = entityManager.getTransaction();
        entityManager.merge(entity);
        transaction.commit();
    }

    @Override
    public void delete(Object entity) throws ConstraintViolationException{
        /*Session session = null;
        try {
            session = this.getSession();
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }*/
//        entityManager.remove(entity);
        EntityTransaction transaction = entityManager.getTransaction();
        entityManager.remove(entity);
        transaction.commit();
    }

    @Override
    public List findAll(Class clazz) {
        /*Session session = this.getSession();
        session.beginTransaction();
        Query query = session.createQuery("from " + clazz.getName());
        List list = query.list();
        session.getTransaction().commit();
        session.close();*/
        EntityTransaction transaction = entityManager.getTransaction();
        List list = entityManager.createQuery("from " + clazz.getName()).getResultList();
        transaction.commit();
        return list;
    }
}
