package com.dcpro.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_number", nullable = false)
    private int groupNumber;

    @Column(name = "faculty", nullable = false)
    private String faculty;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<Student>();

    public Group() {

    }

    public Group(int groupNumber, String faculty) {
        this.groupNumber = groupNumber;
        this.faculty = faculty;
    }

    public Group(Long groupId, int groupNumber, String faculty) {
        this.groupId = groupId;
        this.groupNumber = groupNumber;
        this.faculty = faculty;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return groupNumber + " " + faculty;
    }
}
