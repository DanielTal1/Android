package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import androidx.lifecycle.LiveData;
import com.example.myapplication.entities.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact WHERE id=:id")
    List<Contact> index(String id);

    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact")
    LiveData<List<Contact>> getAllLive();


    @Insert
    void insert(Contact... contacts);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Contact> contacts);
    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact... contacts);
}