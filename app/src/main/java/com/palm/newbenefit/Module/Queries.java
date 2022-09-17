package com.palm.newbenefit.Module;

public class Queries {

    String id;
    String query_id;
    String query_type;

    String query_sub_type;
    String comments;
    String raised_on;
    String resolution;
    String resolved_on;
    String status;
    String document;
    String resolution_tat;

    public Queries() {
    }

    public Queries(String id, String query_id, String query_type, String query_sub_type, String comments, String raised_on, String resolution, String resolved_on, String status, String document, String resolution_tat) {
        this.id = id;
        this.query_id = query_id;
        this.query_type = query_type;
        this.query_sub_type = query_sub_type;
        this.comments = comments;
        this.raised_on = raised_on;
        this.resolution = resolution;
        this.resolved_on = resolved_on;
        this.status = status;
        this.document = document;
        this.resolution_tat = resolution_tat;
    }

    @Override
    public String toString() {
        return "Queries{" +
                "id='" + id + '\'' +
                ", query_id='" + query_id + '\'' +
                ", query_type='" + query_type + '\'' +
                ", query_sub_type='" + query_sub_type + '\'' +
                ", comments='" + comments + '\'' +
                ", raised_on='" + raised_on + '\'' +
                ", resolution='" + resolution + '\'' +
                ", resolved_on='" + resolved_on + '\'' +
                ", status='" + status + '\'' +
                ", document='" + document + '\'' +
                ", resolution_tat='" + resolution_tat + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String query_id) {
        this.query_id = query_id;
    }

    public String getQuery_type() {
        return query_type;
    }

    public void setQuery_type(String query_type) {
        this.query_type = query_type;
    }

    public String getQuery_sub_type() {
        return query_sub_type;
    }

    public void setQuery_sub_type(String query_sub_type) {
        this.query_sub_type = query_sub_type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRaised_on() {
        return raised_on;
    }

    public void setRaised_on(String raised_on) {
        this.raised_on = raised_on;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getResolved_on() {
        return resolved_on;
    }

    public void setResolved_on(String resolved_on) {
        this.resolved_on = resolved_on;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getResolution_tat() {
        return resolution_tat;
    }

    public void setResolution_tat(String resolution_tat) {
        this.resolution_tat = resolution_tat;
    }
}
