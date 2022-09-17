package com.palm.newbenefit.Module;

public class AddNominee {
    String id=null;
    String nom_first_name=null;
    String nom_last_name=null;
    String nom_dob=null;
    String nom_relation=null;
    String g_relation=null;
    String g_first_name=null;
    String g_last_name=null;
    String g_dob=null;
    String share_percent=null;

    @Override
    public String toString() {
        return "AddNominee{" +
                "id='" + id + '\'' +
                ", nom_first_name='" + nom_first_name + '\'' +
                ", nom_last_name='" + nom_last_name + '\'' +
                ", nom_dob='" + nom_dob + '\'' +
                ", nom_relation='" + nom_relation + '\'' +
                ", g_relation='" + g_relation + '\'' +
                ", g_first_name='" + g_first_name + '\'' +
                ", g_last_name='" + g_last_name + '\'' +
                ", g_dob='" + g_dob + '\'' +
                ", share_percent='" + share_percent + '\'' +
                '}';
    }

    public AddNominee( String nom_first_name, String nom_last_name, String nom_dob, String nom_relation, String g_relation, String g_first_name, String g_last_name, String g_dob, String share_percent) {

        this.nom_first_name = nom_first_name;
        this.nom_last_name = nom_last_name;
        this.nom_dob = nom_dob;
        this.nom_relation = nom_relation;
        this.g_relation = g_relation;
        this.g_first_name = g_first_name;
        this.g_last_name = g_last_name;
        this.g_dob = g_dob;
        this.share_percent = share_percent;
    }

    public AddNominee() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom_first_name() {
        return nom_first_name;
    }

    public void setNom_first_name(String nom_first_name) {
        this.nom_first_name = nom_first_name;
    }

    public String getNom_last_name() {
        return nom_last_name;
    }

    public void setNom_last_name(String nom_last_name) {
        this.nom_last_name = nom_last_name;
    }

    public String getNom_dob() {
        return nom_dob;
    }

    public void setNom_dob(String nom_dob) {
        this.nom_dob = nom_dob;
    }

    public String getNom_relation() {
        return nom_relation;
    }

    public void setNom_relation(String nom_relation) {
        this.nom_relation = nom_relation;
    }

    public String getG_relation() {
        return g_relation;
    }

    public void setG_relation(String g_relation) {
        this.g_relation = g_relation;
    }

    public String getG_first_name() {
        return g_first_name;
    }

    public void setG_first_name(String g_first_name) {
        this.g_first_name = g_first_name;
    }

    public String getG_last_name() {
        return g_last_name;
    }

    public void setG_last_name(String g_last_name) {
        this.g_last_name = g_last_name;
    }

    public String getG_dob() {
        return g_dob;
    }

    public void setG_dob(String g_dob) {
        this.g_dob = g_dob;
    }

    public String getShare_percent() {
        return share_percent;
    }

    public void setShare_percent(String share_percent) {
        this.share_percent = share_percent;
    }
}
