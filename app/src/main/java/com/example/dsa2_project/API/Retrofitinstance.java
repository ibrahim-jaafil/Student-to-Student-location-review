package com.example.dsa2_project.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitinstance {
    
    private static final String BASE_URL ="http://10.0.2.2:4002/";
    private static Retrofit retrofit;


public static Auth getservice(){
    if(retrofit ==null){
    retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
}
 return retrofit.create(Auth.class);

}

}
