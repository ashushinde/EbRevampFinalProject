package com.palm.newbenefit.Module;

import java.util.ArrayList;

public class MyInsuranceFlex {

    String  id;
    String  policy_number;
    String  policy_name;
    String  description;
    String  broker_id;
    String  broker;
    String  insurer_id;
    String  insurer;
    String  tpa_id;
    String  tpa;
    String  employer_id;
    String  employer;
    String  policy_type_id;
    String  policy_type;
    String  policy_sub_type_id;

    String  policy_sub_type;
    String  no_of_member;
    String  policy_status;
    String  start_date;
    String  end_date;
    String  enrollement_status;
    String  enrollment_window;
    String  enrollement_type;
    String  enrollement_days;
    String  policy_rater_type_name;

    ArrayList<String>suminsuredlist;
    ArrayList<String>premiumlist;
    ArrayList<String>featurelist;

    @Override
    public String toString() {
        return "MyInsuranceFlex{" +
                "id='" + id + '\'' +
                ", policy_number='" + policy_number + '\'' +
                ", policy_name='" + policy_name + '\'' +
                ", description='" + description + '\'' +
                ", broker_id='" + broker_id + '\'' +
                ", broker='" + broker + '\'' +
                ", insurer_id='" + insurer_id + '\'' +
                ", insurer='" + insurer + '\'' +
                ", tpa_id='" + tpa_id + '\'' +
                ", tpa='" + tpa + '\'' +
                ", employer_id='" + employer_id + '\'' +
                ", employer='" + employer + '\'' +
                ", policy_type_id='" + policy_type_id + '\'' +
                ", policy_type='" + policy_type + '\'' +
                ", policy_sub_type_id='" + policy_sub_type_id + '\'' +
                ", policy_sub_type='" + policy_sub_type + '\'' +
                ", no_of_member='" + no_of_member + '\'' +
                ", policy_status='" + policy_status + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", enrollement_status='" + enrollement_status + '\'' +
                ", enrollment_window='" + enrollment_window + '\'' +
                ", enrollement_type='" + enrollement_type + '\'' +
                ", enrollement_days='" + enrollement_days + '\'' +
                ", policy_rater_type_name='" + policy_rater_type_name + '\'' +
                ", suminsuredlist=" + suminsuredlist +
                ", premiumlist=" + premiumlist +
                ", featurelist=" + featurelist +
                '}';
    }

    public ArrayList<String> getSuminsuredlist() {
        return suminsuredlist;
    }

    public void setSuminsuredlist(ArrayList<String> suminsuredlist) {
        this.suminsuredlist = suminsuredlist;
    }

    public ArrayList<String> getPremiumlist() {
        return premiumlist;
    }

    public void setPremiumlist(ArrayList<String> premiumlist) {
        this.premiumlist = premiumlist;
    }

    public ArrayList<String> getFeaturelist() {
        return featurelist;
    }

    public void setFeaturelist(ArrayList<String> featurelist) {
        this.featurelist = featurelist;
    }

    public MyInsuranceFlex(String id, String policy_number, String policy_name, String description, String broker_id, String broker, String insurer_id, String insurer, String tpa_id, String tpa, String employer_id, String employer, String policy_type_id, String policy_type, String policy_sub_type_id, String policy_sub_type, String no_of_member, String policy_status, String start_date, String end_date, String enrollement_status, String enrollment_window, String enrollement_type, String enrollement_days, String policy_rater_type_name, ArrayList<String> suminsuredlist, ArrayList<String> premiumlist, ArrayList<String> featurelist) {
        this.id = id;
        this.policy_number = policy_number;
        this.policy_name = policy_name;
        this.description = description;
        this.broker_id = broker_id;
        this.broker = broker;
        this.insurer_id = insurer_id;
        this.insurer = insurer;
        this.tpa_id = tpa_id;
        this.tpa = tpa;
        this.employer_id = employer_id;
        this.employer = employer;
        this.policy_type_id = policy_type_id;
        this.policy_type = policy_type;
        this.policy_sub_type_id = policy_sub_type_id;
        this.policy_sub_type = policy_sub_type;
        this.no_of_member = no_of_member;
        this.policy_status = policy_status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.enrollement_status = enrollement_status;
        this.enrollment_window = enrollment_window;
        this.enrollement_type = enrollement_type;
        this.enrollement_days = enrollement_days;
        this.policy_rater_type_name = policy_rater_type_name;
        this.suminsuredlist = suminsuredlist;
        this.premiumlist = premiumlist;
        this.featurelist = featurelist;
    }



