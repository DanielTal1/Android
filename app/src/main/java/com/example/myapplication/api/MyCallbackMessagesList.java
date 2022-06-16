package com.example.myapplication.api;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.Message;

import java.util.List;

public interface MyCallbackMessagesList {
    void onResponse(List<Message> messages);
}
