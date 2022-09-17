package com.palm.newbenefit.Module;

public class Feedback {
    String customername;
    String feedback;
    String rating;

    public Feedback(String customername, String feedback, String rating) {
        this.customername = customername;
        this.feedback = feedback;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "customername='" + customername + '\'' +
                ", feedback='" + feedback + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }

    public Feedback() {
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
