package com.palm.newbenefit.Module;

public class MyDashboard {

String heading;

    String sub_heading;
    String description;
    String cardBackground;
    String cardColor;
    String textColor;
    String redirect_url;
    String image;
    String has_icon;
    String show_card;

    public MyDashboard() {
    }

    public MyDashboard(String heading, String sub_heading,
                       String description, String cardBackground,
                       String cardColor, String textColor, String redirect_url,
                       String image, String has_icon, String show_card) {
        this.heading = heading;
        this.sub_heading = sub_heading;
        this.description = description;
        this.cardBackground = cardBackground;
        this.cardColor = cardColor;
        this.textColor = textColor;
        this.redirect_url = redirect_url;
        this.image = image;
        this.has_icon = has_icon;
        this.show_card = show_card;
    }

    @Override
    public String toString() {
        return "MyDashboard{" +
                "heading='" + heading + '\'' +
                ", sub_heading='" + sub_heading + '\'' +
                ", description='" + description + '\'' +
                ", cardBackground='" + cardBackground + '\'' +
                ", cardColor='" + cardColor + '\'' +
                ", textColor='" + textColor + '\'' +
                ", redirect_url='" + redirect_url + '\'' +
                ", image='" + image + '\'' +
                ", has_icon='" + has_icon + '\'' +
                ", show_card='" + show_card + '\'' +
                '}';
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSub_heading() {
        return sub_heading;
    }

    public void setSub_heading(String sub_heading) {
        this.sub_heading = sub_heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardBackground() {
        return cardBackground;
    }

    public void setCardBackground(String cardBackground) {
        this.cardBackground = cardBackground;
    }

    public String getCardColor() {
        return cardColor;
    }

    public void setCardColor(String cardColor) {
        this.cardColor = cardColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHas_icon() {
        return has_icon;
    }

    public void setHas_icon(String has_icon) {
        this.has_icon = has_icon;
    }

    public String getShow_card() {
        return show_card;
    }

    public void setShow_card(String show_card) {
        this.show_card = show_card;
    }
}
