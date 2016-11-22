package com.dcpro.dao;

import com.dcpro.entities.Group;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

public class GroupDAOImpl extends GenericDAOImpl implements GroupDAO {

    @PersistenceContext(unitName = "sample")
    private EntityManager entityManager;

    @Override
    public Group getByNumberAndFaculty(int groupNumber, String faculty) {
//        Session session = getSession();
//        session.beginTransaction();
//        Query query = session.createQuery("from Group where groupNumber = :num and faculty = :faculty");
        EntityTransaction transaction = entityManager.getTransaction();
        javax.persistence.Query query = entityManager
                .createQuery("from Group where groupNumber = :num and faculty = :faculty");
        query.setParameter("num", groupNumber);
        query.setParameter("faculty", faculty);
        Group group = (Group) query.getSingleResult();
//        query.setLong("num", groupNumber);
//        query.setString("faculty", faculty);
//        Group group = (Group) query.uniqueResult();
//        session.getTransaction().commit();
        return group;
    }
}
