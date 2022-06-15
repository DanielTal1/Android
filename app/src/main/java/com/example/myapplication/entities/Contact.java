package com.example.myapplication.entities;

//import androidx.room.Entity;
//import androidx.room.PrimaryKey;

import com.example.myapplication.R;

//@Entity
public class Contact {

    //@PrimaryKey(autoGenerate = true)


    public String getContact() {
        return name;
    }

    public void setContact(String contact) {
        this.name = contact;
    }

    private String id;
    private String name;
    private String server;
    private String last;
    private String lastdate;

    public String getID() {
        return id;
    }

    public void setID(String ID) {
        this.id = ID;
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


    public Contact(String id,  String contact, String server, String last, String lastdate) {
        this.id = id;
        this.name = contact;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
    }

    public Contact(String id, String contact) {
        this.id = id;
        this.name = contact;
    }
}
