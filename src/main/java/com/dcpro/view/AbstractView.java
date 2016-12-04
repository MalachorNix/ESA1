package com.dcpro.view;

import com.dcpro.dao.DAOModule;
import com.dcpro.dao.DAOService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

public class AbstractView extends VerticalLayout implements View {

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    Class entityClass;

    protected DAOService daoService;

    public AbstractView() {
        Injector injector = Guice.createInjector(new DAOModule());
        daoService = injector.getInstance(DAOService.class);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
