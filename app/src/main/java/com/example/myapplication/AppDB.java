package com.example.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.ContactDao;
import com.example.myapplication.entities.Contact;

import java.util.Objects;

@Database(entities = {Contact.class}, version = 2)
public abstract class  AppDB extends RoomDatabase {
    private static volatile AppDB db;
    public abstract   ContactDao contactDao();
    private static volatile String currentUser;

    public static AppDB getInstance(Context context,String user) {
        if (user == null) {
            throw  new RuntimeException("Attempt to open Invalid - CANNOT continue");
        }
        if ( currentUser == null || (!Objects.equals(currentUser, user))){
            if (db != null) {
                if (db.isOpen()) {
                    db.close();
                }
                db = null;
            }
            currentUser=user;
        }
        if(db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, user)
                    .allowMainThreadQueries().build();
        }
        return db;
    }
}
