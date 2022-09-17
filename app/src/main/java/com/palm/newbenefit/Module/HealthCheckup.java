package com.palm.newbenefit.Module;

import java.util.ArrayList;

public class HealthCheckup {

    String policy_number;
    String employee_code;
    String employee_name;
    String relation_with_employee;
    String member_name;
    String appointment_request_date_time;
    String alternate_appointment_request_date_time;
    String appointment_status;
    String appointment_date_time;
    String checkup_type;
    String status_updated_by;
    String employer_name;
    String employee_member_mapping_id;
    String contact;
    String email;
    String address_line_1;
    String address_line_2;
    String city_id;

    String state_id;
    String pincode;
    String health_check_up_report;

    String is_checkup_done;
    String id;
    ArrayList documents;

    public HealthCheckup() {
    }

    @Override
    public String toString() {
        return "HealthCheckup{" +
                "policy_number='" + policy_number + '\'' +
                ", employee_code='" + employee_code + '\'' +
                ", employee_name='" + employee_name + '\'' +
                ", relation_with_employee='" + relation_with_employee + '\'' +
                ", member_name='" + member_name + '\'' +
                ", appointment_request_date_time='" + appointment_request_date_time + '\'' +
                ", alternate_appointment_request_date_time='" + alternate_appointment_request_date_time + '\'' +
                ", appointment_status='" + appointment_status + '\'' +
                ", appointment_date_time='" + appointment_date_time + '\'' +
                ", checkup_type='" + checkup_type + '\'' +
                ", status_updated_by='" + status_updated_by + '\'' +
                ", employer_name='" + employer_name + '\'' +
                ", employee_member_mapping_id='" + employee_member_mapping_id + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", address_line_1='" + address_line_1 + '\'' +
                ", address_line_2='" + address_line_2 + '\'' +
                ", city_id='" + city_id + '\'' +
                ", state_id='" + state_id + '\'' +
                ", pincode='" + pincode + '\'' +
                ", health_check_up_report='" + health_check_up_report + '\'' +
                ", is_checkup_done='" + is_checkup_done + '\'' +
                ", id='" + id + '\'' +
                ", documents=" + documents +
                '}';
    }

    public HealthCheckup(String policy_number, String employee_code) {
        this.policy_number = policy_number;
        this.employee_code = employee_code;
    }

    public HealthCheckup(String policy_number, String employee_code, String employee_name, String relation_with_employee, String member_name, String appointment_request_date_time, String alternate_appointment_request_date_time, String appointment_status, String appointment_date_time, String checkup_type, String status_updated_by, String employer_name, String employee_member_mapping_id, String contact, String email, String address_line_1, String address_line_2, String city_id, String state_id, String pincode, String health_check_up_report, String is_checkup_done, String id, ArrayList documents) {
        this.policy_number = policy_number;
        this.employee_code = employee_code;
        this.employee_name = employee_name;
        this.relation_with_employee = relation_with_employee;
        this.member_name = member_name;
        this.appointment_request_date_time = appointment_request_date_time;
        this.alternate_appointment_request_date_time = alternate_appointment_request_date_time;
        this.appointment_status = appointment_status;
        this.appointment_date_time = appointment_date_time;
        this.checkup_type = checkup_type;
        this.status_updated_by = status_updated_by;
        this.employer_name = employer_name;
        this.employee_member_mapping_id = employee_member_mapping_id;
        this.contact = contact;
        this.email = email;
        this.address_line_1 = address_line_1;
        this.address_line_2 = address_line_2;
        this.city_id = city_id;
        this.state_id = state_id;
        this.pincode = pincode;
        this.health_check_up_report = health_check_up_report;
        this.is_checkup_done = is_checkup_done;
        this.id = id;
        this.documents = documents;
    }


    public HealthCheckup(String policy_number, String employee_code, String employee_name, String relation_with_employee, String member_name, String appointment_request_date_time, String alternate_appointment_request_date_time, String appointment_status, String appointment_date_time, String checkup_type, String status_updated_by, String employer_name, String employee_member_mapping_id, String contact, String email, String address_line_1, String address_line_2, String city_id, String state_id, String pincode, String health_check_up_report, String is_checkup_done, String id) {
        this.policy_number = policy_number;
        this.employee_code = employee_code;
        this.employee_name = employee_name;
        this.relation_with_employee = relation_with_employee;
        this.member_name = member_name;
        this.appointment_request_date_time = appointment_request_date_time;
        this.alternate_appointment_request_date_time = alternate_appointment_request_date_time;
        this.appointment_status = appointment_status;
        this.appointment_date_time = appointment_date_time;
        this.checkup_type = checkup_type;
        this.status_updated_by = status_updated_by;
        this.employer_name = employer_name;
        this.employee_member_mapping_id = employee_member_mapping_id;
        this.contact = contact;
        this.email = email;
        this.address_line_1 = address_line_1;
        this.address_line_2 = address_line_2;
        this.city_id = city_id;
        this.state_id = state_id;
        this.pincode = pincode;
        this.health_check_up_report = health_check_up_report;
        this.is_checkup_done = is_checkup_done;
        this.id = id;
    }

    public String getPolicy_number() {
        return policy_number;
    }

    public void setPolicy_number(String policy_number) {
        this.policy_number = policy_number;
    }

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getRelation_with_employee() {
        return relation_with_employee;
    }

    public void setRelation_with_employee(String relation_with_employee) {
        this.relation_with_employee = relation_with_employee;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getAppointment_request_date_time() {
        return appointment_request_date_time;
    }

    public void setAppointment_request_date_time(String appointment_request_date_time) {
        this.appointment_request_date_time = appointment_request_date_time;
    }

    public String getAlternate_appointment_request_date_time() {
        return alternate_appointment_request_date_time;
    }

    public void setAlternate_appointment_request_date_time(String alternate_appointment_request_date_time) {
        this.alternate_appointment_request_date_time = alternate_appointment_request_date_time;
    }

    public String getAppointment_status() {
        return appointment_status;
    }

    public void setAppointment_status(String appointment_status) {
        this.appointment_status = appointment_status;
    }

    public String getAppointment_date_time() {
        return appointment_date_time;
    }

    public void setAppointment_date_time(String appointment_date_time) {
        this.appointment_date_time = appointment_date_time;
    }

    public String getCheckup_type() {
        return checkup_type;
    }

    public void setCheckup_type(String checkup_type) {
        this.checkup_type = checkup_type;
    }

    public String getStatus_updated_by() {
        return status_updated_by;
    }

    public void setStatus_updated_by(String status_updated_by) {
        this.status_updated_by = status_updated_by;
    }

    public String getEmployer_name() {
        return employer_name;
    }

    public void setEmployer_name(String employer_name) {
        this.employer_name = employer_name;
    }

    public String getEmployee_member_mapping_id() {
        return employee_member_mapping_id;
    }

    public void setEmployee_member_mapping_id(String employee_member_mapping_id) {
        this.employee_member_mapping_id = employee_member_mapping_id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHealth_check_up_report() {
        return health_check_up_report;
    }

    public void setHealth_check_up_report(String health_check_up_report) {
        this.health_check_up_report = health_check_up_report;
    }

    public String getIs_checkup_done() {
        return is_checkup_done;
    }

    public void setIs_checkup_done(String is_checkup_done) {
        this.is_checkup_done = is_checkup_done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList documents) {
        this.documents = documents;
    }
}
