package com.example.myapplication4;

public class Message {
    private String text;
    private String sender;
    private String type;

    public Message() {
        // Required for Firebase
    }

    public Message(String text, String sender, String type) {
        this.text = text;
        this.sender = sender;
        this.type = type;
    }

    // Getters and setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", sender='" + sender + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
