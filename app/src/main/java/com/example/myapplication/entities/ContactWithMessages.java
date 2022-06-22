package com.example.myapplication.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ContactWithMessages {

    @Embedded public Contact contact;
    @Relation(
            parentColumn = "id",
            entityColumn = "contactId"
    ) public List<Message>messageList;
}
