package com.example.myapplication.api;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.entities.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class Api {
    Retrofit retrofit;
    WebServiceApi webServiceApi;

    public Api(){
        retrofit=new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceApi=retrofit.create(WebServiceApi.class);
    }

    public void checkLogin(HashMap<String, String> data, final MyCallBack mycallback){
        Call<User> call=webServiceApi.isUser(data);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mycallback.onResponseUser(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
            }


        });
    }

    public void RegisterUser(User user, final MyCallbackVoid mycallback){
        Call<Void> call=webServiceApi.createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                mycallback.onResponse(response.code() == 201);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                call.cancel();
            }


        });
    }
}
