package com.dcpro.dao;

import com.dcpro.entities.Group;

public interface GroupDAO extends GenericDAO {

    Group getByNumberAndFaculty(int groupNumber, String faculty);
}
