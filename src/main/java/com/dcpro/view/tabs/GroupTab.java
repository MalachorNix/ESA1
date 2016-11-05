package com.dcpro.view.tabs;

import com.dcpro.dao.GroupDAOImpl;
import com.dcpro.entities.Group;
import com.dcpro.view.windows.AddGroupWindow;
import com.dcpro.view.windows.EditGroupWindow;
import com.dcpro.view.windows.GroupWindow;
import com.dcpro.dao.GroupDAO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.hibernate.exception.ConstraintViolationException;

public class GroupTab extends VerticalLayout implements ComponentContainer {

    private final Grid grid = new Grid();
    private final BeanItemContainer<Group> groups = new BeanItemContainer<>(Group.class);
    private final GroupDAO dao = new GroupDAOImpl();
    private final Button addButton = new Button("Добавить группу", FontAwesome.PLUS);
    private final Button editButton = new Button("Редактировать группу", FontAwesome.EDIT);
    private final Button removeButton = new Button("Удалить группу", FontAwesome.REMOVE);
    private final Button refreshButton = new Button("Обновить таблицу", FontAwesome.REFRESH);
    private final HorizontalLayout buttonLayout = new HorizontalLayout();
    private final GroupWindow addGroupWindow = new AddGroupWindow();
    private final GroupWindow editGroupWindow = new EditGroupWindow();

    public GroupTab() {
        refreshTable();
        tableInit();
        setMargin(true);
        setSpacing(true);
        buttonLayoutInit();
        addComponents(grid, buttonLayout);
        addGroupWindow.addCloseListener(closeEvent -> refreshTable());
        editGroupWindow.addCloseListener(closeEvent -> refreshTable());
    }

    private void buttonLayoutInit() {
        buttonLayout.setSpacing(true);
        buttonLayout.setSizeUndefined();
        buttonLayout.addComponents(addButton, editButton, removeButton, refreshButton);
        addButton.addClickListener((Button.ClickEvent e) -> addGroup());
        editButton.addClickListener((Button.ClickEvent e) -> editGroup());
        removeButton.addClickListener((Button.ClickEvent e) -> removeGroup());
        refreshButton.addClickListener((Button.ClickEvent e) -> refreshTable());
    }

    private void addGroup() {
        UI.getCurrent().addWindow(addGroupWindow);
    }

    private void editGroup() {
        Group group = (Group) grid.getSelectedRow();
        if (group != null) {
            editGroupWindow.setGroup(group);
            UI.getCurrent().addWindow(editGroupWindow);
        } else {
            groupNotSelectedWarning();
        }
    }

    private void tableInit() {
        grid.setContainerDataSource(groups);
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setColumnOrder("groupNumber", "faculty");
        grid.getColumn("groupNumber").setHeaderCaption("Номер группы");
        grid.getColumn("faculty").setHeaderCaption("Факультет");
        grid.getColumn("students").setHeaderCaption("Список студентов");
        grid.removeColumn("groupId");
        grid.removeColumn("students");
        grid.setImmediate(true);
        grid.setHeight(100f, Unit.PERCENTAGE); //
    }

    private void removeGroup() {
        final Group group = (Group) grid.getSelectedRow();
        if (group != null) {
            try {
                dao.delete(group);
            } catch (ConstraintViolationException e) {
                Notification.show("Нельзя удалить группу, пока в ней есть студенты. " +
                        "Удалите из нее студентов, затем повторите попытку.", Notification.Type.WARNING_MESSAGE);
            } finally {
                refreshTable();
            }
        } else {
            groupNotSelectedWarning();
        }
    }

    private void groupNotSelectedWarning() {
        Notification.show("Группа не выбрана!", Notification.Type.WARNING_MESSAGE);
    }

    private void refreshTable() {
        groups.removeAllItems();
        groups.addAll(dao.findAll(Group.class));
    }
}
