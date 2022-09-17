package com.palm.newbenefit.Module;

public class Module {




    String id = null;
    String name = null;
    String url = null;
    String class_data = null;
    String canread = null;

    String canwrite = null;
    String candelete = null;


    public Module(String id, String name, String url, String class_data, String canread, String canwrite, String candelete) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.class_data = class_data;
        this.canread = canread;
        this.canwrite = canwrite;
        this.candelete = candelete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getClass_data() {
        return class_data;
    }

    public void setClass_data(String class_data) {
        this.class_data = class_data;
    }

    public String getCanread() {
        return canread;
    }

    public void setCanread(String canread) {
        this.canread = canread;
    }

    public String getCanwrite() {
        return canwrite;
    }

    public void setCanwrite(String canwrite) {
        this.canwrite = canwrite;
    }

    public String getCandelete() {
        return candelete;
    }

    public void setCandelete(String candelete) {
        this.candelete = candelete;
    }

    public Module() {
    }

    @Override
    public String toString() {
        return "Module{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", class_data='" + class_data + '\'' +
                ", canread='" + canread + '\'' +
                ", canwrite='" + canwrite + '\'' +
                ", candelete='" + candelete + '\'' +
                '}';
    }
}