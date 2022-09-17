package com.palm.newbenefit.Module;

public class ImageData {

    String image=null;
    String imagedata=null;
    String imagesize=null;

    @Override
    public String toString() {
        return "ImageData{" +
                "image='" + image + '\'' +
                '}';
    }

    public ImageData(String image) {
        this.image = image;
    }

    public ImageData() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagedata() {
        return imagedata;
    }

    public void setImagedata(String imagedata) {
        this.imagedata = imagedata;
    }

    public String getImagesize() {
        return imagesize;
    }

    public void setImagesize(String imagesize) {
        this.imagesize = imagesize;
    }
}
