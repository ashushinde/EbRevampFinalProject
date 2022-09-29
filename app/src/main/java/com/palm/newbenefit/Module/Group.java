package com.palm.newbenefit.Module;

public class Group {
    String pol_id;
    String po_id;
    String policy_type_id;
    String ins_co_name;
    String pol_mem_insured;
    String pol_sum_premmium;

    String pol_sub_type_name;
    String pol_sub_type_img_path;
    String insure_com_img_path;
    String pol_sub_type_id;
    String mem_count;
    String cover;

    int members;
    String opd;

    String cover_balancea;
    String opd_suminsured ;
    String enhnace_cover;

    public Group() {
    }

    public Group(String po_id,String pol_id, String policy_type_id, String ins_co_name,
                 String pol_mem_insured, String pol_sum_premmium, String pol_sub_type_name,
                 String pol_sub_type_img_path, String insure_com_img_path,
                 String pol_sub_type_id, String mem_count, String cover, int members,
                 String opd, String cover_balancea, String opd_suminsured,String enhnace_cover) {
        this.po_id = po_id;
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
        this.cover = cover;
        this.members = members;
        this.opd = opd;
        this.cover_balancea = cover_balancea;
        this.opd_suminsured = opd_suminsured;
        this.enhnace_cover = enhnace_cover;

    }

    public Group(String po_id,String pol_id, String policy_type_id, String ins_co_name,
                 String pol_mem_insured, String pol_sum_premmium, String pol_sub_type_name,
                 String pol_sub_type_img_path, String insure_com_img_path,
                 String pol_sub_type_id, String mem_count, String cover, int members,
                 String opd, String cover_balancea, String opd_suminsured) {
        this.po_id = po_id;
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
        this.cover = cover;
        this.members = members;
        this.opd = opd;
        this.cover_balancea = cover_balancea;
        this.opd_suminsured = opd_suminsured;


    }

    @Override
    public String toString() {
        return "Group{" +
                "pol_id='" + pol_id + '\'' +
                ", po_id='" + po_id + '\'' +
                ", policy_type_id='" + policy_type_id + '\'' +
                ", ins_co_name='" + ins_co_name + '\'' +
                ", pol_mem_insured='" + pol_mem_insured + '\'' +
                ", pol_sum_premmium='" + pol_sum_premmium + '\'' +
                ", pol_sub_type_name='" + pol_sub_type_name + '\'' +
                ", pol_sub_type_img_path='" + pol_sub_type_img_path + '\'' +
                ", insure_com_img_path='" + insure_com_img_path + '\'' +
                ", pol_sub_type_id='" + pol_sub_type_id + '\'' +
                ", mem_count='" + mem_count + '\'' +
                ", cover='" + cover + '\'' +
                ", members=" + members +
                ", opd='" + opd + '\'' +
                ", cover_balancea='" + cover_balancea + '\'' +
                ", opd_suminsured='" + opd_suminsured + '\'' +
                ", enhnace_cover='" + enhnace_cover + '\'' +
                '}';
    }

    public String getEnhnace_cover() {
        return enhnace_cover;
    }

    public void setEnhnace_cover(String enhnace_cover) {
        this.enhnace_cover = enhnace_cover;
    }

    public String getPo_id() {
        return po_id;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover_balancea() {
        return cover_balancea;
    }

    public void setCover_balancea(String cover_balancea) {
        this.cover_balancea = cover_balancea;
    }

    public Group(String pol_id, String policy_type_id, String ins_co_name, String pol_mem_insured, String pol_sum_premmium, String pol_sub_type_name, String pol_sub_type_img_path, String insure_com_img_path, String pol_sub_type_id, String mem_count, String cover, int members, String opd, String cover_balancea, String opd_suminsured) {
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
        this.cover = cover;
        this.members = members;
        this.opd = opd;
        this.cover_balancea = cover_balancea;
        this.opd_suminsured = opd_suminsured;
    }

    public String getOpd_suminsured() {
        return opd_suminsured;
    }

    public void setOpd_suminsured(String opd_suminsured) {
        this.opd_suminsured = opd_suminsured;
    }

    public Group(String pol_id, String policy_type_id, String ins_co_name, String pol_mem_insured, String pol_sum_premmium, String pol_sub_type_name, String pol_sub_type_img_path, String insure_com_img_path, String pol_sub_type_id, String mem_count, String cover, int members) {
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
        this.cover = cover;
        this.members = members;
    }

    public Group(String pol_id, String policy_type_id, String ins_co_name, String pol_mem_insured, String pol_sum_premmium, String pol_sub_type_name, String pol_sub_type_img_path, String insure_com_img_path, String pol_sub_type_id, String mem_count, String cover, int members, String opd) {
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
        this.cover = cover;
        this.members = members;
        this.opd = opd;
    }

    public String getOpd() {
        return opd;
    }

    public void setOpd(String opd) {
        this.opd = opd;
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

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }
}
