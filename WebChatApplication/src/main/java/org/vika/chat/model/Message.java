package org.vika.chat.model;

/**
 * Created by Администратор on 26.04.2015.
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String id;
    private String description;
    private String user;
    private String date;
    public Message(String id, String description, String user) {
        this.id = id;
        this.description= description;
        this.user = user;
        this.date=setDate();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String setDate() {
        Date date = new Date();
        String DATE_FORMAT = "dd-MM-yyyy HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }



    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getUser() {
        return this.user;
    }



    public void setUser(String value) {
        this.user = value;
    }

    public String toString() {
        return "{\"id\":\"" + this.id + "\",\"description\":\"" + this.description + "\",\"user\":\"" + this.user + "\"}";
    }
}
