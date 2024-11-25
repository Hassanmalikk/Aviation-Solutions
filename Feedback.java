package com.example.aviation_solutions;
public class Feedback {
    private String datetime;
    private String userEmail;
    private String feedbackMessage;
    public Feedback() {
        // Default constructor required for Firebase
    }

    public Feedback(String dateTime, String userEmail, String feedbackMessage) {
        this.datetime = dateTime;
        this.userEmail = userEmail;
        this.feedbackMessage = feedbackMessage;

    }

    public String getDateTime() {
        return datetime;
    }


    public void setDateTime(String dateTime) {
        this.datetime = dateTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }
}
