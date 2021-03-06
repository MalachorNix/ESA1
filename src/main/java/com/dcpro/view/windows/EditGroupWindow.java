package com.dcpro.view.windows;

public class EditGroupWindow extends GroupWindow {

    public EditGroupWindow() {
        super();
    }

    @Override
    protected void okButtonAddClickListener() {
        getOkButton().addClickListener(clickEvent -> updateGroup());
    }

    private void updateGroup() {
        if (isValidFieldData()) {
            getDao().update(this.getGroup());
            this.close();
        }
    }
}
