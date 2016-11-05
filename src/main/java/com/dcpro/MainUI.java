package com.dcpro;

import com.dcpro.view.tabs.GroupTab;
import com.dcpro.view.tabs.StudentTab;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private final VerticalLayout contentLayout = new VerticalLayout();
    private final TabSheet tabSheet = new TabSheet();
    private final GroupTab groupTab = new GroupTab();
    private final StudentTab studentTab = new StudentTab();

    @Override
    protected void init(VaadinRequest request) {
        setComponentMargin();
        tabSheetInit();
        contentLayout.addComponents(tabSheet);
        contentLayout.setSizeFull();
        setContent(contentLayout);
    }

    private void setComponentMargin() {
        studentTab.setMargin(true);
        groupTab.setMargin(true);
    }

    private void tabSheetInit() {
        tabSheet.setHeight(100f, Unit.PERCENTAGE);
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        tabSheet.addTab(groupTab, "Группы");
        tabSheet.addTab(studentTab, "Студенты");
    }
}