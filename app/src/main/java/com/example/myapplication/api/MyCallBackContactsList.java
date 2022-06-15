package com.example.myapplication.api;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.User;

import java.util.List;

public interface MyCallBackContactsList {
    void onResponse(List<Contact> contacts);
}
