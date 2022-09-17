package com.palm.newbenefit.Module;

public class Relations {

    String Relation;
    String min_age;

    String max_age;

    String employee;

    public Relations(String min_age, String max_age, String employee) {
        this.min_age = min_age;
        this.max_age = max_age;
        this.employee = employee;
    }

    public String getMin_age() {
        return min_age;
    }

    public void setMin_age(String min_age) {
        this.min_age = min_age;
    }

    public String getMax_age() {
        return max_age;
    }

    public void setMax_age(String max_age) {
        this.max_age = max_age;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }

    public Relations(String relation) {
        Relation = relation;
    }

    @Override
    public String toString() {
        return "Relations{" +
                "Relation='" + Relation + '\'' +
                ", min_age='" + min_age + '\'' +
                ", max_age='" + max_age + '\'' +
                ", employee='" + employee + '\'' +
                '}';
    }

    public Relations() {
    }
}
