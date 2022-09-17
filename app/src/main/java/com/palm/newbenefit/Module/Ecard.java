package com.palm.newbenefit.Module;

public class Ecard {

    String name;
    String relation_name;
    String member_id;
    String emp_code;
    String ecard_url;
    String tpa_member_id;
    String tpa_emp_id;
    String policy_no;
    String start_date;
    String end_date;
    String email;
    String mobile;

    public Ecard() {
    }

    @Override
    public String toString() {
        return "Ecard{" +
                "name='" + name + '\'' +
                ", relation_name='" + relation_name + '\'' +
                ", member_id='" + member_id + '\'' +
                ", emp_code='" + emp_code + '\'' +
                ", ecard_url='" + ecard_url + '\'' +
                ", tpa_member_id='" + tpa_member_id + '\'' +
                ", tpa_emp_id='" + tpa_emp_id + '\'' +
                ", policy_no='" + policy_no + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }

    public Ecard(String name, String relation_name,
                 String member_id, String emp_code, String ecard_url,
                 String tpa_member_id, String tpa_emp_id, String policy_no,
                 String start_date, String end_date, String email, String mobile) {
        this.name = name;
        this.relation_name = relation_name;
        this.member_id = member_id;
        this.emp_code = emp_code;
        this.ecard_url = ecard_url;
        this.tpa_member_id = tpa_member_id;
        this.tpa_emp_id = tpa_emp_id;
        this.policy_no = policy_no;
        this.start_date = start_date;
        this.end_date = end_date;
        this.email = email;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation_name() {
        return relation_name;
    }

    public void setRelation_name(String relation_name) {
        this.relation_name = relation_name;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public String getEcard_url() {
        return ecard_url;
    }

    public void setEcard_url(String ecard_url) {
        this.ecard_url = ecard_url;
    }

    public String getTpa_member_id() {
        return tpa_member_id;
    }

    public void setTpa_member_id(String tpa_member_id) {
        this.tpa_member_id = tpa_member_id;
    }

    public String getTpa_emp_id() {
        return tpa_emp_id;
    }

    public void setTpa_emp_id(String tpa_emp_id) {
        this.tpa_emp_id = tpa_emp_id;
    }

    public String getPolicy_no() {
        return policy_no;
    }

    public void setPolicy_no(String policy_no) {
        this.policy_no = policy_no;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
