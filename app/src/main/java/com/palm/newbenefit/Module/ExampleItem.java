package com.palm.newbenefit.Module;

public class ExampleItem {

    String id;
    String ailment_name;

    public ExampleItem() {
    }

    public ExampleItem(String id, String ailment_name) {
        this.id = id;
        this.ailment_name = ailment_name;
    }

    @Override
    public String toString() {
        return "ExampleItem{" +
                "id='" + id + '\'' +
                ", ailment_name='" + ailment_name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAilment_name() {
        return ailment_name;
    }

    public void setAilment_name(String ailment_name) {
        this.ailment_name = ailment_name;
    }
}
