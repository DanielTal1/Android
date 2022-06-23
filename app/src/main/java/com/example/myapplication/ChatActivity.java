package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
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
import com.example.myapplication.entities.UserImage;
import com.example.myapplication.repositories.ContactsRepository;
import com.example.myapplication.viewmodels.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ContactsViewModel viewModel;
    private String user;
    private ContactsListAdapter adapter;
    private ContactDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        TextView tvCurrentUser = findViewById(R.id.tvCurrentUser);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user = extras.getString("user");
        String server = extras.getString("server");
        if (user != null) {
            tvCurrentUser.setText(user);
            System.out.println(tvCurrentUser.getText().toString());
        }
        AppDB db = AppDB.getInstance(this, user);
        dao = db.contactDao();
        UserImage userImage=dao.getImage();
        if(userImage!=null){
            if(userImage.getImage()!=null){
                Uri imageUri= Uri.parse(userImage.getImage());
                ImageView imageView = findViewById(R.id.UserImage);
                imageView.setImageURI(imageUri);
            }
        }
        FloatingActionButton btnAddContact = findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(view -> {
            Intent i = new Intent(this, AddContactActivity.class);
            Bundle extras_add = new Bundle();
            String current_user = user;
            extras.putString("user", current_user);
            extras.putString("server", server);
            startActivity(i.putExtras(extras_add));
        });

        RecyclerView lstContacts = findViewById(R.id.lstContacts);
        adapter = new ContactsListAdapter(this, new ContactsListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent iMessages = new Intent(ChatActivity.this, MessagesActivity.class);
                Bundle extras = new Bundle();
                String current_contact = contact.getId();
                String contact_nick = contact.getName();

                String contact_server = contact.getServer();

                extras.putString("user", user);
                extras.putString("contact", current_contact);
                extras.putString("nickname", contact_nick);
                extras.putString("server", contact_server);
                startActivity(iMessages.putExtras(extras));
            }
        });
        Api api = new Api(server);
        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));

        if (viewModel == null || !user.equals(viewModel.getUser())) {
            viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ContactsViewModel.class);
            viewModel.init(this,user, server);
        }
        viewModel.get().observe(this, contacts -> {
            System.out.println(contacts.size());
            adapter.setContacts(contacts);
        });




        HashMap<String,String> dict = new HashMap<>();
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ChatActivity.this, instanceIdResult -> {
            String Token=instanceIdResult.getToken();
            dict.put("Username",user);
            dict.put("Token", Token);
            api.sendToken(dict);
        });


    }

    @Override
    protected void onResume(){
        super.onResume();
        this.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));
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
        viewModel.getListFromSource();
    }



    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mMessageReceiver);
    }


    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            viewModel.getListFromSource();
        }
    };
}
