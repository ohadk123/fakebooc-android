package com.example.fake_booc.model;

public class BasicServerResponse {
    private boolean success;
    private String message;
    public BasicServerResponse(boolean success,String message){
        this.success=success;
        this.message=message;
    }
}
