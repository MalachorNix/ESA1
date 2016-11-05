package com.dcpro.dao;

import com.dcpro.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.io.Serializable;
import java.util.List;

public abstract class GenericDAOImpl<T, PK extends Serializable> implements GenericDAO {

    protected Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public void save(Object entity) {
        Session session = this.getSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Object entity) {
        Session session = this.getSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void merge(Object entity) {
        Session session = this.getSession();
        session.beginTransaction();
        session.merge(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Object entity) throws ConstraintViolationException{
        Session session = null;
        try {
            session = this.getSession();
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List findAll(Class clazz) {
        Session session = this.getSession();
        session.beginTransaction();
        Query query = session.createQuery("from " + clazz.getName());
        List list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
