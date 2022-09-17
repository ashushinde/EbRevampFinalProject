package com.palm.newbenefit.Module;

public class DefDetails {

    String type;
    String subtype;
    String createdat;
    String status;
    String operations;

    @Override
    public String toString() {
        return "DefDetails{" +
                "type='" + type + '\'' +
                ", subtype='" + subtype + '\'' +
                ", createdat='" + createdat + '\'' +
                ", status='" + status + '\'' +
                ", operations='" + operations + '\'' +
                '}';
    }

    public DefDetails(String type, String subtype, String createdat, String status, String operations) {
        this.type = type;
        this.subtype = subtype;
        this.createdat = createdat;
        this.status = status;
        this.operations = operations;
    }

    public DefDetails() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
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

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }
}
