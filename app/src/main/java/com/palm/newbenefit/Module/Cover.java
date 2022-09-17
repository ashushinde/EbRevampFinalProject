package com.palm.newbenefit.Module;

public class Cover {

    String id = null;
    String price_per_employee = null;
    String industry_id = null;
    String industry_name = null;
    String sub_industry_id = null;
    String sub_industry_name = null;
    String cover_type_id = null;
    String cover_type_name = null;
    String cover_type_title = null;
    String cover_type_content = null;
    String cover_type_logo = null;
    String policy_sub_type_id = null;
    String policy_sub_type_name = null;

    public Cover() {
    }

    public Cover(String id, String price_per_employee, String industry_id, String industry_name, String sub_industry_id, String sub_industry_name, String cover_type_id, String cover_type_name, String cover_type_title, String cover_type_content, String cover_type_logo, String policy_sub_type_id, String policy_sub_type_name) {
        this.id = id;
        this.price_per_employee = price_per_employee;
        this.industry_id = industry_id;
        this.industry_name = industry_name;
        this.sub_industry_id = sub_industry_id;
        this.sub_industry_name = sub_industry_name;
        this.cover_type_id = cover_type_id;
        this.cover_type_name = cover_type_name;
        this.cover_type_title = cover_type_title;
        this.cover_type_content = cover_type_content;
        this.cover_type_logo = cover_type_logo;
        this.policy_sub_type_id = policy_sub_type_id;
        this.policy_sub_type_name = policy_sub_type_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice_per_employee() {
        return price_per_employee;
    }

    public void setPrice_per_employee(String price_per_employee) {
        this.price_per_employee = price_per_employee;
    }

    public String getIndustry_id() {
        return industry_id;
    }

    public void setIndustry_id(String industry_id) {
        this.industry_id = industry_id;
    }

    public String getIndustry_name() {
        return industry_name;
    }

    public void setIndustry_name(String industry_name) {
        this.industry_name = industry_name;
    }

    public String getSub_industry_id() {
        return sub_industry_id;
    }

    public void setSub_industry_id(String sub_industry_id) {
        this.sub_industry_id = sub_industry_id;
    }

    public String getSub_industry_name() {
        return sub_industry_name;
    }

    public void setSub_industry_name(String sub_industry_name) {
        this.sub_industry_name = sub_industry_name;
    }

    public String getCover_type_id() {
        return cover_type_id;
    }

    public void setCover_type_id(String cover_type_id) {
        this.cover_type_id = cover_type_id;
    }

    public String getCover_type_name() {
        return cover_type_name;
    }

    public void setCover_type_name(String cover_type_name) {
        this.cover_type_name = cover_type_name;
    }

    public String getCover_type_title() {
        return cover_type_title;
    }

    public void setCover_type_title(String cover_type_title) {
        this.cover_type_title = cover_type_title;
    }

    public String getCover_type_content() {
        return cover_type_content;
    }

    public void setCover_type_content(String cover_type_content) {
        this.cover_type_content = cover_type_content;
    }

    public String getCover_type_logo() {
        return cover_type_logo;
    }

    public void setCover_type_logo(String cover_type_logo) {
        this.cover_type_logo = cover_type_logo;
    }

    public String getPolicy_sub_type_id() {
        return policy_sub_type_id;
    }

    public void setPolicy_sub_type_id(String policy_sub_type_id) {
        this.policy_sub_type_id = policy_sub_type_id;
    }

    public String getPolicy_sub_type_name() {
        return policy_sub_type_name;
    }

    public void setPolicy_sub_type_name(String policy_sub_type_name) {
        this.policy_sub_type_name = policy_sub_type_name;
    }

    @Override
    public String toString() {
        return "Cover{" +
                "id='" + id + '\'' +
                ", price_per_employee='" + price_per_employee + '\'' +
                ", industry_id='" + industry_id + '\'' +
                ", industry_name='" + industry_name + '\'' +
                ", sub_industry_id='" + sub_industry_id + '\'' +
                ", sub_industry_name='" + sub_industry_name + '\'' +
                ", cover_type_id='" + cover_type_id + '\'' +
                ", cover_type_name='" + cover_type_name + '\'' +
                ", cover_type_title='" + cover_type_title + '\'' +
                ", cover_type_content='" + cover_type_content + '\'' +
                ", cover_type_logo='" + cover_type_logo + '\'' +
                ", policy_sub_type_id='" + policy_sub_type_id + '\'' +
                ", policy_sub_type_name='" + policy_sub_type_name + '\'' +
                '}';
    }
}
