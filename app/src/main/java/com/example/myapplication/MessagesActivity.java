package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.adapters.MessagesListAdapter;
import com.example.myapplication.api.Api;
import com.example.myapplication.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private int msgCount;

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
            msgCount = apiMessages.size();
        });

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        Button sendBtn = findViewById(R.id.btnSend);
        sendBtn.setOnClickListener(v -> {
            EditText etMessage = findViewById(R.id.etMessage);

            Message message = new Message(0, etMessage.getText().toString(), "00:00", true);
//            Message contact_msg = new Message(0, etMessage.getText().toString(), "00:00", false);
            api.postMessage(user, contact, message, response-> {
                if (response) {
                    api.getMessages(user, contact, apiMessages-> {
                        adapter.setMessages(apiMessages);
                        msgCount = apiMessages.size();
                    });
                }
            });
//            api.postMessage(contact, user, contact_msg, response-> {
//                if (response) {
//                    api.getMessages(user, contact, apiMessages-> {
//                        adapter.setMessages(apiMessages);
//                        msgCount = apiMessages.size();
//                    });
//                }
//            });
            etMessage.setText("");
        });
    }
}