package com.palm.newbenefit.Module;

public class SpinnerModalFamilyData {







    public  String selKey = null;
    public String selValue = null;
    public String family_dob = null;
    public String bank_id = null;

    public String age = null;
    public String family_gender = null;
    public String fam_id = null;

    public String getFam_id() {
        return fam_id;
    }

    public void setFam_id(String fam_id) {
        this.fam_id = fam_id;
    }

    public SpinnerModalFamilyData(String selKey, String selValue, String family_dob, String bank_id, String age, String family_gender, String fam_id) {
        this.selKey = selKey;
        this.selValue = selValue;
        this.family_dob = family_dob;
        this.bank_id = bank_id;
        this.age = age;
        this.family_gender = family_gender;
        this.fam_id = fam_id;
    }

    public String getFamily_dob() {
        return family_dob;
    }

    public void setFamily_dob(String family_dob) {
        this.family_dob = family_dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFamily_gender() {
        return family_gender;
    }

    public void setFamily_gender(String family_gender) {
        this.family_gender = family_gender;
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


    @Override
    public String toString() {
        return this.selKey;
    }

}


