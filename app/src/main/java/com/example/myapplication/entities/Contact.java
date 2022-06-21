package com.example.myapplication.entities;

//import androidx.room.Entity;
//import androidx.room.PrimaryKey;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.myapplication.R;

@Entity(tableName = "Contact")
public class Contact {

    @PrimaryKey()
    @NonNull
    private String id;
    private String name;
    private String server;
    private String last;
    private String lastdate;


    public Contact() {}
    @Ignore
    public Contact(@NonNull String id,  String contact, String server, String last, String lastdate) {
        this.id = id;
        this.name = contact;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
    }


    public String getContact() {
        return name;
    }

    public void setContact(String contact) {
        this.name = contact;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setUserId(@NonNull String userId) {
        id = userId;
    }


    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
