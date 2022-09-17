package com.palm.newbenefit.Module;

public class Cost {

    String cost_arr = null;

    @Override
    public String toString() {
        return "Cost{" +
                "cost_arr='" + cost_arr + '\'' +
                '}';
    }

    public String getCost_arr() {
        return cost_arr;
    }

    public void setCost_arr(String cost_arr) {
        this.cost_arr = cost_arr;
    }

    public Cost() {
    }

    public Cost(String cost_arr) {
        this.cost_arr = cost_arr;
    }
}
