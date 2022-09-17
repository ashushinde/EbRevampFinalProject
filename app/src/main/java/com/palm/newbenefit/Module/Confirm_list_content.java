package com.palm.newbenefit.Module;

public class Confirm_list_content {

    String name;
    String eid;
    String dob;
    String relationship;
    String email;
    String sum_insured;
    String gender;

    public Confirm_list_content() {
    }

    @Override
    public String toString() {
        return "Confirm_list_content{" +
                "name='" + name + '\'' +
                ", eid='" + eid + '\'' +
                ", dob='" + dob + '\'' +
                ", relationship='" + relationship + '\'' +
                ", email='" + email + '\'' +
                ", sum_insured='" + sum_insured + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSum_insured() {
        return sum_insured;
    }

    public void setSum_insured(String sum_insured) {
        this.sum_insured = sum_insured;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Confirm_list_content(String name, String eid, String dob, String relationship, String email, String sum_insured, String gender) {
        this.name = name;
        this.eid = eid;
        this.dob = dob;
        this.relationship = relationship;
        this.email = email;
        this.sum_insured = sum_insured;
        this.gender = gender;
    }
}
