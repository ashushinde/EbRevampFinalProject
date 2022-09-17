package com.palm.newbenefit.Module;

public class SpinnerModal {

    String selKey = null;
    public String selValue = null;
    public String bank_id = null;

    public String minage = null;
    public String minvalue = null;

    public String employename = null;
    public String employee_id = null;

    public SpinnerModal(String selKey, String selValue, String bank_id, String minage, String minvalue, String employename, String employee_id, String data) {
        this.selKey = selKey;
        this.selValue = selValue;
        this.bank_id = bank_id;
        this.minage = minage;
        this.minvalue = minvalue;
        this.employename = employename;
        this.employee_id = employee_id;
        this.data = data;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public SpinnerModal(String selKey, String selValue, String bank_id, String minage, String minvalue, String employename, String data) {
        this.selKey = selKey;
        this.selValue = selValue;
        this.bank_id = bank_id;
        this.minage = minage;
        this.minvalue = minvalue;
        this.employename = employename;
        this.data = data;
    }

    public String getEmployename() {
        return employename;
    }

    public void setEmployename(String employename) {
        this.employename = employename;
    }

    public SpinnerModal(String selKey, String selValue) {
        this.selKey = selKey;
        this.selValue = selValue;
    }

    public SpinnerModal(String selKey, String selValue, String bank_id, String minage, String minvalue, String data) {
        this.selKey = selKey;
        this.selValue = selValue;
        this.bank_id = bank_id;
        this.minage = minage;
        this.minvalue = minvalue;
        this.data = data;
    }

    public SpinnerModal(String selKey, String selValue, String minage, String minvalue) {
        this.selKey = selKey;
        this.selValue = selValue;
        this.minage = minage;
        this.minvalue = minvalue;
    }

    public SpinnerModal(String selKey) {
        this.selKey = selKey;

    }

    public SpinnerModal(String selKey, String selValue, String bank_id) {
        this.selKey = selKey;
        this.selValue = selValue;
        this.bank_id = bank_id;
    }
    public void setSelKey(String selKey) {
        this.selKey = selKey;
    }

    public void setSelValue(String selValue) {
        this.selValue = selValue;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }


    public String getSelKey() {
        return selKey;
    }

    public String getSelValue() {
        return selValue;
    }

    public String getMinage() {
        return minage;
    }

    public void setMinage(String minage) {
        this.minage = minage;
    }

    public String getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(String minvalue) {
        this.minvalue = minvalue;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return this.selValue;
    }
    String data="data not found" +
            "data not available";
}


