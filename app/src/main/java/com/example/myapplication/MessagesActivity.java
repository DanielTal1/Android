package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.adapters.MessagesListAdapter;
import com.example.myapplication.api.Api;
import com.example.myapplication.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        TextView tvCurrentContact = findViewById(R.id.tvCurrentContact);

        Intent intent = getIntent();
//        String contact = (String) intent.getSerializableExtra("contact");
        Bundle extras = intent.getExtras();
        String contact = extras.getString("contact");
        String user = extras.getString("user");

        if (contact != null) {
            tvCurrentContact.setText(contact);
        }

        RecyclerView lstMessages = findViewById(R.id.lstMessages);
        final MessagesListAdapter adapter = new MessagesListAdapter(this);

        Api api = new Api();
        lstMessages.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        lstMessages.setLayoutManager(layoutManager);
        api.getMessages(user, contact, apiMessages-> {
            adapter.setMessages(apiMessages);
        });

//        List<Message> messages = new ArrayList<>();
//        messages.add(new Message(1, "Hello", "12:00", true));
//        messages.add(new Message(2, "Hi", "12:00", false));
//        messages.add(new Message(3, "How are you?", "12:00", false));
//        messages.add(new Message(4, "I'm fine", "12:00", true));
//        messages.add(new Message(5, "What about you?", "12:00", true));
//        messages.add(new Message(6, "Cool", "12:00", false));
//        messages.add(new Message(7, "Where are you?", "12:00", false));
//        messages.add(new Message(8, "Here", "12:00", true));

//        adapter.setMessages(messages);

    }
}