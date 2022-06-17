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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        ImageView btnBackToContacts=findViewById(R.id.backToContacts2);
        btnBackToContacts.setOnClickListener(v-> finish());
        TextView tvCurrentContact = findViewById(R.id.tvCurrentContact);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String contact = extras.getString("contact");
        String user = extras.getString("user");
        String server=extras.getString("server");
        String nickname = extras.getString("nickname");

        if (nickname != null) {
            tvCurrentContact.setText(nickname);
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
            api.postMessage(user, contact, message, response-> {
                if (response) {
                    api.transfer(user, contact,server, message, resp-> {
                        if (resp) {
                            ;
                        }
                    });
                    api.getMessages(user, contact, apiMessages-> {
                        adapter.setMessages(apiMessages);
                        msgCount = apiMessages.size();
                        lstMessages.scrollToPosition(msgCount - 1);
                    });
                }
            });

            etMessage.setText("");
        });
    }
}