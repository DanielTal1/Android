package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.Message;

import java.util.List;

public class MessagesViewModel {
//    private MessagesRepository mRepository;

    private LiveData<List<Message>> messages;

//    public MessagesViewModel () {
////        mRepository = new MessagesRepository();
////        messages = mRepository.getAll();
//    }
//
//    public LiveData<List<Message>> get() {return messages; }
//
//    public void add(Message message) { mRepository.add(message); }
//
//    public void delete(Message message) { mRepository.delete(message); }
//
//    public void reload() {mRepository.reload();}}
}