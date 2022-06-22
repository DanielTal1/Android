package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.myapplication.api.Api;

import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ImageView ivSettings = findViewById(R.id.ivSettings);
        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        String server = sharedPreferences.getString(getString(R.string.changed_server), "text");
        HashMap<String,String> dict = new HashMap<>();
        TextView txtHref=findViewById(R.id.textViewHref);
        txtHref.setOnClickListener(v->{
            Intent i = new Intent(this,Register.class);
            startActivity(i.putExtra("server", server));
        });

        Api api=new Api(server);
//        TextView stam = findViewById(R.id.stam);
//        stam.setText(server);

        Button loginBtn= findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(v->{
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
                        Bundle extras = new Bundle();
                        extras.putString("server", server);
//                        extras.putString("server", api.getApi_server());
                        extras.putString("user", Username);
                        Intent iChat = new Intent(this, ChatActivity.class);
//                        String current_user = Username;
                        startActivity(iChat.putExtras(extras));
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