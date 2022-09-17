package com.palm.newbenefit.Module;

public class Member_Team {


    String id;
    String user_id;
    String name;
    String designation_id;
    String designation_name;

    String email;
    String contact_no;

    @Override
    public String toString() {
        return "Member_Team{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", designation_id='" + designation_id + '\'' +
                ", designation_name='" + designation_name + '\'' +
                ", email='" + email + '\'' +
                ", contact_no='" + contact_no + '\'' +
                '}';
    }

    public String getDesignation_name() {
        return designation_name;
    }

    public void setDesignation_name(String designation_name) {
        this.designation_name = designation_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation_id() {
        return designation_id;
    }

    public void setDesignation_id(String designation_id) {
        this.designation_id = designation_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public Member_Team() {
    }

    public Member_Team(String id, String user_id, String name, String designation_id, String designation_name, String email, String contact_no) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.designation_id = designation_id;
        this.designation_name = designation_name;
        this.email = email;
        this.contact_no = contact_no;
    }

    public Member_Team(String id, String user_id, String name, String designation_id, String email, String contact_no) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.designation_id = designation_id;
        this.email = email;
        this.contact_no = contact_no;
    }

}