    public MyInsuranceFlex() {
    }

    public MyInsuranceFlex(String description) {
        this.description = description;
    }

    public MyInsuranceFlex(String id, String broker_id, String broker) {
        this.id = id;
        this.broker_id = broker_id;
        this.broker = broker;
    }

    public MyInsuranceFlex(String id, String policy_number, String policy_name, String description,
                           String broker_id, String broker, String insurer_id, String insurer,
                           String tpa_id, String tpa, String employer_id, String employer,
                           String policy_type_id, String policy_type, String policy_sub_type_id,
                           String policy_sub_type, String no_of_member, String policy_status,
                           String start_date, String end_date, String enrollement_status,
                           String enrollment_window, String enrollement_type, String enrollement_days,
                           String policy_rater_type_name) {
        this.id = id;
        this.policy_number = policy_number;
        this.policy_name = policy_name;
        this.description = description;
        this.broker_id = broker_id;
        this.broker = broker;
        this.insurer_id = insurer_id;
        this.insurer = insurer;
        this.tpa_id = tpa_id;
        this.tpa = tpa;
        this.employer_id = employer_id;
        this.employer = employer;
        this.policy_type_id = policy_type_id;
        this.policy_type = policy_type;
        this.policy_sub_type_id = policy_sub_type_id;
        this.policy_sub_type = policy_sub_type;
        this.no_of_member = no_of_member;
        this.policy_status = policy_status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.enrollement_status = enrollement_status;
        this.enrollment_window = enrollment_window;
        this.enrollement_type = enrollement_type;
        this.enrollement_days = enrollement_days;
        this.policy_rater_type_name = policy_rater_type_name;
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

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBroker_id() {
        return broker_id;
    }

    public void setBroker_id(String broker_id) {
        this.broker_id = broker_id;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getInsurer_id() {
        return insurer_id;
    }

    public void setInsurer_id(String insurer_id) {
        this.insurer_id = insurer_id;
    }

    public String getInsurer() {
        return insurer;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public String getTpa_id() {
        return tpa_id;
    }

    public void setTpa_id(String tpa_id) {
        this.tpa_id = tpa_id;
    }

    public String getTpa() {
        return tpa;
    }

    public void setTpa(String tpa) {
        this.tpa = tpa;
    }

    public String getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(String employer_id) {
        this.employer_id = employer_id;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getPolicy_type_id() {
        return policy_type_id;
    }

    public void setPolicy_type_id(String policy_type_id) {
        this.policy_type_id = policy_type_id;
    }

    public String getPolicy_type() {
        return policy_type;
    }

    public void setPolicy_type(String policy_type) {
        this.policy_type = policy_type;
    }

    public String getPolicy_sub_type_id() {
        return policy_sub_type_id;
    }

    public void setPolicy_sub_type_id(String policy_sub_type_id) {
        this.policy_sub_type_id = policy_sub_type_id;
    }

    public String getPolicy_sub_type() {
        return policy_sub_type;
    }

    public void setPolicy_sub_type(String policy_sub_type) {
        this.policy_sub_type = policy_sub_type;
    }

    public String getNo_of_member() {
        return no_of_member;
    }

    public void setNo_of_member(String no_of_member) {
        this.no_of_member = no_of_member;
    }

    public String getPolicy_status() {
        return policy_status;
    }

    public void setPolicy_status(String policy_status) {
        this.policy_status = policy_status;
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

    public String getEnrollement_status() {
        return enrollement_status;
    }

    public void setEnrollement_status(String enrollement_status) {
        this.enrollement_status = enrollement_status;
    }

    public String getEnrollment_window() {
        return enrollment_window;
    }

    public void setEnrollment_window(String enrollment_window) {
        this.enrollment_window = enrollment_window;
    }

    public String getEnrollement_type() {
        return enrollement_type;
    }

    public void setEnrollement_type(String enrollement_type) {
        this.enrollement_type = enrollement_type;
    }

    public String getEnrollement_days() {
        return enrollement_days;
    }

    public void setEnrollement_days(String enrollement_days) {
        this.enrollement_days = enrollement_days;
    }

    public String getPolicy_rater_type_name() {
        return policy_rater_type_name;
    }

    public void setPolicy_rater_type_name(String policy_rater_type_name) {
        this.policy_rater_type_name = policy_rater_type_name;
    }
}



