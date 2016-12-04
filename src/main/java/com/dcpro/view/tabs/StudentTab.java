package com.dcpro.view.tabs;

import com.dcpro.entities.Student;
import com.dcpro.view.AbstractView;
import com.dcpro.view.NotificationUtils;
import com.dcpro.view.windows.EditStudentWindow;
import com.dcpro.view.windows.StudentWindow;
import com.dcpro.view.windows.AddStudentWindow;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class StudentTab extends AbstractView implements ComponentContainer {

    private final Grid grid = new Grid();
    private final BeanItemContainer<Student> students = new BeanItemContainer<>(Student.class);
//    private final StudentDAO dao = new StudentDAOImpl();
    private final Button addButton = new Button("Добавить студента", FontAwesome.PLUS);
    private final Button editButton = new Button("Редактировать студента", FontAwesome.EDIT);
    private final Button removeButton = new Button("Удалить студента", FontAwesome.REMOVE);
    private final Button refreshButton = new Button("Обновить таблицу", FontAwesome.REFRESH);
    private final HorizontalLayout buttonLayout = new HorizontalLayout();
    private final StudentWindow addStudentWindow = new AddStudentWindow();
    private final StudentWindow editStudentWindow = new EditStudentWindow();
    private final TextField filterLastNameField = new TextField();
    private TextField filterGroupField = new TextField();
    private Grid.HeaderRow filterRow = grid.appendHeaderRow();

    public StudentTab() {
        super();
        refreshTable();
        tableInit();
        setMargin(true);
        setSpacing(true);
        buttonLayoutInit();
        addComponents(grid, buttonLayout);
        addStudentWindow.addCloseListener(closeEvent -> refreshTable());
        editStudentWindow.addCloseListener(closeEvent -> refreshTable());
    }

    public TextField getFilterGroupField() {
        return filterGroupField;
    }

    private void buttonLayoutInit() {
        buttonLayout.setSpacing(true);
        buttonLayout.addComponents(addButton, editButton, removeButton, refreshButton);
        addButton.addClickListener((Button.ClickEvent e) -> addStudent());
        editButton.addClickListener((Button.ClickEvent e) -> editStudent());
        removeButton.addClickListener((Button.ClickEvent e) -> removeStudent());
        refreshButton.addClickListener((Button.ClickEvent e) -> refreshTable());
    }

    private void addStudent() {
        UI.getCurrent().addWindow(addStudentWindow);
    }

    private void editStudent() {
        Student student = (Student) grid.getSelectedRow();
        if (student != null) {
            editStudentWindow.setStudent(student);
            UI.getCurrent().addWindow(editStudentWindow);
        } else {
            studentNotSelectedWarning();
        }
    }

    private void removeStudent() {
        Student student = (Student) grid.getSelectedRow();
        if (student != null) {
//            dao.delete(student);
            try {
                daoService.deleteEntity(student.getStudentId(), Student.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            refreshTable();
        } else {
            studentNotSelectedWarning();
        }
    }

    private void studentNotSelectedWarning() {
        NotificationUtils.showNotification("Студент не выбран");
    }

    private void refreshTable() {
        students.removeAllItems();
//        students.addAll(dao.findAll(Student.class));
        students.addAll(daoService.getEntities(Student.class));
    }

    private void tableInit() {
        grid.setContainerDataSource(students);
        grid.setSizeFull();

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setColumnOrder("lastName", "firstName", "secondName", "birthDate", "group");
        grid.removeColumn("studentId");
        Grid.Column lastNameColumn = grid.getColumn("lastName");
        lastNameColumn.setHeaderCaption("Фамилия");
        Grid.Column firstNameColumn = grid.getColumn("firstName");
        firstNameColumn.setHeaderCaption("Имя");
        Grid.Column secondNameColumn = grid.getColumn("secondName");
        secondNameColumn.setHeaderCaption("Отчество");
        Grid.Column birthDateColumn = grid.getColumn("birthDate");
        birthDateColumn.setHeaderCaption("Дата рождения");
        Grid.Column groupColumn = grid.getColumn("group");
        groupColumn.setHeaderCaption("Группа");
        addFilterToColumn(filterLastNameField, "lastName");
        addFilterToColumn(filterGroupField, "group");
        grid.setImmediate(true);
        Grid.Column bornColumn = grid.getColumn("birthDate");
        bornColumn.setRenderer(new DateRenderer("%1$te %1$tB %1$tY"));
    }

    private void addFilterToColumn(TextField textField, String columnId) {
        textField.addStyleName(ValoTheme.TEXTFIELD_TINY);
        textField.setInputPrompt("Фильтр");
        textField.addTextChangeListener(new FieldEvents.TextChangeListener() {

            SimpleStringFilter filter = null;

            @Override
            public void textChange(FieldEvents.TextChangeEvent textChangeEvent) {

                Container.Filterable f = (Container.Filterable) grid.getContainerDataSource();

                if (filter != null) {
                    f.removeContainerFilter(filter);
                }

                filter = new SimpleStringFilter(columnId, textChangeEvent.getText(), true, false);
                f.addContainerFilter(filter);
                grid.cancelEditor();
            }
        });

        filterRow.getCell(columnId).setComponent(textField);
        filterRow.getCell(columnId).setStyleName("filter-header");
    }
}
