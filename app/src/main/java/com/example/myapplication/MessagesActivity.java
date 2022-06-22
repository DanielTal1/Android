package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapters.MessagesListAdapter;
import com.example.myapplication.api.Api;
import com.example.myapplication.entities.ContactWithMessages;
import com.example.myapplication.entities.Message;
import com.example.myapplication.viewmodels.ContactsViewModel;
import com.example.myapplication.viewmodels.MessagesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private int msgCount;
    private  Context context;
    private String user;
    private String contact;
    private MessagesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_messages);
        ImageView btnBackToContacts=findViewById(R.id.backToContacts2);
        btnBackToContacts.setOnClickListener(v-> finish());
        TextView tvCurrentContact = findViewById(R.id.tvCurrentContact);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        contact = extras.getString("contact");
        user = extras.getString("user");
        String server=extras.getString("server");
        String nickname = extras.getString("nickname");

        if (nickname != null) {
            tvCurrentContact.setText(nickname);
        }

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MessagesViewModel.class);
        viewModel.init(user,this);

        RecyclerView lstMessages = findViewById(R.id.lstMessages);
        final MessagesListAdapter adapter = new MessagesListAdapter(this);

        Api api = new Api();
        lstMessages.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        lstMessages.setLayoutManager(layoutManager);


        viewModel.getMessages().observe(this, contactWithMessages -> {
            for(ContactWithMessages c : contactWithMessages) {
                if(c.contact.getId().equals(contact)) {
                    adapter.setMessages(c.messageList);
                    msgCount = c.messageList.size();
                    break;
                }
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });

        Button sendBtn = findViewById(R.id.btnSend);
        sendBtn.setOnClickListener(v -> {
            EditText etMessage = findViewById(R.id.etMessage);

            Message message = new Message(0, etMessage.getText().toString(), "00:00", true,contact);
            api.postMessage(user, contact, message, response-> {
                if (response) {
                    api.transfer(user, contact,server, message, resp-> {
                        if (resp) {

                        }
                    });
                }
            });
            viewModel.getListFromSource();

            etMessage.setText("");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));
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