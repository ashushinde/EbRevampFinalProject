package com.palm.newbenefit.Module;

public class amount {

    String amt = null;

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    @Override
    public String toString() {
        return "amount{" +
                "amt='" + amt + '\'' +
                '}';
    }

    public amount(String amt) {
        this.amt = amt;
    }
}
