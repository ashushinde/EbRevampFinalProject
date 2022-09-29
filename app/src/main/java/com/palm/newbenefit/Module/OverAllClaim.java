package com.palm.newbenefit.Module;

public class OverAllClaim {
    String claim_id;
    String employer_name;
    String employee_name;
    String member_relation;
    String member_name;
    String claim_amount;
    String settled_amount;
    String claim_type;
    String tpa;
    String claim_registration_date;
    String claim_status;
    String claim_tat;
    String color_code;

    String deduction_amount;

    String type;
    String email;

    String policy_number;

    String empcode;
    String claimtypes;
    String claim_sub_status;

    public OverAllClaim(String claim_id, String employer_name, String employee_name, String member_relation, String member_name, String claim_amount, String settled_amount, String claim_type, String tpa,
                        String claim_registration_date, String claim_status, String claim_tat, String color_code,
                        String deduction_amount,String type,String email,
                        String policy_number,String empcode,String claimtypes,
                        String claim_sub_status) {
        this.claim_id = claim_id;
        this.employer_name = employer_name;
        this.employee_name = employee_name;
        this.member_relation = member_relation;
        this.member_name = member_name;
        this.claim_amount = claim_amount;
        this.settled_amount = settled_amount;
        this.claim_type = claim_type;
        this.tpa = tpa;
        this.claim_registration_date = claim_registration_date;
        this.claim_status = claim_status;
        this.claim_tat = claim_tat;
        this.color_code = color_code;
        this.deduction_amount = deduction_amount;
        this.type = type;
        this.email = email;
        this.policy_number = policy_number;
        this.empcode = empcode;
        this.claimtypes = claimtypes;
        this.claim_sub_status = claim_sub_status;
    }

    @Override
    public String toString() {
        return "OverAllClaim{" +
                "claim_id='" + claim_id + '\'' +
                ", employer_name='" + employer_name + '\'' +
                ", employee_name='" + employee_name + '\'' +
                ", member_relation='" + member_relation + '\'' +
                ", member_name='" + member_name + '\'' +
                ", claim_amount='" + claim_amount + '\'' +
                ", settled_amount='" + settled_amount + '\'' +
                ", claim_type='" + claim_type + '\'' +
                ", tpa='" + tpa + '\'' +
                ", claim_registration_date='" + claim_registration_date + '\'' +
                ", claim_status='" + claim_status + '\'' +
                ", claim_tat='" + claim_tat + '\'' +
                ", color_code='" + color_code + '\'' +
                ", deduction_amount='" + deduction_amount + '\'' +
                ", type='" + type + '\'' +
                ", email='" + email + '\'' +
                ", policy_number='" + policy_number + '\'' +
                ", empcode='" + empcode + '\'' +
                ", claimtypes='" + claimtypes + '\'' +
                ", claim_sub_status='" + claim_sub_status + '\'' +
                '}';
    }

    public String getClaim_sub_status() {
        return claim_sub_status;
    }

    public void setClaim_sub_status(String claim_sub_status) {
        this.claim_sub_status = claim_sub_status;
    }

    public String getPolicy_number() {
        return policy_number;
    }

    public void setPolicy_number(String policy_number) {
        this.policy_number = policy_number;
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public String getClaimtypes() {
        return claimtypes;
    }

    public OverAllClaim() {
    }

    public void setClaimtypes(String claimtypes) {
        this.claimtypes = claimtypes;
    }

    public String getDeduction_amount() {
        return deduction_amount;
    }

    public void setDeduction_amount(String deduction_amount) {
        this.deduction_amount = deduction_amount;
    }

    public String getClaim_id() {
        return claim_id;
    }

    public void setClaim_id(String claim_id) {
        this.claim_id = claim_id;
    }

    public String getEmployer_name() {
        return employer_name;
    }

    public void setEmployer_name(String employer_name) {
        this.employer_name = employer_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getMember_relation() {
        return member_relation;
    }

    public void setMember_relation(String member_relation) {
        this.member_relation = member_relation;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getClaim_amount() {
        return claim_amount;
    }

    public void setClaim_amount(String claim_amount) {
        this.claim_amount = claim_amount;
    }

    public String getSettled_amount() {
        return settled_amount;
    }

    public void setSettled_amount(String settled_amount) {
        this.settled_amount = settled_amount;
    }

    public String getClaim_type() {
        return claim_type;
    }

    public void setClaim_type(String claim_type) {
        this.claim_type = claim_type;
    }

    public String getTpa() {
        return tpa;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTpa(String tpa) {
        this.tpa = tpa;
    }

    public String getClaim_registration_date() {
        return claim_registration_date;
    }

    public void setClaim_registration_date(String claim_registration_date) {
        this.claim_registration_date = claim_registration_date;
    }

    public String getClaim_status() {
        return claim_status;
    }

    public void setClaim_status(String claim_status) {
        this.claim_status = claim_status;
    }

    public String getClaim_tat() {
        return claim_tat;
    }

    public void setClaim_tat(String claim_tat) {
        this.claim_tat = claim_tat;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

}
