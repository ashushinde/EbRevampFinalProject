package com.palm.newbenefit.Module;

public class Feature {

    String id;
    String policy_id;
    String policy_name;
    String suminsured;
    String no_of_times_of_salary;
    String title;
    String content;
    String image;

    public Feature() {
    }

    public Feature(String id, String policy_id, String policy_name, String suminsured, String no_of_times_of_salary, String title, String content, String image) {
        this.id = id;
        this.policy_id = policy_id;
        this.policy_name = policy_name;
        this.suminsured = suminsured;
        this.no_of_times_of_salary = no_of_times_of_salary;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(String policy_id) {
        this.policy_id = policy_id;
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public String getSuminsured() {
        return suminsured;
    }

    public void setSuminsured(String suminsured) {
        this.suminsured = suminsured;
    }

    public String getNo_of_times_of_salary() {
        return no_of_times_of_salary;
    }

    public void setNo_of_times_of_salary(String no_of_times_of_salary) {
        this.no_of_times_of_salary = no_of_times_of_salary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "id='" + id + '\'' +
                ", policy_id='" + policy_id + '\'' +
                ", policy_name='" + policy_name + '\'' +
                ", suminsured='" + suminsured + '\'' +
                ", no_of_times_of_salary='" + no_of_times_of_salary + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
