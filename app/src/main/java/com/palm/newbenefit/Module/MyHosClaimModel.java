package com.palm.newbenefit.Module;

public class MyHosClaimModel {



    String emp_id;
    String claim_reimb_id;
    String name;
    String created_at;
    String total_claim_amount;
    String claim_type;
    String claim_approved_amount;
    String claim_reimb_reason;

    String member_id;
    String policy_id;
    String claim_request_date;

    public MyHosClaimModel(String emp_id, String claim_reimb_id, String name, String created_at, String total_claim_amount, String claim_type, String claim_approved_amount, String claim_reimb_reason, String member_id, String policy_id, String claim_request_date) {
        this.emp_id = emp_id;
        this.claim_reimb_id = claim_reimb_id;
        this.name = name;
        this.created_at = created_at;
        this.total_claim_amount = total_claim_amount;
        this.claim_type = claim_type;
        this.claim_approved_amount = claim_approved_amount;
        this.claim_reimb_reason = claim_reimb_reason;
        this.member_id = member_id;
        this.policy_id = policy_id;
        this.claim_request_date = claim_request_date;
    }

    public String getClaim_request_date() {
        return claim_request_date;
    }

    public void setClaim_request_date(String claim_request_date) {
        this.claim_request_date = claim_request_date;
    }

    public MyHosClaimModel(String emp_id, String claim_reimb_id, String name, String created_at, String total_claim_amount, String claim_type, String claim_approved_amount, String claim_reimb_reason, String member_id, String policy_id) {
        this.emp_id = emp_id;
        this.claim_reimb_id = claim_reimb_id;
        this.name = name;
        this.created_at = created_at;
        this.total_claim_amount = total_claim_amount;
        this.claim_type = claim_type;
        this.claim_approved_amount = claim_approved_amount;
        this.claim_reimb_reason = claim_reimb_reason;
        this.member_id = member_id;
        this.policy_id = policy_id;
    }

    @Override
    public String toString() {
        return "MyHosClaimModel{" +
                "emp_id='" + emp_id + '\'' +
                ", claim_reimb_id='" + claim_reimb_id + '\'' +
                ", name='" + name + '\'' +
                ", created_at='" + created_at + '\'' +
                ", total_claim_amount='" + total_claim_amount + '\'' +
                ", claim_type='" + claim_type + '\'' +
                ", claim_approved_amount='" + claim_approved_amount + '\'' +
                ", claim_reimb_reason='" + claim_reimb_reason + '\'' +
                ", member_id='" + member_id + '\'' +
                ", policy_id='" + policy_id + '\'' +
                '}';
    }

    public String getMember_id() {


        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(String policy_id) {
        this.policy_id = policy_id;
    }

    public String getClaim_type() {
        return claim_type;
    }

    public void setClaim_type(String claim_type) {
        this.claim_type = claim_type;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getClaim_reimb_id() {
        return claim_reimb_id;
    }

    public void setClaim_reimb_id(String claim_reimb_id) {
        this.claim_reimb_id = claim_reimb_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTotal_claim_amount() {
        return total_claim_amount;
    }

    public void setTotal_claim_amount(String total_claim_amount) {
        this.total_claim_amount = total_claim_amount;
    }

    public String getClaim_approved_amount() {
        return claim_approved_amount;
    }

    public void setClaim_approved_amount(String claim_approved_amount) {
        this.claim_approved_amount = claim_approved_amount;
    }

    public String getClaim_reimb_reason() {
        return claim_reimb_reason;
    }

    public void setClaim_reimb_reason(String claim_reimb_reason) {
        this.claim_reimb_reason = claim_reimb_reason;
    }







}
