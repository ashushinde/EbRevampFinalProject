package com.palm.newbenefit.Module;

public class Notification {
    String id;

    String notification_id;
    String dynamic_content;
    String notification_type_id;
    String action_type_id;
    String is_read;
    String title;
    String content;
    String action_url;
    String link;
    String start_date;
    String end_date;
    String updated_at;

    public Notification() {
    }

    public Notification(String id, String notification_id, String dynamic_content, String notification_type_id, String action_type_id, String is_read, String title, String content, String action_url, String link, String start_date, String end_date, String updated_at) {
        this.id = id;
        this.notification_id = notification_id;
        this.dynamic_content = dynamic_content;
        this.notification_type_id = notification_type_id;
        this.action_type_id = action_type_id;
        this.is_read = is_read;
        this.title = title;
        this.content = content;
        this.action_url = action_url;
        this.link = link;
        this.start_date = start_date;
        this.end_date = end_date;
        this.updated_at = updated_at;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getDynamic_content() {
        return dynamic_content;
    }

    public void setDynamic_content(String dynamic_content) {
        this.dynamic_content = dynamic_content;
    }

    public String getNotification_type_id() {
        return notification_type_id;
    }

    public void setNotification_type_id(String notification_type_id) {
        this.notification_type_id = notification_type_id;
    }

    public String getAction_type_id() {
        return action_type_id;
    }

    public void setAction_type_id(String action_type_id) {
        this.action_type_id = action_type_id;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
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

    public String getAction_url() {
        return action_url;
    }

    public void setAction_url(String action_url) {
        this.action_url = action_url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", notification_id='" + notification_id + '\'' +
                ", dynamic_content='" + dynamic_content + '\'' +
                ", notification_type_id='" + notification_type_id + '\'' +
                ", action_type_id='" + action_type_id + '\'' +
                ", is_read='" + is_read + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", action_url='" + action_url + '\'' +
                ", link='" + link + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}