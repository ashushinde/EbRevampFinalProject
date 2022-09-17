package com.palm.newbenefit.Module;

public class CustomerLead {
    String id;
    String companyname;
    String industrytype;
    String contact;
    String email;
    String quotename;
    String finalpremium;
    String suminsured;
    String approvalremark;
    String documents;
    String activityhandle;
    String createdat;
    String status;
    String en_id;

    public CustomerLead() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CustomerLead{" +
                "id='" + id + '\'' +
                ", companyname='" + companyname + '\'' +
                ", industrytype='" + industrytype + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", quotename='" + quotename + '\'' +
                ", finalpremium='" + finalpremium + '\'' +
                ", suminsured='" + suminsured + '\'' +
                ", approvalremark='" + approvalremark + '\'' +
                ", documents='" + documents + '\'' +
                ", activityhandle='" + activityhandle + '\'' +
                ", createdat='" + createdat + '\'' +
                ", status='" + status + '\'' +
                ", en_id='" + en_id + '\'' +
                '}';
    }

    public String getEn_id() {
        return en_id;
    }

    public void setEn_id(String en_id) {
        this.en_id = en_id;
    }



    public CustomerLead(String id,String en_id,String companyname, String industrytype, String contact, String email, String quotename, String finalpremium, String suminsured, String approvalremark, String documents, String activityhandle, String createdat, String status) {
        this.id = id;
        this.en_id = en_id;
        this.companyname = companyname;

        this.industrytype = industrytype;
        this.contact = contact;
        this.email = email;
        this.quotename = quotename;
        this.finalpremium = finalpremium;
        this.suminsured = suminsured;
        this.approvalremark = approvalremark;
        this.documents = documents;
        this.activityhandle = activityhandle;
        this.createdat = createdat;
        this.status = status;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getIndustrytype() {
        return industrytype;
    }

    public void setIndustrytype(String industrytype) {
        this.industrytype = industrytype;
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

    public String getQuotename() {
        return quotename;
    }

    public void setQuotename(String quotename) {
        this.quotename = quotename;
    }

    public String getFinalpremium() {
        return finalpremium;
    }

    public void setFinalpremium(String finalpremium) {
        this.finalpremium = finalpremium;
    }

    public String getSuminsured() {
        return suminsured;
    }

    public void setSuminsured(String suminsured) {
        this.suminsured = suminsured;
    }

    public String getApprovalremark() {
        return approvalremark;
    }

    public void setApprovalremark(String approvalremark) {
        this.approvalremark = approvalremark;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getActivityhandle() {
        return activityhandle;
    }

    public void setActivityhandle(String activityhandle) {
        this.activityhandle = activityhandle;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
