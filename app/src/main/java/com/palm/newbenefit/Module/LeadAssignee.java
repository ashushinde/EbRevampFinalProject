package com.palm.newbenefit.Module;

public class LeadAssignee {


    String id;
    String rfq_lead_id;
    String role_type_id;
    String role_type_name;
    String ic_role_id;
    String ic_role;
    String broker_role_id;
    String broker_role;
    String user_id;
    String user_name;
    String start_date;
    String end_date;
    String statusa;


    public LeadAssignee(String id, String rfq_lead_id, String role_type_id, String role_type_name, String ic_role_id, String ic_role, String broker_role_id, String broker_role, String user_id, String user_name, String start_date, String end_date, String statusa) {
        this.id = id;
        this.rfq_lead_id = rfq_lead_id;
        this.role_type_id = role_type_id;
        this.role_type_name = role_type_name;
        this.ic_role_id = ic_role_id;
        this.ic_role = ic_role;
        this.broker_role_id = broker_role_id;
        this.broker_role = broker_role;
        this.user_id = user_id;
        this.user_name = user_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.statusa = statusa;
    }

    @Override
    public String toString() {
        return "LeadAssignee{" +
                "id='" + id + '\'' +
                ", rfq_lead_id='" + rfq_lead_id + '\'' +
                ", role_type_id='" + role_type_id + '\'' +
                ", role_type_name='" + role_type_name + '\'' +
                ", ic_role_id='" + ic_role_id + '\'' +
                ", ic_role='" + ic_role + '\'' +
                ", broker_role_id='" + broker_role_id + '\'' +
                ", broker_role='" + broker_role + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", statusa='" + statusa + '\'' +
                '}';
    }

    public LeadAssignee() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRfq_lead_id() {
        return rfq_lead_id;
    }

    public void setRfq_lead_id(String rfq_lead_id) {
        this.rfq_lead_id = rfq_lead_id;
    }

    public String getRole_type_id() {
        return role_type_id;
    }

    public void setRole_type_id(String role_type_id) {
        this.role_type_id = role_type_id;
    }

    public String getRole_type_name() {
        return role_type_name;
    }

    public void setRole_type_name(String role_type_name) {
        this.role_type_name = role_type_name;
    }

    public String getIc_role_id() {
        return ic_role_id;
    }

    public void setIc_role_id(String ic_role_id) {
        this.ic_role_id = ic_role_id;
    }

    public String getIc_role() {
        return ic_role;
    }

    public void setIc_role(String ic_role) {
        this.ic_role = ic_role;
    }

    public String getBroker_role_id() {
        return broker_role_id;
    }

    public void setBroker_role_id(String broker_role_id) {
        this.broker_role_id = broker_role_id;
    }

    public String getBroker_role() {
        return broker_role;
    }

    public void setBroker_role(String broker_role) {
        this.broker_role = broker_role;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatusa() {
        return statusa;
    }

    public void setStatusa(String statusa) {
        this.statusa = statusa;
    }
}
