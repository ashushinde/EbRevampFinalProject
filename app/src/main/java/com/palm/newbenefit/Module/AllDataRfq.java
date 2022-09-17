package com.palm.newbenefit.Module;

public class AllDataRfq {

    public String min_age = null;
    public String age_group = null;
    public String max_age = null;
    public String suminsured = null;
    public String total_amount = null;

    public AllDataRfq() {
    }

    public AllDataRfq(String min_age, String age_group, String max_age, String suminsured, String total_amount) {
        this.min_age = min_age;
        this.age_group = age_group;
        this.max_age = max_age;
        this.suminsured = suminsured;
        this.total_amount = total_amount;
    }



    public String getMin_age() {
        return min_age;
    }

    public void setMin_age(String min_age) {
        this.min_age = min_age;
    }

    public String getAge_group() {
        return age_group;
    }

    public void setAge_group(String age_group) {
        this.age_group = age_group;
    }

    public String getMax_age() {
        return max_age;
    }

    public void setMax_age(String max_age) {
        this.max_age = max_age;
    }

    public String getSuminsured() {
        return suminsured;
    }

    public void setSuminsured(String suminsured) {
        this.suminsured = suminsured;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
}



