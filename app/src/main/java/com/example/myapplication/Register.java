package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.api.Api;
import com.example.myapplication.entities.User;

public class Register extends AppCompatActivity {
    private ImageView image;
    public TextView UsernameError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String server = intent.getStringExtra("server");
        Api api=new Api(server);
        setContentView(R.layout.activity_register);
        TextView txtHref=findViewById(R.id.textViewHref);
        txtHref.setOnClickListener(v->{
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        });
        Button btnRegister=findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(view->{
            UsernameError=findViewById(R.id.Register_UserNameError);
            TextView NicknameError=findViewById(R.id.register_NicknameError);
            TextView repeatPasswordError=findViewById(R.id.Register_passwordRepeatError);
            TextView PasswordError=findViewById(R.id.Register_passwordError);
            UsernameError.setVisibility(View.GONE);
            NicknameError.setVisibility(View.GONE);
            repeatPasswordError.setVisibility(View.GONE);
            PasswordError.setVisibility(View.GONE);
            EditText edUsername= findViewById(R.id.Register_editTextTextPersonName);
            EditText edNickname= findViewById(R.id.Register_editTextNickName);
            EditText edPassword= findViewById(R.id.register_editTextTextPassword);
            EditText edRepeatPassword= findViewById(R.id.register_editTextTextPasswordRepeat);
            String Username=edUsername.getText().toString();
            String Password=edPassword.getText().toString();
            String Nickname=edNickname.getText().toString();
            String repeatPassword=edRepeatPassword.getText().toString();
            boolean areErrors=false;
            String regex ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}$";
            if (Username.equals("")) {
                UsernameError.setText("Username Required");
                UsernameError.setVisibility(View.VISIBLE);
                areErrors=true;
            }
            if (Password.equals("")) {
                PasswordError.setText("Password Required");
                PasswordError.setVisibility(View.VISIBLE);
                areErrors=true;
            }
            else if(!Password.matches(regex)){
                PasswordError.setText("password must be between 8 and 16 characters and contain at least one lowercase letter, one uppercase letter and one digit.");
                PasswordError.setVisibility(View.VISIBLE);
                areErrors=true;
            }
            if (Nickname.equals("")) {
                NicknameError.setText("Nickname Required");
                NicknameError.setVisibility(View.VISIBLE);
                areErrors=true;
            }
            if (repeatPassword.equals("")) {
                repeatPasswordError.setText("Repeated Password Required");
                repeatPasswordError.setVisibility(View.VISIBLE);
                areErrors=true;
            }
            else if(!repeatPassword.equals(Password)){
                repeatPasswordError.setText("repeated password must be identical to password");
                repeatPasswordError.setVisibility(View.VISIBLE);
                areErrors=true;
            }
            if(!areErrors){
                User newUser=new User(Username,Username,Password,Nickname);
                api.RegisterUser(newUser, success -> {
                    if(success){
                        Intent iChat = new Intent(this, ChatActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("server", server);
                        extras.putString("user", Username);
                        startActivity(iChat.putExtras(extras));
                    } else{
                        UsernameError.setText("Username taken");
                        UsernameError.setVisibility(View.VISIBLE);
                    }
                });
            }
        });


        Button btnImage=findViewById(R.id.btn_AddImage);
        btnImage.setOnClickListener(v->{
            Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imageActivityResultLauncher.launch(galleryIntent);
        });
    }
    ActivityResultLauncher<Intent> imageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if(data!=null&& data.getData()!=null){
                        image=findViewById(R.id.imageView3);
                        Uri selectedImage=data.getData();
                        image.setImageURI(selectedImage);
                    }
                }
            });

}