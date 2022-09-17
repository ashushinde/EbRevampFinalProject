package com.palm.newbenefit.Module;

public class NomineeEnroll {



    String fr_name ;
    String nominee_id ;
    String emp_id ;
    String nominee_fname;
    String nominee_lname ;
    String fr_id ;
    String guardian_relation ;
    String guardian_fname ;
    String guardian_lname;
    String guardian_dob ;
    String share_percentile ;
    String nominee_dob ;
    String confirmed_flag ;
    String confirmed_date;
    String status ;
    String created_at;

    public NomineeEnroll() {
    }

    public NomineeEnroll(String fr_name, String nominee_id, String emp_id, String nominee_fname, String nominee_lname, String fr_id, String guardian_relation, String guardian_fname, String guardian_lname, String guardian_dob, String share_percentile, String nominee_dob, String confirmed_flag, String confirmed_date, String status, String created_at) {
        this.fr_name = fr_name;
        this.nominee_id = nominee_id;
        this.emp_id = emp_id;
        this.nominee_fname = nominee_fname;
        this.nominee_lname = nominee_lname;
        this.fr_id = fr_id;
        this.guardian_relation = guardian_relation;
        this.guardian_fname = guardian_fname;
        this.guardian_lname = guardian_lname;
        this.guardian_dob = guardian_dob;
        this.share_percentile = share_percentile;
        this.nominee_dob = nominee_dob;
        this.confirmed_flag = confirmed_flag;
        this.confirmed_date = confirmed_date;
        this.status = status;
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "NomineeEnroll{" +
                "fr_name='" + fr_name + '\'' +
                ", nominee_id='" + nominee_id + '\'' +
                ", emp_id='" + emp_id + '\'' +
                ", nominee_fname='" + nominee_fname + '\'' +
                ", nominee_lname='" + nominee_lname + '\'' +
                ", fr_id='" + fr_id + '\'' +
                ", guardian_relation='" + guardian_relation + '\'' +
                ", guardian_fname='" + guardian_fname + '\'' +
                ", guardian_lname='" + guardian_lname + '\'' +
                ", guardian_dob='" + guardian_dob + '\'' +
                ", share_percentile='" + share_percentile + '\'' +
                ", nominee_dob='" + nominee_dob + '\'' +
                ", confirmed_flag='" + confirmed_flag + '\'' +
                ", confirmed_date='" + confirmed_date + '\'' +
                ", status='" + status + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }

    public String getFr_name() {
        return fr_name;
    }

    public void setFr_name(String fr_name) {
        this.fr_name = fr_name;
    }

    public String getNominee_id() {
        return nominee_id;
    }

    public void setNominee_id(String nominee_id) {
        this.nominee_id = nominee_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getNominee_fname() {
        return nominee_fname;
    }

    public void setNominee_fname(String nominee_fname) {
        this.nominee_fname = nominee_fname;
    }

    public String getNominee_lname() {
        return nominee_lname;
    }

    public void setNominee_lname(String nominee_lname) {
        this.nominee_lname = nominee_lname;
    }

    public String getFr_id() {
        return fr_id;
    }

    public void setFr_id(String fr_id) {
        this.fr_id = fr_id;
    }

    public String getGuardian_relation() {
        return guardian_relation;
    }

    public void setGuardian_relation(String guardian_relation) {
        this.guardian_relation = guardian_relation;
    }

    public String getGuardian_fname() {
        return guardian_fname;
    }

    public void setGuardian_fname(String guardian_fname) {
        this.guardian_fname = guardian_fname;
    }

    public String getGuardian_lname() {
        return guardian_lname;
    }

    public void setGuardian_lname(String guardian_lname) {
        this.guardian_lname = guardian_lname;
    }

    public String getGuardian_dob() {
        return guardian_dob;
    }

    public void setGuardian_dob(String guardian_dob) {
        this.guardian_dob = guardian_dob;
    }

    public String getShare_percentile() {
        return share_percentile;
    }

    public void setShare_percentile(String share_percentile) {
        this.share_percentile = share_percentile;
    }

    public String getNominee_dob() {
        return nominee_dob;
    }

    public void setNominee_dob(String nominee_dob) {
        this.nominee_dob = nominee_dob;
    }

    public String getConfirmed_flag() {
        return confirmed_flag;
    }

    public void setConfirmed_flag(String confirmed_flag) {
        this.confirmed_flag = confirmed_flag;
    }

    public String getConfirmed_date() {
        return confirmed_date;
    }

    public void setConfirmed_date(String confirmed_date) {
        this.confirmed_date = confirmed_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
