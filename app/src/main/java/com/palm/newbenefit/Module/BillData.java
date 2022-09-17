package com.palm.newbenefit.Module;

public class BillData {

    String bill_no = null;

    public BillData(String bill_no) {
        this.bill_no = bill_no;
    }

    public BillData() {
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    @Override
    public String toString() {
        return "BillData{" +
                "bill_no='" + bill_no + '\'' +
                '}';
    }
}
