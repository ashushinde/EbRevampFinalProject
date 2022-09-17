package com.palm.newbenefit.models;

public class ContactUsMain {

    String Mainname;
    String subname;
    String empname;
    String emailid;
    String contactno;
    String address;
    String levelcount;

    public ContactUsMain(String mainname, String subname) {
        Mainname = mainname;
        this.subname = subname;
    }

    public ContactUsMain(String mainname, String subname, String empname, String emailid, String contactno, String address, String levelcount) {
        Mainname = mainname;
        this.subname = subname;
        this.empname = empname;
        this.emailid = emailid;
        this.contactno = contactno;
        this.address = address;
        this.levelcount = levelcount;
    }

    public ContactUsMain(String mainname) {
        Mainname = mainname;
    }

    @Override
    public String toString() {
        return "ContactUsMain{" +
                "Mainname='" + Mainname + '\'' +
                ", subname='" + subname + '\'' +
                ", empname='" + empname + '\'' +
                ", emailid='" + emailid + '\'' +
                ", contactno='" + contactno + '\'' +
                ", address='" + address + '\'' +
                ", levelcount='" + levelcount + '\'' +
                '}';
    }

    public String getMainname() {
        return Mainname;
    }

    public void setMainname(String mainname) {
        Mainname = mainname;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLevelcount() {
        return levelcount;
    }

    public void setLevelcount(String levelcount) {
        this.levelcount = levelcount;
    }
}
