package com.dcpro.view.windows;

import com.dcpro.dao.*;
import com.dcpro.entities.Group;
import com.dcpro.entities.Student;
import com.dcpro.view.NotificationUtils;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class StudentWindow extends Window {

    private final FormLayout form = new FormLayout();
    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private final Button okButton = new Button("OK", FontAwesome.CHECK);
    private final Button cancelButton = new Button("Отмена", FontAwesome.CLOSE);
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
    protected DAOService daoService;

    public DAOService getDaoService() {
        return daoService;
    }

    public StudentWindow() {
        super();
        Injector injector = Guice.createInjector(new DAOModule());
        daoService = injector.getInstance(DAOService.class);
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
            List<Group> list = getDao().getEntities(Group.class);
            groupCombo.removeAllItems();
            groupCombo.addItems(list);
            Collection<?> itemIds = groupCombo.getItemIds();
            for (Object itemId : itemIds) {
                Group itemId1 = (Group) itemId;
                if (itemId1.getGroupId().equals(student.getGroup().getGroupId())) {
                    groupCombo.select(itemId1);
                }
            }
        });
    }

    public DAOService getDao() {
        return daoService;
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
                NotificationUtils.showNotification("Поле " + field.getCaption() + " не заполнено");
                return false;
            }
        }

        if (birthDateField.getValue() == null) {
            NotificationUtils.showNotification("Поле " + birthDateField.getCaption() + " не заполнено");
            return false;
        }
        long date = birthDateField.getValue().getTime();
        birthDate = new Date(date);

        if (group == null) {
            NotificationUtils.showNotification("Поле " + groupCombo.getCaption() + " не заполнено");
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
        Collection<?> itemIds = groupCombo.getItemIds();



        List<Group> list = getDao().getEntities(Group.class);
        groupCombo.removeAllItems();
        groupCombo.addItems(list);
        for (Object itemId : itemIds) {
            Group itemId1 = (Group) itemId;
            if (itemId1.getGroupId().equals(student.getGroup().getGroupId())) {
//                        groupCombo.setValue(itemId1);
                groupCombo.select(itemId1);
            }
        }
    }
}
