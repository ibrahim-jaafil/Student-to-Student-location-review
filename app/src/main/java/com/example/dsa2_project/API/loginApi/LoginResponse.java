package com.example.dsa2_project.API.loginApi;

public class LoginResponse {
    private String message;
    private int STUid;



    public int getId(){
        return STUid;
    }
    public String getMessage(){
        return message;
    }
}
