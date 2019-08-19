package com.twu.biblioteca.model;

public class LibraryItem {
    String title;
    private boolean checkedOut;

    LibraryItem(String title, boolean checkedOut) {
        this.title = title;
        this.checkedOut = checkedOut;
    }

    public String getTitle() {
        return title;
    }

    boolean isCheckedOut() {
        return checkedOut;
    }

    void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }
}
