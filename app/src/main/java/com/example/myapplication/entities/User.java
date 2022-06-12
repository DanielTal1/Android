package com.example.myapplication.entities;

//@Entity
public class User {

    //@PrimaryKey(autoGenerate=false)
    private String id;
    private String userName;

    public void setId(String id) {
        this.id = id;
    }

    public void setUsernName(String usernName) {
        this.userName = usernName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private String password;
    private String nickname;


    public User(String id, String userName, String password, String nickname) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public String getUsernName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
