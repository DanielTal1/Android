package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.R;

@Entity
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String contact;
    private String server;
    private String last;
    private String lastdate;
    private int pic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getServer() { return server; }

    public void setServer(String server) { this.server = server; }

    public String getLast() { return last; }

    public void setLast(String last) { this.last = last; }

    public String getLastdate() { return lastdate;}

    public void setLastdate(String lastdate) { this.lastdate = lastdate; }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public Contact() {
        this.pic = R.drawable.cat;
    }

    public Contact(int id, String userId, String contact, String server, String last, String lastdate) {
        this.id = id;
        this.userId = userId;
        this.contact = contact;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
    }

    public Contact(int id, String contact, int pic) {
        this.id = id;
        this.contact = contact;
        this.pic = pic;
    }
}
