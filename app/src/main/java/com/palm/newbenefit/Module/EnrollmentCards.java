package com.palm.newbenefit.Module;

public class EnrollmentCards {


    String id;
    String policy_number;
    String policy_sub_type_id;
    String name;
    String enrollement_status;
    String enrollement_confirmed;
    String enrollement_start_date;
    String enrollement_end_date;
    String policy_enrollment_window;

    public EnrollmentCards(String id, String policy_number, String policy_sub_type_id, String name, String enrollement_status, String enrollement_confirmed, String enrollement_start_date, String enrollement_end_date) {
        this.id = id;
        this.policy_number = policy_number;
        this.policy_sub_type_id = policy_sub_type_id;
        this.name = name;
        this.enrollement_status = enrollement_status;
        this.enrollement_confirmed = enrollement_confirmed;
        this.enrollement_start_date = enrollement_start_date;
        this.enrollement_end_date = enrollement_end_date;
    }

    public EnrollmentCards(String id, String policy_number, String policy_sub_type_id, String name, String enrollement_status, String enrollement_confirmed, String enrollement_start_date, String enrollement_end_date, String policy_enrollment_window) {
        this.id = id;
        this.policy_number = policy_number;
        this.policy_sub_type_id = policy_sub_type_id;
        this.name = name;
        this.enrollement_status = enrollement_status;
        this.enrollement_confirmed = enrollement_confirmed;
        this.enrollement_start_date = enrollement_start_date;
        this.enrollement_end_date = enrollement_end_date;
        this.policy_enrollment_window = policy_enrollment_window;
    }

    public EnrollmentCards() {
    }

    @Override
    public String toString() {
        return "EnrollmentCards{" +
                "id='" + id + '\'' +
                ", policy_number='" + policy_number + '\'' +
                ", policy_sub_type_id='" + policy_sub_type_id + '\'' +
                ", name='" + name + '\'' +
                ", enrollement_status='" + enrollement_status + '\'' +
                ", enrollement_confirmed='" + enrollement_confirmed + '\'' +
                ", enrollement_start_date='" + enrollement_start_date + '\'' +
                ", enrollement_end_date='" + enrollement_end_date + '\'' +
                ", policy_enrollment_window='" + policy_enrollment_window + '\'' +
                '}';
    }

    public String getPolicy_enrollment_window() {
        return policy_enrollment_window;
    }

    public void setPolicy_enrollment_window(String policy_enrollment_window) {
        this.policy_enrollment_window = policy_enrollment_window;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPolicy_number() {
        return policy_number;
    }

    public void setPolicy_number(String policy_number) {
        this.policy_number = policy_number;
    }

    public String getPolicy_sub_type_id() {
        return policy_sub_type_id;
    }

    public void setPolicy_sub_type_id(String policy_sub_type_id) {
        this.policy_sub_type_id = policy_sub_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnrollement_status() {
        return enrollement_status;
    }

    public void setEnrollement_status(String enrollement_status) {
        this.enrollement_status = enrollement_status;
    }

    public String getEnrollement_confirmed() {
        return enrollement_confirmed;
    }

    public void setEnrollement_confirmed(String enrollement_confirmed) {
        this.enrollement_confirmed = enrollement_confirmed;
    }

    public String getEnrollement_start_date() {
        return enrollement_start_date;
    }

    public void setEnrollement_start_date(String enrollement_start_date) {
        this.enrollement_start_date = enrollement_start_date;
    }

    public String getEnrollement_end_date() {
        return enrollement_end_date;
    }

    public void setEnrollement_end_date(String enrollement_end_date) {
        this.enrollement_end_date = enrollement_end_date;
    }
}
