package com.example.chatapp.allActivity;

public class Message {

    String senderId;
    String receiverId;
    String message;
    String senderEmail;
    String date;


    public Message(String senderId, String receiverId, String message, String senderEmail, String time) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.senderEmail = senderEmail;
        this.date = date;
    }

    public Message() {

    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String time) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", message='" + message + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                ", time='" + date + '\'' +
                '}';
    }
}
