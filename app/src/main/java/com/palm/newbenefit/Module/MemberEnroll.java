package com.palm.newbenefit.Module;

public class MemberEnroll {



    String company_id = null;
    String tpa_member_name = null;
    String tpa_member_id = null;
    String policy_detail_id = null;
    String broker_id = null;

    String family_relation_id = null;
    String status = null;
    String policy_no = null;
    String TPA_id = null;
    String policy_sub_type_id = null;
    String family_id = null;
    String fr_id = null;
    String fr_name = null;
    String emp_firstname = null;
    String emp_lastname = null;


    String gender = null;
    String bdate = null;
    String member_id = null;

    String age = null;
    String age_type = null;
    String policy_mem_sum_premium = null;
    String start_date = null;
    String policy_member_id = null;
    String employee_policy_mem_sum_premium = null;
    String pay_type = null;
    String policy_mem_sum_insured = null;

    public MemberEnroll() {
    }

    @Override
    public String toString() {
        return "MemberEnroll{" +
                "company_id='" + company_id + '\'' +
                ", tpa_member_name='" + tpa_member_name + '\'' +
                ", tpa_member_id='" + tpa_member_id + '\'' +
                ", policy_detail_id='" + policy_detail_id + '\'' +
                ", broker_id='" + broker_id + '\'' +
                ", family_relation_id='" + family_relation_id + '\'' +
                ", status='" + status + '\'' +
                ", policy_no='" + policy_no + '\'' +
                ", TPA_id='" + TPA_id + '\'' +
                ", policy_sub_type_id='" + policy_sub_type_id + '\'' +
                ", family_id='" + family_id + '\'' +
                ", fr_id='" + fr_id + '\'' +
                ", fr_name='" + fr_name + '\'' +
                ", emp_firstname='" + emp_firstname + '\'' +
                ", emp_lastname='" + emp_lastname + '\'' +
                ", gender='" + gender + '\'' +
                ", bdate='" + bdate + '\'' +
                ", member_id='" + member_id + '\'' +
                ", age='" + age + '\'' +
                ", age_type='" + age_type + '\'' +
                ", policy_mem_sum_premium='" + policy_mem_sum_premium + '\'' +
                ", start_date='" + start_date + '\'' +
                ", policy_member_id='" + policy_member_id + '\'' +
                ", employee_policy_mem_sum_premium='" + employee_policy_mem_sum_premium + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", policy_mem_sum_insured='" + policy_mem_sum_insured + '\'' +
                '}';
    }

    public MemberEnroll(String company_id, String tpa_member_name, String tpa_member_id, String policy_detail_id, String broker_id, String family_relation_id, String status, String policy_no, String TPA_id, String policy_sub_type_id, String family_id, String fr_id, String fr_name, String emp_firstname, String emp_lastname, String gender, String bdate, String member_id, String age, String age_type, String policy_mem_sum_premium, String start_date, String policy_member_id, String employee_policy_mem_sum_premium, String pay_type, String policy_mem_sum_insured) {
        this.company_id = company_id;
        this.tpa_member_name = tpa_member_name;
        this.tpa_member_id = tpa_member_id;
        this.policy_detail_id = policy_detail_id;
        this.broker_id = broker_id;
        this.family_relation_id = family_relation_id;
        this.status = status;
        this.policy_no = policy_no;
        this.TPA_id = TPA_id;
        this.policy_sub_type_id = policy_sub_type_id;
        this.family_id = family_id;
        this.fr_id = fr_id;
        this.fr_name = fr_name;
        this.emp_firstname = emp_firstname;
        this.emp_lastname = emp_lastname;
        this.gender = gender;
        this.bdate = bdate;
        this.member_id = member_id;
        this.age = age;
        this.age_type = age_type;
        this.policy_mem_sum_premium = policy_mem_sum_premium;
        this.start_date = start_date;
        this.policy_member_id = policy_member_id;
        this.employee_policy_mem_sum_premium = employee_policy_mem_sum_premium;
        this.pay_type = pay_type;
        this.policy_mem_sum_insured = policy_mem_sum_insured;
    }

    public String getPolicy_mem_sum_insured() {
        return policy_mem_sum_insured;
    }

    public void setPolicy_mem_sum_insured(String policy_mem_sum_insured) {
        this.policy_mem_sum_insured = policy_mem_sum_insured;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getTpa_member_name() {
        return tpa_member_name;
    }

    public void setTpa_member_name(String tpa_member_name) {
        this.tpa_member_name = tpa_member_name;
    }

    public String getTpa_member_id() {
        return tpa_member_id;
    }

    public void setTpa_member_id(String tpa_member_id) {
        this.tpa_member_id = tpa_member_id;
    }

    public String getPolicy_detail_id() {
        return policy_detail_id;
    }

    public void setPolicy_detail_id(String policy_detail_id) {
        this.policy_detail_id = policy_detail_id;
    }

    public String getBroker_id() {
        return broker_id;
    }

    public void setBroker_id(String broker_id) {
        this.broker_id = broker_id;
    }

    public String getFamily_relation_id() {
        return family_relation_id;
    }

    public void setFamily_relation_id(String family_relation_id) {
        this.family_relation_id = family_relation_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPolicy_no() {
        return policy_no;
    }

    public void setPolicy_no(String policy_no) {
        this.policy_no = policy_no;
    }

    public String getTPA_id() {
        return TPA_id;
    }

    public void setTPA_id(String TPA_id) {
        this.TPA_id = TPA_id;
    }

    public String getPolicy_sub_type_id() {
        return policy_sub_type_id;
    }

    public void setPolicy_sub_type_id(String policy_sub_type_id) {
        this.policy_sub_type_id = policy_sub_type_id;
    }

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public String getFr_id() {
        return fr_id;
    }

    public void setFr_id(String fr_id) {
        this.fr_id = fr_id;
    }

    public String getFr_name() {
        return fr_name;
    }

    public void setFr_name(String fr_name) {
        this.fr_name = fr_name;
    }

    public String getEmp_firstname() {
        return emp_firstname;
    }

    public void setEmp_firstname(String emp_firstname) {
        this.emp_firstname = emp_firstname;
    }

    public String getEmp_lastname() {
        return emp_lastname;
    }

    public void setEmp_lastname(String emp_lastname) {
        this.emp_lastname = emp_lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge_type() {
        return age_type;
    }

    public void setAge_type(String age_type) {
        this.age_type = age_type;
    }

    public String getPolicy_mem_sum_premium() {
        return policy_mem_sum_premium;
    }

    public void setPolicy_mem_sum_premium(String policy_mem_sum_premium) {
        this.policy_mem_sum_premium = policy_mem_sum_premium;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getPolicy_member_id() {
        return policy_member_id;
    }

    public void setPolicy_member_id(String policy_member_id) {
        this.policy_member_id = policy_member_id;
    }

    public String getEmployee_policy_mem_sum_premium() {
        return employee_policy_mem_sum_premium;
    }

    public void setEmployee_policy_mem_sum_premium(String employee_policy_mem_sum_premium) {
        this.employee_policy_mem_sum_premium = employee_policy_mem_sum_premium;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }




}
