package com.palm.newbenefit.Module;

public class MyIntimateClaimModel {

    String emp_id;
    String claim_intimate_id;
    String name;
    String claim_type;
    String created_date;
    String claim_Amount;
    String reason;
    String claim_request_date;
    String status;
    String alltype;

    String policy_type;
    String policy_sub_type;
    String type;

    String claim_hospitalization_type;

    @Override
    public String toString() {
        return "MyIntimateClaimModel{" +
                "emp_id='" + emp_id + '\'' +
                ", claim_intimate_id='" + claim_intimate_id + '\'' +
                ", name='" + name + '\'' +
                ", claim_type='" + claim_type + '\'' +
                ", created_date='" + created_date + '\'' +
                ", claim_Amount='" + claim_Amount + '\'' +
                ", reason='" + reason + '\'' +
                ", claim_request_date='" + claim_request_date + '\'' +
                ", status='" + status + '\'' +
                ", alltype='" + alltype + '\'' +
                ", policy_type='" + policy_type + '\'' +
                ", policy_sub_type='" + policy_sub_type + '\'' +
                ", type='" + type + '\'' +
                ", claim_hospitalization_type='" + claim_hospitalization_type + '\'' +
                '}';
    }

    public String getClaim_hospitalization_type() {
        return claim_hospitalization_type;
    }

    public void setClaim_hospitalization_type(String claim_hospitalization_type) {
        this.claim_hospitalization_type = claim_hospitalization_type;
    }

    public MyIntimateClaimModel(String emp_id, String claim_intimate_id, String name,
                                String claim_type, String created_date, String claim_Amount,
                                String reason, String claim_request_date, String status, String alltype,
                                String policy_type, String policy_sub_type, String type
    ) {
        this.emp_id = emp_id;
        this.claim_intimate_id = claim_intimate_id;
        this.name = name;
        this.claim_type = claim_type;
        this.created_date = created_date;
        this.claim_Amount = claim_Amount;
        this.reason = reason;
        this.claim_request_date = claim_request_date;
        this.status = status;
        this.alltype = alltype;
        this.policy_type = policy_type;
        this.policy_sub_type = policy_sub_type;
        this.type = type;
    }


    public MyIntimateClaimModel(String emp_id, String claim_intimate_id, String name,
                                String claim_type, String created_date, String claim_Amount,
                                String reason, String claim_request_date, String status, String alltype,
                                String policy_type, String policy_sub_type, String type,
                                String claim_hospitalization_type
    ) {
        this.emp_id = emp_id;
        this.claim_intimate_id = claim_intimate_id;
        this.name = name;
        this.claim_type = claim_type;
        this.created_date = created_date;
        this.claim_Amount = claim_Amount;
        this.reason = reason;
        this.claim_request_date = claim_request_date;
        this.status = status;
        this.alltype = alltype;
        this.policy_type = policy_type;
        this.policy_sub_type = policy_sub_type;
        this.type = type;
        this.claim_hospitalization_type=claim_hospitalization_type;
    }



    public String getPolicy_type() {
        return policy_type;
    }

    public void setPolicy_type(String policy_type) {
        this.policy_type = policy_type;
    }

    public String getPolicy_sub_type() {
        return policy_sub_type;
    }

    public void setPolicy_sub_type(String policy_sub_type) {
        this.policy_sub_type = policy_sub_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MyIntimateClaimModel(String emp_id, String claim_intimate_id, String name, String claim_type, String created_date, String claim_Amount, String reason, String claim_request_date, String status, String alltype) {
        this.emp_id = emp_id;
        this.claim_intimate_id = claim_intimate_id;
        this.name = name;
        this.claim_type = claim_type;
        this.created_date = created_date;
        this.claim_Amount = claim_Amount;
        this.reason = reason;
        this.claim_request_date = claim_request_date;
        this.status = status;
        this.alltype = alltype;
    }

    public String getAlltype() {
        return alltype;
    }

    public void setAlltype(String alltype) {
        this.alltype = alltype;
    }

    public MyIntimateClaimModel(String emp_id, String claim_intimate_id, String name, String claim_type, String created_date, String claim_Amount, String reason, String claim_request_date, String status) {
        this.emp_id = emp_id;
        this.claim_intimate_id = claim_intimate_id;
        this.name = name;
        this.claim_type = claim_type;
        this.created_date = created_date;
        this.claim_Amount = claim_Amount;
        this.reason = reason;
        this.claim_request_date = claim_request_date;
        this.status = status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MyIntimateClaimModel(String emp_id, String claim_intimate_id, String name, String claim_type, String created_date, String claim_Amount, String reason, String claim_request_date) {
        this.emp_id = emp_id;
        this.claim_intimate_id = claim_intimate_id;
        this.name = name;
        this.claim_type = claim_type;
        this.created_date = created_date;
        this.claim_Amount = claim_Amount;
        this.reason = reason;
        this.claim_request_date = claim_request_date;
    }

    public String getClaim_request_date() {
        return claim_request_date;
    }

    public void setClaim_request_date(String claim_request_date) {
        this.claim_request_date = claim_request_date;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getClaim_intimate_id() {
        return claim_intimate_id;
    }

    public void setClaim_intimate_id(String claim_intimate_id) {
        this.claim_intimate_id = claim_intimate_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClaim_type() {
        return claim_type;
    }

    public void setClaim_type(String claim_type) {
        this.claim_type = claim_type;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getClaim_Amount() {
        return claim_Amount;
    }

    public void setClaim_Amount(String claim_Amount) {
        this.claim_Amount = claim_Amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public MyIntimateClaimModel(String emp_id, String claim_intimate_id, String name, String claim_type, String created_date, String claim_Amount, String reason) {
        this.emp_id = emp_id;
        this.claim_intimate_id = claim_intimate_id;
        this.name = name;
        this.claim_type = claim_type;
        this.created_date = created_date;
        this.claim_Amount = claim_Amount;
        this.reason = reason;
    }

}
