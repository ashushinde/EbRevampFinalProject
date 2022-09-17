package com.palm.newbenefit.Module;

public class ContactUs {

    String id;
    String name;
    String email_1;
    String email_2;
    String email_3;
    String country;
    String state_id;
    String state;
    String city_id;
    String city;
    String pincode;

    String address_line_1;
    String address_line_2;
    String address_line_3;
    String contact_1;
    String contact_2;
    String contact_3;

    public ContactUs(String id, String name, String email_1, String email_2, String email_3, String country, String state_id, String state, String city_id, String city, String pincode, String address_line_1, String address_line_2, String address_line_3, String contact_1, String contact_2, String contact_3) {
        this.id = id;
        this.name = name;
        this.email_1 = email_1;
        this.email_2 = email_2;
        this.email_3 = email_3;
        this.country = country;
        this.state_id = state_id;
        this.state = state;
        this.city_id = city_id;
        this.city = city;
        this.pincode = pincode;
        this.address_line_1 = address_line_1;
        this.address_line_2 = address_line_2;
        this.address_line_3 = address_line_3;
        this.contact_1 = contact_1;
        this.contact_2 = contact_2;
        this.contact_3 = contact_3;
    }

    @Override
    public String toString() {
        return "ContactUs{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email_1='" + email_1 + '\'' +
                ", email_2='" + email_2 + '\'' +
                ", email_3='" + email_3 + '\'' +
                ", country='" + country + '\'' +
                ", state_id='" + state_id + '\'' +
                ", state='" + state + '\'' +
                ", city_id='" + city_id + '\'' +
                ", city='" + city + '\'' +
                ", pincode='" + pincode + '\'' +
                ", address_line_1='" + address_line_1 + '\'' +
                ", address_line_2='" + address_line_2 + '\'' +
                ", address_line_3='" + address_line_3 + '\'' +
                ", contact_1='" + contact_1 + '\'' +
                ", contact_2='" + contact_2 + '\'' +
                ", contact_3='" + contact_3 + '\'' +
                '}';
    }

    public ContactUs() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_1() {
        return email_1;
    }

    public void setEmail_1(String email_1) {
        this.email_1 = email_1;
    }

    public String getEmail_2() {
        return email_2;
    }

    public void setEmail_2(String email_2) {
        this.email_2 = email_2;
    }

    public String getEmail_3() {
        return email_3;
    }

    public void setEmail_3(String email_3) {
        this.email_3 = email_3;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress_line_1() {
        return address_line_1;
    }

    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public String getAddress_line_2() {
        return address_line_2;
    }

    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    public String getAddress_line_3() {
        return address_line_3;
    }

    public void setAddress_line_3(String address_line_3) {
        this.address_line_3 = address_line_3;
    }

    public String getContact_1() {
        return contact_1;
    }

    public void setContact_1(String contact_1) {
        this.contact_1 = contact_1;
    }

    public String getContact_2() {
        return contact_2;
    }

    public void setContact_2(String contact_2) {
        this.contact_2 = contact_2;
    }

    public String getContact_3() {
        return contact_3;
    }

    public void setContact_3(String contact_3) {
        this.contact_3 = contact_3;
    }
}
