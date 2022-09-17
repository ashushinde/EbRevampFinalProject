package com.palm.newbenefit.Module;

public class MemberData {
    String name ;
    String member_id ;
    String relation;
    String date_of_birth;
    String sum_assured ;
    String cover_date_of_birth ;
    String in_progress_status;
    String health_card;
    String last_name;
    String member_name_tpa;
    String tp_id;
    String coverName;
    String emp_code;
    String policy_sub_type_id;
    String end_date;
    String opd_suminsured;
    String image_url;
    String emp_codes;

    @Override
    public String toString() {
        return "MemberData{" +
                "name='" + name + '\'' +
                ", member_id='" + member_id + '\'' +
                ", relation='" + relation + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", sum_assured='" + sum_assured + '\'' +
                ", cover_date_of_birth='" + cover_date_of_birth + '\'' +
                ", in_progress_status='" + in_progress_status + '\'' +
                ", health_card='" + health_card + '\'' +
                ", last_name='" + last_name + '\'' +
                ", member_name_tpa='" + member_name_tpa + '\'' +
                ", tp_id='" + tp_id + '\'' +
                ", coverName='" + coverName + '\'' +
                ", emp_code='" + emp_code + '\'' +
                ", policy_sub_type_id='" + policy_sub_type_id + '\'' +
                ", end_date='" + end_date + '\'' +
                ", opd_suminsured='" + opd_suminsured + '\'' +
                ", image_url='" + image_url + '\'' +
                ", emp_codes='" + emp_codes + '\'' +
                '}';
    }

    public String getEmp_codes() {
        return emp_codes;
    }

