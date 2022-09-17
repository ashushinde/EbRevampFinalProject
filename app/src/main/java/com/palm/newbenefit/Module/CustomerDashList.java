package com.palm.newbenefit.Module;

public class CustomerDashList {
    String id;
    String policy_name;
    String plan_name;
    String sum_insured;
    String premium;
    String status;
    String total_emp;
    String total_lives;
    String dependant_lives;

    public CustomerDashList() {
    }

    public CustomerDashList(String id, String policy_name, String plan_name, String sum_insured, String premium, String status, String total_emp, String total_lives, String dependant_lives) {
        this.id = id;
        this.policy_name = policy_name;
        this.plan_name = plan_name;
        this.sum_insured = sum_insured;
        this.premium = premium;
        this.status = status;
        this.total_emp = total_emp;
        this.total_lives = total_lives;
        this.dependant_lives = dependant_lives;
    }

    public CustomerDashList(String policy_name, String plan_name, String sum_insured, String premium, String status, String total_emp, String total_lives, String dependant_lives) {
        this.policy_name = policy_name;
        this.plan_name = plan_name;
        this.sum_insured = sum_insured;
        this.premium = premium;
        this.status = status;
        this.total_emp = total_emp;
        this.total_lives = total_lives;
        this.dependant_lives = dependant_lives;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CustomerDashList{" +
                "id='" + id + '\'' +
                ", policy_name='" + policy_name + '\'' +
                ", plan_name='" + plan_name + '\'' +
                ", sum_insured='" + sum_insured + '\'' +
                ", premium='" + premium + '\'' +
                ", status='" + status + '\'' +
                ", total_emp='" + total_emp + '\'' +
                ", total_lives='" + total_lives + '\'' +
                ", dependant_lives='" + dependant_lives + '\'' +
                '}';
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getSum_insured() {
        return sum_insured;
    }

    public void setSum_insured(String sum_insured) {
        this.sum_insured = sum_insured;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_emp() {
        return total_emp;
    }

    public void setTotal_emp(String total_emp) {
        this.total_emp = total_emp;
    }

    public String getTotal_lives() {
        return total_lives;
    }

    public void setTotal_lives(String total_lives) {
        this.total_lives = total_lives;
    }

    public String getDependant_lives() {
        return dependant_lives;
    }

    public void setDependant_lives(String dependant_lives) {
        this.dependant_lives = dependant_lives;
    }



}
