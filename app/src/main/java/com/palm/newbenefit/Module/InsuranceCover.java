package com.palm.newbenefit.Module;

public class InsuranceCover {
    String plan_name;
    String policy_name;
    String policy_number;
    String sum_insured;
    String benefit_description;
int data;
int benifit;
    String  id;
    String enrollement_status;
    String getEnrollement_confirmed;

    public String getEnrollement_status() {
        return enrollement_status;
    }

    public void setEnrollement_status(String enrollement_status) {
        this.enrollement_status = enrollement_status;
    }

    public String getGetEnrollement_confirmed() {
        return getEnrollement_confirmed;
    }

    public void setGetEnrollement_confirmed(String getEnrollement_confirmed) {
        this.getEnrollement_confirmed = getEnrollement_confirmed;
    }

    public InsuranceCover(String plan_name, String policy_name, String policy_number, String sum_insured, String benefit_description, int data, int benifit, String id, String enrollement_status, String getEnrollement_confirmed) {
        this.plan_name = plan_name;
        this.policy_name = policy_name;
        this.policy_number = policy_number;
        this.sum_insured = sum_insured;
        this.benefit_description = benefit_description;
        this.data = data;
        this.benifit = benifit;
        this.id = id;
        this.enrollement_status = enrollement_status;
        this.getEnrollement_confirmed = getEnrollement_confirmed;
    }

    @Override
    public String toString() {
        return "InsuranceCover{" +
                "plan_name='" + plan_name + '\'' +
                ", policy_name='" + policy_name + '\'' +
                ", policy_number='" + policy_number + '\'' +
                ", sum_insured='" + sum_insured + '\'' +
                ", benefit_description='" + benefit_description + '\'' +
                ", data=" + data +
                ", benifit=" + benifit +
                ", id='" + id + '\'' +
                ", enrollement_status='" + enrollement_status + '\'' +
                ", getEnrollement_confirmed='" + getEnrollement_confirmed + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InsuranceCover(String plan_name, String policy_name, String policy_number, String sum_insured, String benefit_description, int data, int benifit, String id) {
        this.plan_name = plan_name;
        this.policy_name = policy_name;
        this.policy_number = policy_number;
        this.sum_insured = sum_insured;
        this.benefit_description = benefit_description;
        this.data = data;
        this.benifit = benifit;
        this.id = id;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getBenifit() {
        return benifit;
    }

    public void setBenifit(int benifit) {
        this.benifit = benifit;
    }

    public InsuranceCover() {
    }

    public InsuranceCover(String plan_name, String policy_name, String policy_number, String sum_insured, String benefit_description, int data, int benifit) {
        this.plan_name = plan_name;
        this.policy_name = policy_name;
        this.policy_number = policy_number;
        this.sum_insured = sum_insured;
        this.benefit_description = benefit_description;
        this.data = data;
        this.benifit = benifit;
    }

    public InsuranceCover(String plan_name, String policy_name) {
        this.plan_name = plan_name;
        this.policy_name = policy_name;
    }

    public InsuranceCover(String plan_name) {
        this.plan_name = plan_name;
    }

    public InsuranceCover(String plan_name, String policy_name, String policy_number, String sum_insured, String benefit_description) {
        this.plan_name = plan_name;
        this.policy_name = policy_name;
        this.policy_number = policy_number;
        this.sum_insured = sum_insured;
        this.benefit_description = benefit_description;
    }



    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public String getPolicy_number() {
        return policy_number;
    }

    public void setPolicy_number(String policy_number) {
        this.policy_number = policy_number;
    }

    public String getSum_insured() {
        return sum_insured;
    }

    public void setSum_insured(String sum_insured) {
        this.sum_insured = sum_insured;
    }

    public String getBenefit_description() {
        return benefit_description;
    }

    public void setBenefit_description(String benefit_description) {
        this.benefit_description = benefit_description;
    }
}
