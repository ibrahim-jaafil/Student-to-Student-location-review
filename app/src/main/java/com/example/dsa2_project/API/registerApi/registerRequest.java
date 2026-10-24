package com.example.dsa2_project.API.registerApi;

public class registerRequest {

    private int STUid;
    private String name;
    private String email;

    private String password;

    public registerRequest(int STUid, String name, String email, String password) {
        this.STUid = STUid;
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
