package com.dcpro.dao;

import com.dcpro.entities.Group;
import org.hibernate.Query;
import org.hibernate.Session;

public class GroupDAOImpl extends GenericDAOImpl implements GroupDAO {

    @Override
    public Group getByNumberAndFaculty(int groupNumber, String faculty) {
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("from com.haulmont.testtask.entities.Group where groupNumber = :num and faculty = :faculty");
        query.setLong("num", groupNumber);
        query.setString("faculty", faculty);
        Group group = (Group) query.uniqueResult();
        session.getTransaction().commit();
        return group;
    }
}
