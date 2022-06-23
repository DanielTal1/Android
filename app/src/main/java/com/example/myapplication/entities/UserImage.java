package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserImage")
public class UserImage {
    public UserImage(@NonNull String image) {
        this.image = image;
    }

    @NonNull
    public String getImage() {
        return image;
    }

    public void setImage(@NonNull String image) {
        this.image = image;
    }

    @PrimaryKey
    @NonNull
    private String image;

}
