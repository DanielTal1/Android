package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.Api;

import java.util.HashMap;

public class AddContactActivity extends AppCompatActivity {

//    private AppDB db;
//    private PostDao postDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Intent intent = getIntent();
        String user = (String) intent.getSerializableExtra("user");
        ImageView btnBackToContacts=findViewById(R.id.backToContacts);
        btnBackToContacts.setOnClickListener(v-> finish());
        Button btnAddContact=findViewById(R.id.btnSave);
        btnAddContact.setOnClickListener(v->{
            TextView nameError=findViewById(R.id.AddContact_nameError);
            TextView nickNameError=findViewById(R.id.AddContact_NicknameError);
            TextView serverError=findViewById(R.id.AddContact_ServerError);
            nameError.setVisibility(View.GONE);
            nickNameError.setVisibility(View.GONE);
            serverError.setVisibility(View.GONE);
            EditText etName= findViewById(R.id.etNewContact);
            EditText etNickname= findViewById(R.id.etNickname);
            EditText etServer= findViewById(R.id.etServer);
            String name=etName.getText().toString();
            String nickname=etNickname.getText().toString();
            String server=etServer.getText().toString();
            boolean areErrors=false;
            if(name.equals("")){
                nameError.setText("Contact Name Required");
                nameError.setVisibility(View.VISIBLE);
                areErrors=true;
            }
            else if(name.equals(user)){
                nameError.setText("can't add yourself as contact");
                nameError.setVisibility(View.VISIBLE);
                areErrors=true;
            }
            if (nickname.equals("")) {
                nickNameError.setText("Nickname Required");
                nickNameError.setVisibility(View.VISIBLE);
                areErrors=true;
            }
            if (server.equals("")) {
                serverError.setText("server Required");
                serverError.setVisibility(View.VISIBLE);
                areErrors=true;
            }
            if(!areErrors){
                HashMap<String,String> dict = new HashMap<>();
                Api api=new Api();
                dict.put("from",user);
                dict.put("to", name);
                dict.put("server", server);
                api.inviteContact(dict, result -> {
                    if(result==0){
                        nameError.setText("There's no such user in "+ server);
                        nameError.setVisibility(View.VISIBLE);
                    } else if(result==1){
                        nameError.setText(name+" is already a contact");
                        nameError.setVisibility(View.VISIBLE);
                    } else if(result==3) {
                        serverError.setText("server was not found");
                        serverError.setVisibility(View.VISIBLE);
                    } else{
                        dict.clear();
                        dict.put("id",name);
                        dict.put("name", nickname);
                        dict.put("server", server);
                        dict.put("user",user);
                        api.addContact(dict,result2->{
                            if(result2==2) {
                                finish();
                                return;
                            }
                        });
                    }
                });


            }

        });
//        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "PostsDB")
//                .allowMainThreadQueries().build();
//
//        postDao = db.postDao();
//        String tempUser = "Rita";
//
//        Button btnSave = findViewById(R.id.btnSave);
//        btnSave.setOnClickListener(view -> {
//            EditText etNewContact = findViewById(R.id.etNewContact);
//            Contact post = new Contact(0, etNewContact.getText().toString(), tempUser);
//            postDao.insert(post);
//
//            finish();
//        });
    }

}