package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {

//    private AppDB db;
//    private PostDao postDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

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