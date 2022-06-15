package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapters.ContactsListAdapter;
import com.example.myapplication.api.Api;
import com.example.myapplication.entities.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

//    private AppDB db;
//    private PostDao postDao;
    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);

        Intent intent = getIntent();
        String user = (String) intent.getSerializableExtra("user");
        if (user != null) {
            tvCurrentUser.setText(user);
        }

//        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "PostsDB")
//                .allowMainThreadQueries().build();
//
//        postDao = db.postDao();

        FloatingActionButton btnAddContact = findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(view -> {
            Intent i = new Intent(this, AddContactActivity.class);
            startActivity(i);
        });

        RecyclerView lstContacts = findViewById(R.id.lstContacts);
        final ContactsListAdapter adapter = new ContactsListAdapter(this);
        Api api = new Api();
        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));
        api.get(user, apiContacts-> {
            adapter.setContacts(apiContacts);
        });



    }
}