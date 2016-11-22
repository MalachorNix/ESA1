package com.dcpro.view.windows;

import com.dcpro.dao.StudentDAO;
import com.dcpro.dao.StudentDAOImpl;
import com.dcpro.entities.Group;
import com.dcpro.entities.Student;
import com.dcpro.dao.GroupDAO;
import com.dcpro.dao.GroupDAOImpl;
import com.dcpro.view.NotificationUtils;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public abstract class StudentWindow extends Window {

    private final FormLayout form = new FormLayout();
    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private final Button okButton = new Button("OK", FontAwesome.CHECK);
    private final Button cancelButton = new Button("Отмена", FontAwesome.CLOSE);
    private final StudentDAO dao = new StudentDAOImpl();
    private final TextField lastNameField = new TextField("Фамилия");
    private final TextField firstNameField = new TextField("Имя");
    private final TextField secondNameField = new TextField("Отчество");
    private final PopupDateField birthDateField = new PopupDateField("Дата рождения");

    private final ComboBox groupCombo = new ComboBox("Группа");
    private String lastName;
    private String firstName;
    private String secondName;
    private Date birthDate;
    private Group group;
    private Student student;
    private final GroupDAO groupDAO = new GroupDAOImpl();

    public StudentWindow() {
        super();
        formInit();
        setModal(true);
        setContent(form);
    }

    public FormLayout getForm() {
        return form;
    }

    public HorizontalLayout getButtonsLayout() {
        return buttonsLayout;
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public StudentDAO getDao() {
        return dao;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getSecondNameField() {
        return secondNameField;
    }

    public PopupDateField getBirthDateField() {
        return birthDateField;
    }

    public ComboBox getGroupCombo() {
        return groupCombo;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Group getGroup() {
        return group;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public Student getStudent() {
        return student;
    }

    private void formInit() {
        buttonsLayoutInit();
        form.addComponents(lastNameField, firstNameField, secondNameField, birthDateField, groupCombo, buttonsLayout);
        lastNameField.setRequired(true);
        firstNameField.setRequired(true);
        secondNameField.setRequired(true);
        birthDateField.setRequired(true);
        groupCombo.setRequired(true);
        form.setMargin(true);
        form.setSpacing(true);
        groupCombo.addFocusListener(focusEvent -> {
            List<Group> list = groupDAO.findAll(Group.class);
            groupCombo.removeAllItems();
            groupCombo.addItems(list);
        });
    }

    private void buttonsLayoutInit() {
        buttonsLayout.addComponents(okButton, cancelButton);
        okButtonAddClickListener();
        cancelButton.addClickListener((Button.ClickEvent e) -> {
            this.close();
        });
        buttonsLayout.setSpacing(true);
    }

    protected abstract void okButtonAddClickListener();

    protected boolean isValidFieldData() {
        lastName = lastNameField.getValue().trim();
        firstName = firstNameField.getValue().trim();
        secondName = secondNameField.getValue().trim();
        group = (Group) groupCombo.getValue();
        List<TextField> fields = new ArrayList<>();
        fields.add(lastNameField);
        fields.add(firstNameField);
        fields.add(secondNameField);
        for (TextField field : fields) {
            if (field.getValue().length() == 0) {
                NotificationUtils
                        .showNotification("Поле " + field.getCaption() + " не заполнено"
                        );
                return false;
            }
        }

        if (birthDateField.getValue() == null) {
            NotificationUtils
                    .showNotification("Поле " + birthDateField.getCaption() + " не заполнено"
                    );
            return false;
        }
        long date = birthDateField.getValue().getTime();
        birthDate = new Date(date);

        if (group == null) {
            NotificationUtils
                    .showNotification("Поле " + groupCombo.getCaption() + " не заполнено"
                    );
            return false;
        }

        if (student == null) {
            student = new Student();
        }
        student.setLastName(lastName);
        student.setFirstName(firstName);
        student.setSecondName(secondName);
        student.setBirthDate(birthDate);
        student.setGroup(group);

        return true;
    }

    public void setStudent(Student student) {
        this.student = student;
        populateFields();
    }

    private void populateFields() {
        lastNameField.setValue(student.getLastName());
        firstNameField.setValue(student.getFirstName());
        secondNameField.setValue(student.getSecondName());
        birthDateField.setValue(student.getBirthDate());
        groupCombo.setValue(student.getGroup());
    }
}
