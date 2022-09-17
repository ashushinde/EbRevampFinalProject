package com.palm.newbenefit.Module;

public class Policy {

    String id;
    String cover_type;
    String policy_no;
    String company_name;
    String suminsured;
    String premium;
    String start_date;
    String end_date;
    String status;
    String renew_status;
    String day_left;
    String redirect_url;

    public Policy(String id, String cover_type, String policy_no, String company_name, String suminsured, String premium, String start_date, String end_date, String status, String renew_status, String day_left, String redirect_url) {
        this.id = id;
        this.cover_type = cover_type;
        this.policy_no = policy_no;
        this.company_name = company_name;
        this.suminsured = suminsured;
        this.premium = premium;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.renew_status = renew_status;
        this.day_left = day_left;
        this.redirect_url = redirect_url;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "id='" + id + '\'' +
                ", cover_type='" + cover_type + '\'' +
                ", policy_no='" + policy_no + '\'' +
                ", company_name='" + company_name + '\'' +
                ", suminsured='" + suminsured + '\'' +
                ", premium='" + premium + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status='" + status + '\'' +
                ", renew_status='" + renew_status + '\'' +
                ", day_left='" + day_left + '\'' +
                ", redirect_url='" + redirect_url + '\'' +
                '}';
    }

    public Policy() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover_type() {
        return cover_type;
    }

    public void setCover_type(String cover_type) {
        this.cover_type = cover_type;
    }

    public String getPolicy_no() {
        return policy_no;
    }

    public void setPolicy_no(String policy_no) {
        this.policy_no = policy_no;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getSuminsured() {
        return suminsured;
    }

    public void setSuminsured(String suminsured) {
        this.suminsured = suminsured;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRenew_status() {
        return renew_status;
    }

    public void setRenew_status(String renew_status) {
        this.renew_status = renew_status;
    }

    public String getDay_left() {
        return day_left;
    }

    public void setDay_left(String day_left) {
        this.day_left = day_left;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }
}
