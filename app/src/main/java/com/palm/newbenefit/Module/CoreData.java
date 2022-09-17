package com.palm.newbenefit.Module;

public class CoreData {

    String master_flexi_benefit_id = null;
    String flexi_benefit_name = null;
    String img_name = null;
    String flexi_type = null;
    String employee_flexi_benefit_transaction_id = null;

    String emp_id = null;
    String fr_id = null;
    String employee_flexi_benifit_id = null;
    String transac_start_date = null;
    String transac_end_date = null;
    String transac_type = null;



    String reimbursement_amount = null;
    String gst_amount = null;
    String reimbursement_ingst_amount = null;

    String final_amount = null;
    String balance_amount = null;
    String deduction_type = null;
    private boolean expanded;

    String remarks = null;
    String relationship = null;
    String sum_insured = null;

    String add_info_1 = null;
    String add_info_2 = null;
    String add_info_3 = null;
    String add_info_4 = null;
    String add_info_5 = null;
    String created_at = null;
    String updated_at = null;

    public CoreData(String master_flexi_benefit_id, String flexi_benefit_name, String img_name, String flexi_type, String employee_flexi_benefit_transaction_id, String emp_id, String fr_id, String employee_flexi_benifit_id, String transac_start_date, String transac_end_date, String transac_type, String reimbursement_amount, String gst_amount, String reimbursement_ingst_amount, String final_amount, String balance_amount, String deduction_type, String remarks, String relationship, String sum_insured, String add_info_1, String add_info_2, String add_info_3, String add_info_4, String add_info_5, String created_at, String updated_at) {
        this.master_flexi_benefit_id = master_flexi_benefit_id;
        this.flexi_benefit_name = flexi_benefit_name;
        this.img_name = img_name;
        this.flexi_type = flexi_type;
        this.employee_flexi_benefit_transaction_id = employee_flexi_benefit_transaction_id;
        this.emp_id = emp_id;
        this.fr_id = fr_id;
        this.employee_flexi_benifit_id = employee_flexi_benifit_id;
        this.transac_start_date = transac_start_date;
        this.transac_end_date = transac_end_date;
        this.transac_type = transac_type;
        this.reimbursement_amount = reimbursement_amount;
        this.gst_amount = gst_amount;
        this.reimbursement_ingst_amount = reimbursement_ingst_amount;
        this.final_amount = final_amount;
        this.balance_amount = balance_amount;
        this.deduction_type = deduction_type;
        this.remarks = remarks;
        this.relationship = relationship;
        this.sum_insured = sum_insured;
        this.add_info_1 = add_info_1;
        this.add_info_2 = add_info_2;
        this.add_info_3 = add_info_3;
        this.add_info_4 = add_info_4;
        this.add_info_5 = add_info_5;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public CoreData() {
    }

    @Override
    public String toString() {
        return "CoreData{" +
                "master_flexi_benefit_id='" + master_flexi_benefit_id + '\'' +
                ", flexi_benefit_name='" + flexi_benefit_name + '\'' +
                ", img_name='" + img_name + '\'' +
                ", flexi_type='" + flexi_type + '\'' +
                ", employee_flexi_benefit_transaction_id='" + employee_flexi_benefit_transaction_id + '\'' +
                ", emp_id='" + emp_id + '\'' +
                ", fr_id='" + fr_id + '\'' +
                ", employee_flexi_benifit_id='" + employee_flexi_benifit_id + '\'' +
                ", transac_start_date='" + transac_start_date + '\'' +
                ", transac_end_date='" + transac_end_date + '\'' +
                ", transac_type='" + transac_type + '\'' +
                ", reimbursement_amount='" + reimbursement_amount + '\'' +
                ", gst_amount='" + gst_amount + '\'' +
                ", reimbursement_ingst_amount='" + reimbursement_ingst_amount + '\'' +
                ", final_amount='" + final_amount + '\'' +
                ", balance_amount='" + balance_amount + '\'' +
                ", deduction_type='" + deduction_type + '\'' +
                ", remarks='" + remarks + '\'' +
                ", relationship='" + relationship + '\'' +
                ", sum_insured='" + sum_insured + '\'' +
                ", add_info_1='" + add_info_1 + '\'' +
                ", add_info_2='" + add_info_2 + '\'' +
                ", add_info_3='" + add_info_3 + '\'' +
                ", add_info_4='" + add_info_4 + '\'' +
                ", add_info_5='" + add_info_5 + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }

    public String getMaster_flexi_benefit_id() {
        return master_flexi_benefit_id;
    }

    public void setMaster_flexi_benefit_id(String master_flexi_benefit_id) {
        this.master_flexi_benefit_id = master_flexi_benefit_id;
    }

    public String getFlexi_benefit_name() {
        return flexi_benefit_name;
    }

    public void setFlexi_benefit_name(String flexi_benefit_name) {
        this.flexi_benefit_name = flexi_benefit_name;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getFlexi_type() {
        return flexi_type;
    }

    public void setFlexi_type(String flexi_type) {
        this.flexi_type = flexi_type;
    }

    public String getEmployee_flexi_benefit_transaction_id() {
        return employee_flexi_benefit_transaction_id;
    }

    public void setEmployee_flexi_benefit_transaction_id(String employee_flexi_benefit_transaction_id) {
        this.employee_flexi_benefit_transaction_id = employee_flexi_benefit_transaction_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getFr_id() {
        return fr_id;
    }

    public void setFr_id(String fr_id) {
        this.fr_id = fr_id;
    }

    public String getEmployee_flexi_benifit_id() {
        return employee_flexi_benifit_id;
    }

    public void setEmployee_flexi_benifit_id(String employee_flexi_benifit_id) {
        this.employee_flexi_benifit_id = employee_flexi_benifit_id;
    }

    public String getTransac_start_date() {
        return transac_start_date;
    }

    public void setTransac_start_date(String transac_start_date) {
        this.transac_start_date = transac_start_date;
    }

    public String getTransac_end_date() {
        return transac_end_date;
    }

    public void setTransac_end_date(String transac_end_date) {
        this.transac_end_date = transac_end_date;
    }

    public String getTransac_type() {
        return transac_type;
    }

    public void setTransac_type(String transac_type) {
        this.transac_type = transac_type;
    }

    public String getReimbursement_amount() {
        return reimbursement_amount;
    }

    public void setReimbursement_amount(String reimbursement_amount) {
        this.reimbursement_amount = reimbursement_amount;
    }

    public String getGst_amount() {
        return gst_amount;
    }

    public void setGst_amount(String gst_amount) {
        this.gst_amount = gst_amount;
    }

    public String getReimbursement_ingst_amount() {
        return reimbursement_ingst_amount;
    }

    public void setReimbursement_ingst_amount(String reimbursement_ingst_amount) {
        this.reimbursement_ingst_amount = reimbursement_ingst_amount;
    }

    public String getFinal_amount() {
        return final_amount;
    }

    public void setFinal_amount(String final_amount) {
        this.final_amount = final_amount;
    }

    public String getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(String balance_amount) {
        this.balance_amount = balance_amount;
    }

    public String getDeduction_type() {
        return deduction_type;
    }

    public void setDeduction_type(String deduction_type) {
        this.deduction_type = deduction_type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getSum_insured() {
        return sum_insured;
    }

    public void setSum_insured(String sum_insured) {
        this.sum_insured = sum_insured;
    }

    public String getAdd_info_1() {
        return add_info_1;
    }

    public void setAdd_info_1(String add_info_1) {
        this.add_info_1 = add_info_1;
    }

    public String getAdd_info_2() {
        return add_info_2;
    }

    public void setAdd_info_2(String add_info_2) {
        this.add_info_2 = add_info_2;
    }

    public String getAdd_info_3() {
        return add_info_3;
    }

    public void setAdd_info_3(String add_info_3) {
        this.add_info_3 = add_info_3;
    }

    public String getAdd_info_4() {
        return add_info_4;
    }

    public void setAdd_info_4(String add_info_4) {
        this.add_info_4 = add_info_4;
    }

    public String getAdd_info_5() {
        return add_info_5;
    }

    public void setAdd_info_5(String add_info_5) {
        this.add_info_5 = add_info_5;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }





    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }
}
