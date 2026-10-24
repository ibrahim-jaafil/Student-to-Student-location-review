package com.example.dsa2_project.API.loginApi;

public class LoginRequest {

 private int STUid;
 private String password;


 public LoginRequest(int id, String password) {
  this.STUid = id;
  this.password = password;
 }
}
