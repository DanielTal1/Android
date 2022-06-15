package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapters.ContactsListAdapter;
import com.example.myapplication.api.ContactsApi;
import com.example.myapplication.entities.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

//    private AppDB db;
//    private PostDao postDao;

    private List<Contact> contacts;
//    private ArrayAdapter<Contact> adapter;

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

        ContactsApi contactsApi = new ContactsApi();
        contactsApi.get();

        RecyclerView lstContacts = findViewById(R.id.lstContacts);
        final ContactsListAdapter adapter = new ContactsListAdapter(this);

        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));

        // it's a hardcoded example
        contacts = new ArrayList<>();
        contacts.add(new Contact(1, "Alice", R.drawable.cat));
        contacts.add(new Contact(2, "Bob", R.drawable.cat));
        contacts.add(new Contact(3, "Rita", R.drawable.cat));
        contacts.add(new Contact(4, "Daniel", R.drawable.cat));
        contacts.add(new Contact(5, "Foo", R.drawable.cat));
        contacts.add(new Contact(6, "Itay", R.drawable.cat));
        contacts.add(new Contact(7, "Hunter", R.drawable.cat));

        adapter.setContacts(contacts);

    }
}