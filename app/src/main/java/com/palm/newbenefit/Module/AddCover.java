package com.palm.newbenefit.Module;

public class AddCover {
    String policy_id;
    String policy_sub_type_name;
    String policy_sub_type_id;
    String has_flex;
    String has_payroll;
    String enrollement_status;
    String suminsured;
    String premium;
    String description;
    String flexi_benefit_id;
    String premium_type_id;
    String old_suminsured;
    String policyname;

    @Override
    public String toString() {
        return "AddCover{" +
                "policy_id='" + policy_id + '\'' +
                ", policy_sub_type_name='" + policy_sub_type_name + '\'' +
                ", policy_sub_type_id='" + policy_sub_type_id + '\'' +
                ", has_flex='" + has_flex + '\'' +
                ", has_payroll='" + has_payroll + '\'' +
                ", enrollement_status='" + enrollement_status + '\'' +
                ", suminsured='" + suminsured + '\'' +
                ", premium='" + premium + '\'' +
                ", description='" + description + '\'' +
                ", flexi_benefit_id='" + flexi_benefit_id + '\'' +
                ", premium_type_id='" + premium_type_id + '\'' +
                ", old_suminsured='" + old_suminsured + '\'' +
                ", policyname='" + policyname + '\'' +
                '}';
    }

    public String getPolicyname() {
        return policyname;
    }

    public void setPolicyname(String policyname) {
        this.policyname = policyname;
    }

    public String getOld_suminsured() {
        return old_suminsured;
    }

    public void setOld_suminsured(String old_suminsured) {
        this.old_suminsured = old_suminsured;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlexi_benefit_id() {
        return flexi_benefit_id;
    }

    public void setFlexi_benefit_id(String flexi_benefit_id) {
        this.flexi_benefit_id = flexi_benefit_id;
    }

    public String getPremium_type_id() {
        return premium_type_id;
    }

    public void setPremium_type_id(String premium_type_id) {
        this.premium_type_id = premium_type_id;
    }

    public AddCover() {
    }

    public AddCover(String policy_id, String policy_sub_type_name, String policy_sub_type_id, String has_flex, String has_payroll, String enrollement_status, String suminsured, String premium, String description, String flexi_benefit_id, String premium_type_id) {
        this.policy_id = policy_id;
        this.policy_sub_type_name = policy_sub_type_name;
        this.policy_sub_type_id = policy_sub_type_id;
        this.has_flex = has_flex;
        this.has_payroll = has_payroll;
        this.enrollement_status = enrollement_status;
        this.suminsured = suminsured;
        this.premium = premium;
        this.description = description;
        this.flexi_benefit_id = flexi_benefit_id;
        this.premium_type_id = premium_type_id;
    }

    public AddCover(String policy_id, String policy_sub_type_name, String policy_sub_type_id, String has_flex, String has_payroll, String enrollement_status, String suminsured, String premium, String description, String flexi_benefit_id, String premium_type_id,String old_suminsured) {
        this.policy_id = policy_id;
        this.policy_sub_type_name = policy_sub_type_name;
        this.policy_sub_type_id = policy_sub_type_id;
        this.has_flex = has_flex;
        this.has_payroll = has_payroll;
        this.enrollement_status = enrollement_status;
        this.suminsured = suminsured;
        this.premium = premium;
        this.description = description;
        this.flexi_benefit_id = flexi_benefit_id;
        this.premium_type_id = premium_type_id;
        this.old_suminsured=old_suminsured;
    }

    public AddCover(String policy_id, String policy_sub_type_name,
                    String policy_sub_type_id, String has_flex,
                    String has_payroll, String enrollement_status,
                    String suminsured, String premium, String description,
                    String flexi_benefit_id, String premium_type_id,String old_suminsured,
    String policyname) {
        this.policy_id = policy_id;
        this.policy_sub_type_name = policy_sub_type_name;
        this.policy_sub_type_id = policy_sub_type_id;
        this.has_flex = has_flex;
        this.has_payroll = has_payroll;
        this.enrollement_status = enrollement_status;
        this.suminsured = suminsured;
        this.premium = premium;
        this.description = description;
        this.flexi_benefit_id = flexi_benefit_id;
        this.premium_type_id = premium_type_id;
        this.old_suminsured=old_suminsured;
        this.policyname=policyname;
    }

    public String getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(String policy_id) {
        this.policy_id = policy_id;
    }

    public String getPolicy_sub_type_name() {
        return policy_sub_type_name;
    }

    public void setPolicy_sub_type_name(String policy_sub_type_name) {
        this.policy_sub_type_name = policy_sub_type_name;
    }

    public String getPolicy_sub_type_id() {
        return policy_sub_type_id;
    }

    public void setPolicy_sub_type_id(String policy_sub_type_id) {
        this.policy_sub_type_id = policy_sub_type_id;
    }

    public String getHas_flex() {
        return has_flex;
    }

    public void setHas_flex(String has_flex) {
        this.has_flex = has_flex;
    }

    public String getHas_payroll() {
        return has_payroll;
    }

    public void setHas_payroll(String has_payroll) {
        this.has_payroll = has_payroll;
    }

    public String getEnrollement_status() {
        return enrollement_status;
    }

    public void setEnrollement_status(String enrollement_status) {
        this.enrollement_status = enrollement_status;
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
}