    public void setEmp_codes(String emp_codes) {
        this.emp_codes = emp_codes;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getMember_name_tpa()
    {
        return member_name_tpa;
    }

    public void setMember_name_tpa(String member_name_tpa) {
        this.member_name_tpa = member_name_tpa;
    }



    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public MemberData(String name, String member_id, String relation, String date_of_birth, String sum_assured, String cover_date_of_birth, String in_progress_status, String health_card, String last_name, String member_name_tpa, String tp_id, String coverName, String emp_code, String policy_sub_type_id) {
        this.name = name;
        this.member_id = member_id;
        this.relation = relation;
        this.date_of_birth = date_of_birth;
        this.sum_assured = sum_assured;
        this.cover_date_of_birth = cover_date_of_birth;
        this.in_progress_status = in_progress_status;
        this.health_card = health_card;
        this.last_name = last_name;
        this.member_name_tpa = member_name_tpa;
        this.tp_id = tp_id;
        this.coverName = coverName;
        this.emp_code = emp_code;
        this.policy_sub_type_id = policy_sub_type_id;
    }

    public MemberData(String name, String member_id, String relation, String date_of_birth, String sum_assured, String cover_date_of_birth, String in_progress_status, String health_card, String last_name, String member_name_tpa, String tp_id, String coverName, String emp_code, String policy_sub_type_id,
                      String end_date,String opd_suminsured) {
        this.name = name;
        this.member_id = member_id;
        this.relation = relation;
        this.date_of_birth = date_of_birth;
        this.sum_assured = sum_assured;
        this.cover_date_of_birth = cover_date_of_birth;
        this.in_progress_status = in_progress_status;
        this.health_card = health_card;
        this.last_name = last_name;
        this.member_name_tpa = member_name_tpa;
        this.tp_id = tp_id;
        this.coverName = coverName;
        this.emp_code = emp_code;
        this.policy_sub_type_id = policy_sub_type_id;
        this.end_date = end_date;
        this.opd_suminsured=opd_suminsured;
    }

    public MemberData(String name, String member_id, String relation, String date_of_birth, String sum_assured, String cover_date_of_birth, String in_progress_status, String health_card, String last_name, String member_name_tpa, String tp_id, String coverName, String emp_code, String policy_sub_type_id,
                      String end_date,String opd_suminsured,String image_url,
                      String emp_codes) {
        this.name = name;
        this.member_id = member_id;
        this.relation = relation;
        this.date_of_birth = date_of_birth;
        this.sum_assured = sum_assured;
        this.cover_date_of_birth = cover_date_of_birth;
        this.in_progress_status = in_progress_status;
        this.health_card = health_card;
        this.last_name = last_name;
        this.member_name_tpa = member_name_tpa;
        this.tp_id = tp_id;
        this.coverName = coverName;
        this.emp_code = emp_code;
        this.policy_sub_type_id = policy_sub_type_id;
        this.end_date = end_date;
        this.opd_suminsured=opd_suminsured;
        this.image_url=image_url;
        this.emp_codes=emp_codes;
    }

    public String getPolicy_sub_type_id() {
        return policy_sub_type_id;
    }

    public void setPolicy_sub_type_id(String policy_sub_type_id) {
        this.policy_sub_type_id = policy_sub_type_id;
    }

    public String getOpd_suminsured() {
        return opd_suminsured;
    }

    public void setOpd_suminsured(String opd_suminsured) {
        this.opd_suminsured = opd_suminsured;
    }

    public MemberData(String name, String member_id, String relation, String date_of_birth, String sum_assured, String cover_date_of_birth, String in_progress_status, String health_card, String last_name, String member_name_tpa, String tp_id, String coverName, String emp_code) {
        this.name = name;
        this.member_id = member_id;
        this.relation = relation;
        this.date_of_birth = date_of_birth;
        this.sum_assured = sum_assured;
        this.cover_date_of_birth = cover_date_of_birth;
        this.in_progress_status = in_progress_status;
        this.health_card = health_card;
        this.last_name = last_name;
        this.member_name_tpa = member_name_tpa;
        this.tp_id = tp_id;
        this.coverName = coverName;
        this.emp_code = emp_code;
    }

    public MemberData(String name, String member_id, String relation, String date_of_birth, String sum_assured, String cover_date_of_birth, String in_progress_status, String health_card, String last_name, String member_name_tpa, String tp_id, String coverName) {
        this.name = name;
        this.member_id = member_id;
        this.relation = relation;
        this.date_of_birth = date_of_birth;
        this.sum_assured = sum_assured;
        this.cover_date_of_birth = cover_date_of_birth;
        this.in_progress_status = in_progress_status;
        this.health_card = health_card;
        this.last_name = last_name;
        this.member_name_tpa = member_name_tpa;
        this.tp_id = tp_id;
        this.coverName = coverName;
    }

    public MemberData(String name, String member_id, String relation, String date_of_birth, String sum_assured, String cover_date_of_birth, String in_progress_status, String health_card, String last_name, String member_name_tpa, String tp_id) {
        this.name = name;
        this.member_id = member_id;
        this.relation = relation;
        this.date_of_birth = date_of_birth;
        this.sum_assured = sum_assured;
        this.cover_date_of_birth = cover_date_of_birth;
        this.in_progress_status = in_progress_status;
        this.health_card = health_card;
        this.last_name = last_name;
        this.member_name_tpa = member_name_tpa;
        this.tp_id = tp_id;
    }

    public String getTp_id() {
        return tp_id;
    }

    public void setTp_id(String tp_id) {
        this.tp_id = tp_id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getSum_assured() {
        return sum_assured;
    }

    public void setSum_assured(String sum_assured) {
        this.sum_assured = sum_assured;
    }

    public String getCover_date_of_birth() {
        return cover_date_of_birth;
    }

    public void setCover_date_of_birth(String cover_date_of_birth) {
        this.cover_date_of_birth = cover_date_of_birth;
    }

    public String getIn_progress_status() {
        return in_progress_status;
    }

    public void setIn_progress_status(String in_progress_status) {
        this.in_progress_status = in_progress_status;
    }

    public String getHealth_card() {
        return health_card;
    }

    public void setHealth_card(String health_card) {
        this.health_card = health_card;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }
}
