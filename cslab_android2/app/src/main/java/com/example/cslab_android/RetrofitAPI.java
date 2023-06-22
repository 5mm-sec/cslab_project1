package com.example.cslab_android;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RetrofitAPI {
    @POST("/submit")
    Call<registerDATA> register(@Body JsonObject body);

    @POST("/audio")
    Call<registerDATA> audioSend(@Body JsonObject body);

}