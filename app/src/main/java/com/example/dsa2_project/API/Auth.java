package com.example.dsa2_project.API;


import com.example.dsa2_project.API.load.loadCampusesResponse;
import com.example.dsa2_project.API.load.loadFavsRequest;
import com.example.dsa2_project.API.load.loadFavsResponse;
import com.example.dsa2_project.API.load.loadPlacesRequest;
import com.example.dsa2_project.API.load.loadPlacesResponse;
import com.example.dsa2_project.API.load.loadReviewRequest;
import com.example.dsa2_project.API.load.loadReviewsResponse;
import com.example.dsa2_project.API.load.loadUsersResponse;
import com.example.dsa2_project.API.loginApi.LoginRequest;
import com.example.dsa2_project.API.loginApi.LoginResponse;
import com.example.dsa2_project.API.registerApi.registerRequest;
import com.example.dsa2_project.API.registerApi.registerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Auth{
    @POST("auth/login")
    Call <LoginResponse>login(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call <registerResponse>register(@Body registerRequest request);

    @GET("load/loadCampuses")
    Call <List<loadCampusesResponse>>getCampuses();


    @POST("/load/loadPlaces")
    Call <List<loadPlacesResponse>>loadPlaces(@Body loadPlacesRequest request);


    @POST ("load/loadReviews")
    Call <List<loadReviewsResponse>>loadReviews(@Body loadReviewRequest request);

    @GET("load/loadUsers")
    Call <List<loadUsersResponse>>loadUsers();


    @POST("/load/loadfavs")
    Call <List<loadFavsResponse>>loadfavs(@Body loadFavsRequest request);

   // @POST("/load/editfavs")
  //  Call <editFavsResponse>editfavs(@Body editFavsRequest request);

}

