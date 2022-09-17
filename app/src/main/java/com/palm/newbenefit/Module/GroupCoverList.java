package com.palm.newbenefit.Module;

public class GroupCoverList {

    private String coverName;
    private int coverAmount;
    private String finalAmount;
    private String policyId;

    public GroupCoverList(String coverName, String finalAmount, String policyId) {
        this.coverName = coverName;
        this.finalAmount = finalAmount;
        this.policyId = policyId;
    }

    public GroupCoverList(String coverName) {
        this.coverName = coverName;
    }

    public GroupCoverList(int coverAmount) {
        this.coverAmount = coverAmount;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    public int getCoverAmount() {
        return coverAmount;
    }

    public void setCoverAmount(int coverAmount) {
        this.coverAmount = coverAmount;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

}
