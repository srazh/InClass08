package com.example.inclass08_simplified.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

//represents a chat document in chats root collection.....
public class Chat implements Serializable {
    private ArrayList<String> user_emails;

    public Chat(){}
    public Chat(ArrayList<String> user_emails) {
        this.user_emails = user_emails;
    }

    public ArrayList<String> getUser_emails() {
        return user_emails;
    }

    public void setUser_emails(ArrayList<String> user_emails) {
        this.user_emails = user_emails;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "user_emails=" + user_emails +
                '}';
    }
}
