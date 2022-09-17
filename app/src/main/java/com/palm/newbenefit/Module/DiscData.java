package com.palm.newbenefit.Module;

public class DiscData {

    String id;
    String claim_id;
    String remark;
    String response;
    String other_document;
    String status;
    String created_by;
    String created_at;

    public DiscData(String id, String claim_id, String remark, String response, String other_document, String status, String created_by, String created_at) {
        this.id = id;
        this.claim_id = claim_id;
        this.remark = remark;
        this.response = response;
        this.other_document = other_document;
        this.status = status;
        this.created_by = created_by;
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "DiscData{" +
                "id='" + id + '\'' +
                ", claim_id='" + claim_id + '\'' +
                ", remark='" + remark + '\'' +
                ", response='" + response + '\'' +
                ", other_document='" + other_document + '\'' +
                ", status='" + status + '\'' +
                ", created_by='" + created_by + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClaim_id() {
        return claim_id;
    }

    public void setClaim_id(String claim_id) {
        this.claim_id = claim_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getOther_document() {
        return other_document;
    }

    public void setOther_document(String other_document) {
        this.other_document = other_document;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public DiscData() {
    }


}
