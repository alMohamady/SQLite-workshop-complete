package com.masafa_eg.sqliteteast01;

/**
 * Created by amohamady on 01/16/2018.
 */

public class ContactModel {

    private String ID, firstName, lastName;

    public ContactModel() {
        this.ID = "";
        this.firstName = "";
        this.lastName = "";
    }

    public ContactModel(String ID, String firstName, String lastName) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
