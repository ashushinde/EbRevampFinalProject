package com.palm.newbenefit.Module;

public class CustomerPolicy {
    String company_name;
    String enquiry_id;
    String industry_type;
    String no_employee;
    String email;
    String phone_no;
    String status;

    public CustomerPolicy() {
    }

    public CustomerPolicy(String company_name, String enquiry_id, String industry_type, String no_employee, String email, String phone_no, String status) {
        this.company_name = company_name;
        this.enquiry_id = enquiry_id;
        this.industry_type = industry_type;
        this.no_employee = no_employee;
        this.email = email;
        this.phone_no = phone_no;
        this.status = status;
    }

    @Override
    public String toString() {
        return "CustomerPolicy{" +
                "company_name='" + company_name + '\'' +
                ", enquiry_id='" + enquiry_id + '\'' +
                ", industry_type='" + industry_type + '\'' +
                ", no_employee='" + no_employee + '\'' +
                ", email='" + email + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getEnquiry_id() {
        return enquiry_id;
    }

    public void setEnquiry_id(String enquiry_id) {
        this.enquiry_id = enquiry_id;
    }

    public String getIndustry_type() {
        return industry_type;
    }

    public void setIndustry_type(String industry_type) {
        this.industry_type = industry_type;
    }

    public String getNo_employee() {
        return no_employee;
    }

    public void setNo_employee(String no_employee) {
        this.no_employee = no_employee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
