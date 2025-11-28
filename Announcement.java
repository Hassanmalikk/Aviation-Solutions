package com.example.aviation_solutions;

public class Announcement {

    private String subject;
    private String body;
    private String timestamp;

    public Announcement() {
        // Default constructor required for Firebase
    }

    public Announcement(String subject, String body, String timestamp) {
        this.subject = subject;
        this.body = body;
        this.timestamp = timestamp;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
