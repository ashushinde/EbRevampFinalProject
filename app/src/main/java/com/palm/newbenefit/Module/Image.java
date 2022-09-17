package com.palm.newbenefit.Module;

public class Image {

    String Image;
    String title;
    String content;
    String main_content;


    @Override
    public String toString() {
        return "Image{" +
                "Image='" + Image + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", main_content='" + main_content + '\'' +
                '}';
    }

    public Image() {
    }

    public Image(String image, String title, String content, String main_content) {
        Image = image;
        this.title = title;
        this.content = content;
        this.main_content = main_content;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMain_content() {
        return main_content;
    }

    public void setMain_content(String main_content) {
        this.main_content = main_content;
    }
}
