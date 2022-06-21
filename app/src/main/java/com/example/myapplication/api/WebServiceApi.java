package com.example.myapplication.api;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WebServiceApi {
    @POST("Users/Login")
    Call<User> isUser(@Body HashMap<String, String> data);

    @POST("Users/Register")
    Call<Void> createUser(@Body User user);

    @POST("Users/Token")
    Call<Void> sendToken(@Body HashMap<String, String> data);

    @GET("Contacts")
    Call<List<Contact>> getContacts(@Query("user") String user);

    @POST("{fullUrl}")
    Call<Void> inviteContact(@Path(value = "fullUrl", encoded = true) String fullUrl, @Body HashMap<String, String> data);

    @POST("contacts/AddContact")
    Call<Void> addContact(@Body HashMap<String, String> data);

    @GET("Contacts/{id}/Messages")
    Call<List<Message>> getMessages(@Path("id") String id, @Query("user") String user);

    @POST("Contacts/{id}/Messages")
    Call<Void> createMessage(@Path("id") String id, @Body HashMap<String, String> data);

    @POST("{fullUrl}")
    Call<Void> transferMsg(@Path(value = "fullUrl", encoded = true) String fullUrl,@Body HashMap<String, String> data);
}
