package com.example.dsa2_project.API;


import com.example.dsa2_project.API.loginApi.LoginRequest;
import com.example.dsa2_project.API.loginApi.LoginResponse;
import com.example.dsa2_project.API.registerApi.registerRequest;
import com.example.dsa2_project.API.registerApi.registerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Auth{
    @POST("auth/login")
    Call <LoginResponse>login(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call <registerResponse>register(@Body registerRequest request);
}

