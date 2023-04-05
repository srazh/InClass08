package com.example.inclass08_simplified.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

//represents a chat document in chats root collection.....
public class Chat implements Serializable {
    private ArrayList<String> user_emails;
    private String chat_name;
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
    public String getChat_name() {
        return chat_name;
    }

    public void setChat_name(String chat_name) {
        this.chat_name = chat_name;
    }
    @Override
    public String toString() {
        return "Chat{" +
                "user_emails=" + user_emails +
                '}';
    }
}
