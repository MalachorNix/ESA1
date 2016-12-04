package com.dcpro.view.windows;

public class EditStudentWindow extends StudentWindow {

    public EditStudentWindow() {
        super();
    }

    @Override
    protected void okButtonAddClickListener() {
        getOkButton().addClickListener(clickEvent -> updateStudent());
    }

    private void updateStudent() {
        if (super.isValidFieldData()) {
            this.getDao().mergeEntity(getStudent());
            this.close();
        }
    }
}
