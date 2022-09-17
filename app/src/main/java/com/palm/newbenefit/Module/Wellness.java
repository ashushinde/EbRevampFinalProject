package com.palm.newbenefit.Module;

public class Wellness {

    String id;
    String benefit_id;
    String benefit_name;
    String benefit_content;
    String benefit_image;
    String wellness_partner_id;
    String wellness_partner_name;
    String wellness_partner_url;
    String wellness_partner_logo;

    public Wellness(String id, String benefit_id, String benefit_name, String benefit_content, String benefit_image, String wellness_partner_id, String wellness_partner_name, String wellness_partner_url, String wellness_partner_logo) {
        this.id = id;
        this.benefit_id = benefit_id;
        this.benefit_name = benefit_name;
        this.benefit_content = benefit_content;
        this.benefit_image = benefit_image;
        this.wellness_partner_id = wellness_partner_id;
        this.wellness_partner_name = wellness_partner_name;
        this.wellness_partner_url = wellness_partner_url;
        this.wellness_partner_logo = wellness_partner_logo;
    }

    @Override
    public String toString() {
        return "Wellness{" +
                "id='" + id + '\'' +
                ", benefit_id='" + benefit_id + '\'' +
                ", benefit_name='" + benefit_name + '\'' +
                ", benefit_content='" + benefit_content + '\'' +
                ", benefit_image='" + benefit_image + '\'' +
                ", wellness_partner_id='" + wellness_partner_id + '\'' +
                ", wellness_partner_name='" + wellness_partner_name + '\'' +
                ", wellness_partner_url='" + wellness_partner_url + '\'' +
                ", wellness_partner_logo='" + wellness_partner_logo + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBenefit_id() {
        return benefit_id;
    }

    public void setBenefit_id(String benefit_id) {
        this.benefit_id = benefit_id;
    }

    public String getBenefit_name() {
        return benefit_name;
    }

    public void setBenefit_name(String benefit_name) {
        this.benefit_name = benefit_name;
    }

    public String getBenefit_content() {
        return benefit_content;
    }

    public void setBenefit_content(String benefit_content) {
        this.benefit_content = benefit_content;
    }

    public String getBenefit_image() {
        return benefit_image;
    }

    public void setBenefit_image(String benefit_image) {
        this.benefit_image = benefit_image;
    }

    public String getWellness_partner_id() {
        return wellness_partner_id;
    }

    public void setWellness_partner_id(String wellness_partner_id) {
        this.wellness_partner_id = wellness_partner_id;
    }

    public String getWellness_partner_name() {
        return wellness_partner_name;
    }

    public void setWellness_partner_name(String wellness_partner_name) {
        this.wellness_partner_name = wellness_partner_name;
    }

    public String getWellness_partner_url() {
        return wellness_partner_url;
    }

    public void setWellness_partner_url(String wellness_partner_url) {
        this.wellness_partner_url = wellness_partner_url;
    }

    public String getWellness_partner_logo() {
        return wellness_partner_logo;
    }

    public void setWellness_partner_logo(String wellness_partner_logo) {
        this.wellness_partner_logo = wellness_partner_logo;
    }
}
