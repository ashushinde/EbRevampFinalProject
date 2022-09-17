package com.palm.newbenefit.Module;

public class PolicyData {
    private String pol_id;
    private String policy_type_id;
    private String ins_co_name;
    private String pol_mem_insured;
    private String pol_sum_premmium;
    private String pol_sub_type_name;
    private String pol_sub_type_img_path;
    private String insure_com_img_path;
    private String pol_sub_type_id;
    private String mem_count;
    private String members;
    private String coverName;

    public PolicyData(String pol_id, String policy_type_id, String ins_co_name, String pol_mem_insured, String pol_sum_premmium, String pol_sub_type_name, String pol_sub_type_img_path, String insure_com_img_path, String pol_sub_type_id, String mem_count, String members, String coverName) {
        this.pol_id = pol_id;
        this.policy_type_id = policy_type_id;
        this.ins_co_name = ins_co_name;
        this.pol_mem_insured = pol_mem_insured;
        this.pol_sum_premmium = pol_sum_premmium;
        this.pol_sub_type_name = pol_sub_type_name;
        this.pol_sub_type_img_path = pol_sub_type_img_path;
        this.insure_com_img_path = insure_com_img_path;
        this.pol_sub_type_id = pol_sub_type_id;
        this.mem_count = mem_count;
        this.members = members;
        this.coverName = coverName;
    }

    public PolicyData(String pol_id, String policy_type_id, String ins_co_name, String pol_mem_insured, String pol_sum_premmium, String pol_sub_type_name, String pol_sub_type_img_path, String insure_com_img_path, String pol_sub_type_id, String mem_count, String members) {
        this.pol_id = pol_id;
        this.policy_type_id = policy_type_id;
        this.ins_co_name = ins_co_name;
        this.pol_mem_insured = pol_mem_insured;
        this.pol_sum_premmium = pol_sum_premmium;
        this.pol_sub_type_name = pol_sub_type_name;
        this.pol_sub_type_img_path = pol_sub_type_img_path;
        this.insure_com_img_path = insure_com_img_path;
        this.pol_sub_type_id = pol_sub_type_id;
        this.mem_count = mem_count;
        this.members = members;
    }

    public String getPol_id() {
        return pol_id;
    }

    public void setPol_id(String pol_id) {
        this.pol_id = pol_id;
    }

    public String getPolicy_type_id() {
        return policy_type_id;
    }

    public void setPolicy_type_id(String policy_type_id) {
        this.policy_type_id = policy_type_id;
    }

    public String getIns_co_name() {
        return ins_co_name;
    }

    public void setIns_co_name(String ins_co_name) {
        this.ins_co_name = ins_co_name;
    }

    public String getPol_mem_insured() {
        return pol_mem_insured;
    }

    public void setPol_mem_insured(String pol_mem_insured) {
        this.pol_mem_insured = pol_mem_insured;
    }

    public String getPol_sum_premmium() {
        return pol_sum_premmium;
    }

    public void setPol_sum_premmium(String pol_sum_premmium) {
        this.pol_sum_premmium = pol_sum_premmium;
    }

    public String getPol_sub_type_name() {
        return pol_sub_type_name;
    }

    public void setPol_sub_type_name(String pol_sub_type_name) {
        this.pol_sub_type_name = pol_sub_type_name;
    }

    public String getPol_sub_type_img_path() {
        return pol_sub_type_img_path;
    }

    public void setPol_sub_type_img_path(String pol_sub_type_img_path) {
        this.pol_sub_type_img_path = pol_sub_type_img_path;
    }

    public String getInsure_com_img_path() {
        return insure_com_img_path;
    }

    public void setInsure_com_img_path(String insure_com_img_path) {
        this.insure_com_img_path = insure_com_img_path;
    }

    public String getPol_sub_type_id() {
        return pol_sub_type_id;
    }

    public void setPol_sub_type_id(String pol_sub_type_id) {
        this.pol_sub_type_id = pol_sub_type_id;
    }

    public String getMem_count() {
        return mem_count;
    }

    public void setMem_count(String mem_count) {
        this.mem_count = mem_count;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    @Override
    public String toString() {
        return "PolicyData{" +
                "pol_id='" + pol_id + '\'' +
                ", policy_type_id='" + policy_type_id + '\'' +
                ", ins_co_name='" + ins_co_name + '\'' +
                ", pol_mem_insured='" + pol_mem_insured + '\'' +
                ", pol_sum_premmium='" + pol_sum_premmium + '\'' +
                ", pol_sub_type_name='" + pol_sub_type_name + '\'' +
                ", pol_sub_type_img_path='" + pol_sub_type_img_path + '\'' +
                ", insure_com_img_path='" + insure_com_img_path + '\'' +
                ", pol_sub_type_id='" + pol_sub_type_id + '\'' +
                ", mem_count='" + mem_count + '\'' +
                ", members='" + members + '\'' +
                ", coverName='" + coverName + '\'' +
                '}';
    }
}
