package com.palm.newbenefit.Module;

public class VoluntaryCoverList {

    private String policyName, policyCoverAmount, policyPremiumAmount, policyWalletAmount, policyPayrollAmount;

    public VoluntaryCoverList(String policyName, String policyCoverAmount, String policyPremiumAmount, String policyWalletAmount, String policyPayrollAmount) {
        this.policyName = policyName;
        this.policyCoverAmount = policyCoverAmount;
        this.policyPremiumAmount = policyPremiumAmount;
        this.policyWalletAmount = policyWalletAmount;
        this.policyPayrollAmount = policyPayrollAmount;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyCoverAmount() {
        return policyCoverAmount;
    }

    public void setPolicyCoverAmount(String policyCoverAmount) {
        this.policyCoverAmount = policyCoverAmount;
    }

    public String getPolicyPremiumAmount() {
        return policyPremiumAmount;
    }

    public void setPolicyPremiumAmount(String policyPremiumAmount) {
        this.policyPremiumAmount = policyPremiumAmount;
    }

    public String getPolicyWalletAmount() {
        return policyWalletAmount;
    }

    public void setPolicyWalletAmount(String policyWalletAmount) {
        this.policyWalletAmount = policyWalletAmount;
    }

    public String getPolicyPayrollAmount() {
        return policyPayrollAmount;
    }

    public void setPolicyPayrollAmount(String policyPayrollAmount) {
        this.policyPayrollAmount = policyPayrollAmount;
    }


}
