package com.palm.newbenefit.Module;

public class FamilyData {

    String family_id = null;
    String family_firstname = null;
    String family_lastname = null;
    String family_flat = null;
    String family_contact = null;

    String family_dob = null;
    String fr_name = null;
    String family_pincode = null;
    String cities = null;
    String state_names = null;
    String family_location = null;
    String family_email = null;
    String family_street = null;
    String marriage_date;

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public String getFamily_firstname() {
        return family_firstname;
    }

    public void setFamily_firstname(String family_firstname) {
        this.family_firstname = family_firstname;
    }

    public String getFamily_lastname() {
        return family_lastname;
    }

    public void setFamily_lastname(String family_lastname) {
        this.family_lastname = family_lastname;
    }

    public String getFamily_flat() {
        return family_flat;
    }

    public void setFamily_flat(String family_flat) {
        this.family_flat = family_flat;
    }

    public String getFamily_contact() {
        return family_contact;
    }

    public void setFamily_contact(String family_contact) {
        this.family_contact = family_contact;
    }

    public String getFamily_dob() {
        return family_dob;
    }

    public void setFamily_dob(String family_dob) {
        this.family_dob = family_dob;
    }

    public String getFr_name() {
        return fr_name;
    }

    public void setFr_name(String fr_name) {
        this.fr_name = fr_name;
    }

    public String getFamily_pincode() {
        return family_pincode;
    }

    public void setFamily_pincode(String family_pincode) {
        this.family_pincode = family_pincode;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public String getState_names() {
        return state_names;
    }

    public void setState_names(String state_names) {
        this.state_names = state_names;
    }

    public String getFamily_location() {
        return family_location;
    }

    public void setFamily_location(String family_location) {
        this.family_location = family_location;
    }

    public String getFamily_email() {
        return family_email;
    }

    public void setFamily_email(String family_email) {
        this.family_email = family_email;
    }

    public String getFamily_street() {
        return family_street;
    }

    public void setFamily_street(String family_street) {
        this.family_street = family_street;
    }

    public String getMarriage_date() {
        return marriage_date;
    }

    public void setMarriage_date(String marriage_date) {
        this.marriage_date = marriage_date;
    }

    public FamilyData() {
    }

    @Override
    public String toString() {
        return "FamilyData{" +
                "family_id='" + family_id + '\'' +
                ", family_firstname='" + family_firstname + '\'' +
                ", family_lastname='" + family_lastname + '\'' +
                ", family_flat='" + family_flat + '\'' +
                ", family_contact='" + family_contact + '\'' +
                ", family_dob='" + family_dob + '\'' +
                ", fr_name='" + fr_name + '\'' +
                ", family_pincode='" + family_pincode + '\'' +
                ", cities='" + cities + '\'' +
                ", state_names='" + state_names + '\'' +
                ", family_location='" + family_location + '\'' +
                ", family_email='" + family_email + '\'' +
                ", family_street='" + family_street + '\'' +
                ", marriage_date='" + marriage_date + '\'' +
                '}';
    }

    public FamilyData(String family_id, String family_firstname, String family_lastname, String family_flat, String family_contact, String family_dob, String fr_name, String family_pincode, String cities, String state_names, String family_location, String family_email, String family_street, String marriage_date) {
        this.family_id = family_id;
        this.family_firstname = family_firstname;
        this.family_lastname = family_lastname;
        this.family_flat = family_flat;
        this.family_contact = family_contact;
        this.family_dob = family_dob;
        this.fr_name = fr_name;
        this.family_pincode = family_pincode;
        this.cities = cities;
        this.state_names = state_names;
        this.family_location = family_location;
        this.family_email = family_email;
        this.family_street = family_street;
        this.marriage_date = marriage_date;
    }


}