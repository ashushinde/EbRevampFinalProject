package com.palm.newbenefit.Module;

public class Claim_amount {

    String claim_amount = null;

    public Claim_amount() {
    }

    public Claim_amount(String claim_amount) {
        this.claim_amount = claim_amount;
    }

    public String getClaim_amount() {
        return claim_amount;
    }

    public void setClaim_amount(String claim_amount) {
        this.claim_amount = claim_amount;
    }

    @Override
    public String toString() {
        return "Claim_amount{" +
                "claim_amount='" + claim_amount + '\'' +
                '}';
    }
}
