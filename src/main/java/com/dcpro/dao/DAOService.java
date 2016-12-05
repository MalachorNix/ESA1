package com.dcpro.dao;

import com.dcpro.entities.Group;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class DAOService {

    private EntityManagerFactory factory;

    @Inject
    public DAOService() {
        factory = Persistence.createEntityManagerFactory("lib-app");
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getEntities(Class<T> clazz) {
        EntityManager em = factory.createEntityManager();
        String s = clazz.getSimpleName().toLowerCase() + "s";
        String queryString = String.format("select * from %s", s);
        Query query = em.createNativeQuery(queryString, clazz);
        return query.getResultList();
    }

    public boolean commitEntity(Object entity) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void mergeEntity(Object entity) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(entity);
        transaction.commit();
    }

    public void deleteEntity(Long entityId, Class clazz) throws MySQLIntegrityConstraintViolationException {
        EntityManager em = factory.createEntityManager();

        String entityName = clazz.getSimpleName();
        String columnIdName = entityName.toLowerCase() + "Id";
        String queryString = String.format("select e from %s e where e." + columnIdName + " = :id", entityName);
        Query query = em.createQuery(queryString, clazz);
        query.setParameter("id", entityId);
        Object entity = query.getSingleResult();

        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new MySQLIntegrityConstraintViolationException();
        }
    }

    public Group getByNumberAndFaculty(int groupNumber, String faculty) {
        String queryString = "select group from Group group where group.groupNumber = :num and group.faculty = :faculty";
        EntityManager em = factory.createEntityManager();
        Query query = em.createQuery(queryString);
        query.setParameter("num", groupNumber);
        query.setParameter("faculty", faculty);
        Object singleResult = null;
        try {
            singleResult = query.getSingleResult();
        } catch (NoResultException e) {

        }
        return (Group) singleResult;
    }
}
