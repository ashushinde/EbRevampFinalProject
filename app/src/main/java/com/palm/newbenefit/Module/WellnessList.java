package com.palm.newbenefit.Module;

public class WellnessList {

    private String coverName,flexAmount,payrollAmount,finalAmount;

    public WellnessList(String coverName, String flexAmount, String payrollAmount, String finalAmount) {
        this.coverName = coverName;
        this.flexAmount = flexAmount;
        this.payrollAmount = payrollAmount;
        this.finalAmount = finalAmount;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    public String getFlexAmount() {
        return flexAmount;
    }

    public void setFlexAmount(String flexAmount) {
        this.flexAmount = flexAmount;
    }

    public String getPayrollAmount() {
        return payrollAmount;
    }

    public void setPayrollAmount(String payrollAmount) {
        this.payrollAmount = payrollAmount;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }
}
