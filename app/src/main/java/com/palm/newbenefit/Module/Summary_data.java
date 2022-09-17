package com.palm.newbenefit.Module;

public class Summary_data {

    String summary;

    @Override
    public String toString() {
        return "Summary_data{" +
                "summary='" + summary + '\'' +
                '}';
    }

    public Summary_data(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
