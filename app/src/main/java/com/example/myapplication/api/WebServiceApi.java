package com.example.myapplication.api;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebServiceApi {
    @POST("Users/Login")
    Call<User> isUser(@Body HashMap<String, String> data);

    @POST("Users/Register")
    Call<Void> createUser(@Body User user);

    @GET("Contacts")
    Call<List<Contact>> getContacts(@Query("user") String user);

//    @POST("contacts")
//    Call<Void> createContact(@Body Contact contact);
//
//    @DELETE("contacts/{id}")
//    Call<Void> deleteContact(@Path("id") int id);
}
