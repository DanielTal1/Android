package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.Api;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Api api=new Api();
        HashMap<String,String> dict = new HashMap<>();
        TextView txtHref=findViewById(R.id.textViewHref);
        txtHref.setOnClickListener(v->{
            Intent i = new Intent(this,Register.class);
            startActivity(i);
        });
        Button loginBtn= findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(v->{
            Intent iChat = new Intent(this, ChatActivity.class);
            TextView UsernameError=findViewById(R.id.login_UserNameError);
            TextView PasswordError=findViewById(R.id.login_PasswordError);
            UsernameError.setVisibility(View.GONE);
            PasswordError.setVisibility(View.GONE);
            EditText edUsername= findViewById(R.id.editTextTextPersonName);
            EditText edPassword= findViewById(R.id.editTextTextPassword);
            String Username=edUsername.getText().toString();
            String Password=edPassword.getText().toString();
            if(Username.equals("")||Password.equals("")) {
                if (Username.equals("")) {
                    UsernameError.setText("Username Required");
                    UsernameError.setVisibility(View.VISIBLE);
                }
                if (Password.equals("")) {
                    PasswordError.setText("Password Required");
                    PasswordError.setVisibility(View.VISIBLE);
                }
            }
            else{
                dict.put("Username",Username);
                dict.put("Password", Password);
                api.checkLogin(dict, user -> {
                    if(user!=null){
//                        Intent i=new Intent(this,Register.class);
//                        startActivity(i);
                        String current_user = Username;
                        startActivity(iChat.putExtra("user", current_user));
                    } else{
                        TextView LoginError=findViewById(R.id.login_PasswordError);
                        LoginError.setText("Invalid username or password");
                        LoginError.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

    }
}