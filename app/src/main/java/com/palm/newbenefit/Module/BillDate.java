package com.palm.newbenefit.Module;

public class BillDate {


    String bill_date = null;

    public String getBill_date() {
        return bill_date;
    }

    public BillDate() {
    }

    @Override
    public String toString() {
        return "BillDate{" +
                "bill_date='" + bill_date + '\'' +
                '}';
    }

    public BillDate(String bill_date) {
        this.bill_date = bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }
}
