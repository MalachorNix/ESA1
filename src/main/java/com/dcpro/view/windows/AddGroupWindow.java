package com.dcpro.view.windows;

import com.dcpro.entities.Group;

public class AddGroupWindow extends GroupWindow {

    public AddGroupWindow() {
        super();
    }

    @Override
    protected void okButtonAddClickListener() {
        getOkButton().addClickListener(clickEvent -> addGroup());
    }

    private void addGroup() {
        if (super.isValidFieldData()) {
            Group group = new Group(this.getGroupNumber(), this.getFaculty());
//            this.getDao().save(group);
            this.getDao().commitEntity(group);
            this.close();
        }
    }
}
