package com.dcpro.dao;

import com.dcpro.entities.Group;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.inject.Singleton;
import javax.persistence.*;

@Singleton
public class GroupDAOImpl extends GenericDAOImpl implements GroupDAO {

//    @PersistenceContext(unitName = "sample")
//    private EntityManager entityManager;

    private EntityManagerFactory factory;


    public GroupDAOImpl() {
        factory = Persistence.createEntityManagerFactory("lib-app");
    }

    @Override
    public Group getByNumberAndFaculty(int groupNumber, String faculty) {
//        Session session = getSession();
//        session.beginTransaction();
//        Query query = session.createQuery("from Group where groupNumber = :num and faculty = :faculty");
        EntityManager entityManager = factory.createEntityManager();
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
