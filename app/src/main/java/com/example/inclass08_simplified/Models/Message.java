package com.example.inclass08_simplified.Models;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String text;
    private String imageuri;

    private User sender;

    private long time;

    public Message(){};

    public Message(String text, User sender) {
        this.text = text;
        this.sender = sender;
        Date date = new Date();
        this.time = date.getTime();
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", imageuri='" + imageuri + '\'' +
                '}';
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}
