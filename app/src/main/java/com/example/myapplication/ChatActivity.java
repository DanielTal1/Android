package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.adapters.ContactsListAdapter;
import com.example.myapplication.api.Api;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.viewmodels.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

//    private AppDB db;
//    private PostDao postDao;
    private List<Contact> contacts;
//    private ContactsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        viewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
//
//        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
//        refreshLayout.setOnRefreshListener(() -> {
//            viewModel.reload();
//        });

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
            String current_user = user;
            startActivity(i.putExtra("user", current_user));
        });

        RecyclerView lstContacts = findViewById(R.id.lstContacts);
        final ContactsListAdapter adapter = new ContactsListAdapter(this, new ContactsListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent iMessages = new Intent(ChatActivity.this, MessagesActivity.class);
                Bundle extras = new Bundle();
                String current_contact = contact.getID();
                String contact_nick = contact.getName();
                extras.putString("user", user);
                extras.putString("contact", current_contact);
                extras.putString("nickname", contact_nick);
//                startActivity(iMessages.putExtra("contact", current_contact));
                startActivity(iMessages.putExtras(extras));
            }
        });
        Api api = new Api();
        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));
        api.get(user, apiContacts-> {
            adapter.setContacts(apiContacts);
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
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
            String current_user = user;
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i.putExtra("user", current_user));
        });

        RecyclerView lstContacts = findViewById(R.id.lstContacts);
        final ContactsListAdapter adapter = new ContactsListAdapter(this, new ContactsListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent iMessages = new Intent(ChatActivity.this, MessagesActivity.class);
                Bundle extras = new Bundle();
                String current_contact = contact.getID();
                String contact_nick = contact.getName();
                extras.putString("user", user);
                extras.putString("contact", current_contact);
                extras.putString("nickname", contact_nick);
                startActivity(iMessages.putExtras(extras));
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        Api api = new Api();
        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));
        api.get(user, apiContacts-> {
            adapter.setContacts(apiContacts);
        });
    }
}