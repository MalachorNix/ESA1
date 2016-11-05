package com.dcpro.view.windows;

import com.dcpro.entities.Student;

public class AddStudentWindow extends StudentWindow {

    public AddStudentWindow() {
        super();
    }

    @Override
    protected void okButtonAddClickListener() {
        getOkButton().addClickListener(clickEvent -> addStudent());
    }

    private void addStudent() {
        if (super.isValidFieldData()) {
            Student student = new Student(getFirstName(), getSecondName(), getLastName(), getBirthDate(), getGroup());
            this.getDao().save(student);
            this.close();
        }
    }
}
