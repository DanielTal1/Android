package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.adapters.ContactsListAdapter;
import com.example.myapplication.api.Api;
import com.example.myapplication.dao.ContactDao;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.repositories.ContactsRepository;
import com.example.myapplication.viewmodels.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ContactsRepository contactsRepository;
    private ContactsViewModel viewModel;
    private String user;
    private ContactsListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);

        Intent intent = getIntent();
        user = (String) intent.getSerializableExtra("user");
        if (user != null) {
            tvCurrentUser.setText(user);
        }
        contactsRepository = new ContactsRepository(this,user);
        FloatingActionButton btnAddContact = findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(view -> {
            Intent i = new Intent(this, AddContactActivity.class);
            String current_user = user;
            startActivity(i.putExtra("user", current_user));
        });

        RecyclerView lstContacts = findViewById(R.id.lstContacts);
        adapter = new ContactsListAdapter(this, new ContactsListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent iMessages = new Intent(ChatActivity.this, MessagesActivity.class);
                Bundle extras = new Bundle();
                String current_contact = contact.getId();
                String contact_nick = contact.getName();
                extras.putString("user", user);
                extras.putString("contact", current_contact);
                extras.putString("nickname", contact_nick);
                startActivity(iMessages.putExtras(extras));
            }
        });
        Api api = new Api();
        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));

        if (viewModel == null || !user.equals(viewModel.getUser())) {
            viewModel = new ContactsViewModel(this.getApplicationContext(), user);
        }
        viewModel.get().observe(this, contacts -> {
            adapter.setContacts(contacts);
        });

        contactsRepository.getSourceListTodb();

        //call repository
        //repository.getContacts(user)

//        api.getContacts(user, apiContacts-> {
//            adapter.setContacts(apiContacts);
////            for (int i = 0; i < apiContacts.size(); i++) {
////                Contact contact = new Contact(apiContacts.get(i).getId(), apiContacts.get(i).getName());
////                contactDao.insert(contact);
////            }
//        });
        /*
        HashMap<String,String> dict = new HashMap<>();
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ChatActivity.this, instanceIdResult -> {
            String Token=instanceIdResult.getToken();
            dict.put("Username",user);
            dict.put("Token", Token);
            api.sendToken(dict);
        });


         */
    }

    @Override
    protected void onResume(){
        super.onResume();
        setContentView(R.layout.activity_chat);


        FloatingActionButton btnAddContact = findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(view -> {
            Intent i = new Intent(this, AddContactActivity.class);
            String current_user = user;
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i.putExtra("user", current_user));
        });

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });



    }
}