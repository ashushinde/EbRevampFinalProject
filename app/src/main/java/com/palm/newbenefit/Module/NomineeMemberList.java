package com.palm.newbenefit.Module;

public class NomineeMemberList {

    public NomineeMemberList() {
    }

    private String name, relation, sharePercentile;

    public NomineeMemberList(String name, String relation, String sharePercentile) {
        this.name = name;
        this.relation = relation;
        this.sharePercentile = sharePercentile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getSharePercentile() {
        return sharePercentile;
    }

    public void setSharePercentile(String sharePercentile) {
        this.sharePercentile = sharePercentile;
    }

}
