package com.example.inclass08_simplified.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class ChatRecord implements Serializable {
    private String documentReference;
    private Set<String> user_emails;

    public ChatRecord(){}

    public ChatRecord(String documentReference, Set<String> user_emails) {
        this.documentReference = documentReference;
        this.user_emails = user_emails;
    }

    @Override
    public String toString() {
        return "ChatRecord{" +
                "documentReference='" + documentReference + '\'' +
                ", user_emails=" + user_emails +
                '}';
    }

    public String getDocumentReference() {
        return documentReference;
    }

    public void setDocumentReference(String documentReference) {
        this.documentReference = documentReference;
    }

    public Set<String> getUser_emails() {
        return user_emails;
    }

    public void setUser_emails(Set<String> user_emails) {
        this.user_emails = user_emails;
    }
}
