package com.palm.newbenefit.Module;

public class GroupCoverMemberList {

    public GroupCoverMemberList() {
    }

    private String name,gender,relation,dob,policyNo;

    public GroupCoverMemberList(String name, String gender, String relation, String dob, String policyNo) {
        this.name = name;
        this.gender = gender;
        this.relation = relation;
        this.dob = dob;
        this.policyNo = policyNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }
}
