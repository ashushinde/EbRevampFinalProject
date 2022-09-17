package com.palm.newbenefit.Module;

public class FlexBenefit {



    String name;
    String flex_name;
    String deduction_type;
    String final_amount;

    String amount;

    public FlexBenefit() {
    }

    public FlexBenefit(String name, String flex_name, String deduction_type, String final_amount) {
        this.name = name;
        this.flex_name = flex_name;
        this.deduction_type = deduction_type;
        this.final_amount = final_amount;
    }

    @Override
    public String toString() {
        return "FlexBenefit{" +
                "name='" + name + '\'' +
                ", flex_name='" + flex_name + '\'' +
                ", deduction_type='" + deduction_type + '\'' +
                ", final_amount='" + final_amount + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public FlexBenefit(String name, String flex_name, String deduction_type, String final_amount, String amount) {
        this.name = name;
        this.flex_name = flex_name;
        this.deduction_type = deduction_type;
        this.final_amount = final_amount;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlex_name() {
        return flex_name;
    }

    public void setFlex_name(String flex_name) {
        this.flex_name = flex_name;
    }

    public String getDeduction_type() {
        return deduction_type;
    }

    public void setDeduction_type(String deduction_type) {
        this.deduction_type = deduction_type;
    }

    public String getFinal_amount() {
        return final_amount;
    }

    public void setFinal_amount(String final_amount) {
        this.final_amount = final_amount;
    }

}
