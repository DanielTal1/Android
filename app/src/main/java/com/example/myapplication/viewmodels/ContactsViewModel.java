package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.entities.Contact;

import java.util.List;

public class ContactsViewModel extends ViewModel {

//    private ContactsRepository mRepository;

    private LiveData<List<Contact>> contacts;

//    public ContactsViewModel () {
////        mRepository = new ContactsRepository();
////        contacts = mRepository.getAll();
//    }
//
//    public LiveData<List<Contact>> get() {return contacts; }
//
//    public void add(Contact contact) { mRepository.add(contact); }
//
//    public void delete(Contact contact) { mRepository.delete(contact); }
//
//    public void reload() {mRepository.reload();}
}
