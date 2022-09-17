package com.palm.newbenefit.Module;

public class RetailInsurance {

    String id;
    String ic_id;
    String name;
    String url;
    String media;

    public RetailInsurance() {
    }

    @Override
    public String toString() {
        return "RetailInsurance{" +
                "id='" + id + '\'' +
                ", ic_id='" + ic_id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", media='" + media + '\'' +
                '}';
    }

    public RetailInsurance(String id, String ic_id, String name, String url, String media) {
        this.id = id;
        this.ic_id = ic_id;
        this.name = name;
        this.url = url;
        this.media = media;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIc_id() {
        return ic_id;
    }

    public void setIc_id(String ic_id) {
        this.ic_id = ic_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
