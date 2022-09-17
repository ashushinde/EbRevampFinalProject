package com.palm.newbenefit.Module;

public class BillAllData {

   String bill_number=null;
   String bill_amount=null;
   String Bill_Date=null;
   String Type=null;
   String Comment=null;

    public BillAllData() {
    }

    public BillAllData(String bill_number, String bill_amount, String bill_Date, String type, String comment) {
        this.bill_number = bill_number;
        this.bill_amount = bill_amount;
        Bill_Date = bill_Date;
        Type = type;
        Comment = comment;
    }

    @Override
    public String toString() {
        return "BillAllData{" +
                "bill_number='" + bill_number + '\'' +
                ", bill_amount='" + bill_amount + '\'' +
                ", Bill_Date='" + Bill_Date + '\'' +
                ", Type='" + Type + '\'' +
                ", Comment='" + Comment + '\'' +
                '}';
    }

    public String getBill_number() {
        return bill_number;
    }

    public void setBill_number(String bill_number) {
        this.bill_number = bill_number;
    }

    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getBill_Date() {
        return Bill_Date;
    }

    public void setBill_Date(String bill_Date) {
        Bill_Date = bill_Date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
