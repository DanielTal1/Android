package com.example.myapplication.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.AppDB;
import com.example.myapplication.api.Api;
//import com.example.myapplication.api.ContactApi;
import com.example.myapplication.dao.ContactDao;
import com.example.myapplication.entities.Contact;

import java.util.LinkedList;
import java.util.List;

public class ContactsRepository {
    private ContactDao dao;
    private MutableLiveData<List<Contact>> contactListData;
    private Api api;
    private AppDB db;
    private String user;

    public ContactsRepository(Context context, String user) {
        db = AppDB.getInstance(context,user);
        dao = db.contactDao();
        this.user = user;
        contactListData = dao.getAllLive();
        api = new Api();
    }


    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public String getUser() {
        return user;
    }

    public void getSourceListTodb() {
        api.getContacts(user, apiContacts-> {
            dao.insertAll(apiContacts);
        });
    }

    //getContacts function
    //var list= what returns from api
    //dao.insert(list)

    public LiveData<List<Contact>> getRoomContacts(){
        return contactListData;
    }
}



//    public void setData(List<Contact> contacts) {
//        this.contactListData.setValue(contacts);
//        for (int i = 0; i < contacts.size(); i++) {
//            this.dao.insert(contacts.get(i));
//        }
//    }

    //    public void add(final Contact contact) {
//        api.add(contact);
//    }
//
//    public void delete(final Contact contact) {
//        api.delete(contact);
//    }
//
//    public void reload() {
//        api.get();
//    }

