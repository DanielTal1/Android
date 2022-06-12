package com.example.myapplication.api;

import com.example.myapplication.entities.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface WebServiceApi {
    @POST("Users/Login")
    Call<User> isUser(@Body HashMap<String, String> data);

    @POST("Users/Register")
    Call<Void> createUser(@Body User user);

}
