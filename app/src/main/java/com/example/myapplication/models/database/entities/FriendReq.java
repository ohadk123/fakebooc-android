package com.example.myapplication.models.database.entities;

public class FriendReq {
    private String sender;
    private String reciever;

    public String getSender() { return sender; }
    public void setSender(String value) { this.sender = value; }

    public String getReciever() { return reciever; }
    public void setReciever(String value) { this.reciever = value; }
}
