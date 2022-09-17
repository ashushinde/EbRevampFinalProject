package com.palm.newbenefit.Module;

public class DocumentData {

    String doc_name;
    String doc_link;
    String doc_type;


    @Override
    public String toString() {
        return "DocumentData{" +
                "doc_name='" + doc_name + '\'' +
                ", doc_link='" + doc_link + '\'' +
                ", doc_type='" + doc_type + '\'' +
                '}';
    }

    public DocumentData(String doc_name, String doc_link, String doc_type) {
        this.doc_name = doc_name;
        this.doc_link = doc_link;
        this.doc_type = doc_type;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_link() {
        return doc_link;
    }

    public void setDoc_link(String doc_link) {
        this.doc_link = doc_link;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }
}



