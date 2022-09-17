package com.palm.newbenefit.Module;

public class comment {

    String comment_arr=null;

    public comment(String comment_arr) {
        this.comment_arr = comment_arr;
    }

    public comment() {
    }

    @Override
    public String toString() {
        return "comment{" +
                "comment_arr='" + comment_arr + '\'' +
                '}';
    }

    public String getComment_arr() {
        return comment_arr;
    }

    public void setComment_arr(String comment_arr) {
        this.comment_arr = comment_arr;
    }
}
