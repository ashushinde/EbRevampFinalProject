package com.palm.newbenefit.Module;

public class FlexBenefitWellness {



    String flexi_benefit_id;
    String amount;
    String flex_balance;
    String flexi_benefit_name;
    String flexi_benefit_description;
    String flexi_benefit_code;
    String flexi_benefit_type;
    String image;
    String allocated_amount;

    public String getFlex_balance() {
        return flex_balance;
    }

    public void setFlex_balance(String flex_balance) {
        this.flex_balance = flex_balance;
    }

    public FlexBenefitWellness() {
    }

    public String getFlexi_benefit_id() {
        return flexi_benefit_id;
    }

    public void setFlexi_benefit_id(String flexi_benefit_id) {
        this.flexi_benefit_id = flexi_benefit_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFlexi_benefit_name() {
        return flexi_benefit_name;
    }

    public void setFlexi_benefit_name(String flexi_benefit_name) {
        this.flexi_benefit_name = flexi_benefit_name;
    }

    public String getFlexi_benefit_description() {
        return flexi_benefit_description;
    }

    public void setFlexi_benefit_description(String flexi_benefit_description) {
        this.flexi_benefit_description = flexi_benefit_description;
    }

    public String getFlexi_benefit_code() {
        return flexi_benefit_code;
    }

    public void setFlexi_benefit_code(String flexi_benefit_code) {
        this.flexi_benefit_code = flexi_benefit_code;
    }

    public String getFlexi_benefit_type() {
        return flexi_benefit_type;
    }

    public void setFlexi_benefit_type(String flexi_benefit_type) {
        this.flexi_benefit_type = flexi_benefit_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAllocated_amount() {
        return allocated_amount;
    }

    public void setAllocated_amount(String allocated_amount) {
        this.allocated_amount = allocated_amount;
    }

    public FlexBenefitWellness(String flexi_benefit_id, String amount, String flex_balance, String flexi_benefit_name, String flexi_benefit_description, String flexi_benefit_code, String flexi_benefit_type, String image, String allocated_amount) {
        this.flexi_benefit_id = flexi_benefit_id;
        this.amount = amount;
        this.flex_balance = flex_balance;
        this.flexi_benefit_name = flexi_benefit_name;
        this.flexi_benefit_description = flexi_benefit_description;
        this.flexi_benefit_code = flexi_benefit_code;
        this.flexi_benefit_type = flexi_benefit_type;
        this.image = image;
        this.allocated_amount = allocated_amount;
    }

    @Override
    public String toString() {
        return "FlexBenefitWellness{" +
                "flexi_benefit_id='" + flexi_benefit_id + '\'' +
                ", amount='" + amount + '\'' +
                ", flex_balance='" + flex_balance + '\'' +
                ", flexi_benefit_name='" + flexi_benefit_name + '\'' +
                ", flexi_benefit_description='" + flexi_benefit_description + '\'' +
                ", flexi_benefit_code='" + flexi_benefit_code + '\'' +
                ", flexi_benefit_type='" + flexi_benefit_type + '\'' +
                ", image='" + image + '\'' +
                ", allocated_amount='" + allocated_amount + '\'' +
                '}';
    }
}
