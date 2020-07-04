package com.example.chatapp.model;

public class User {
    private String uuid;
    private String fullname;
    private String username;
    private String imageURL;

    public User(String uuid,String fullname, String username, String imageURL){
        this.uuid =uuid;
        this.username = username;
        this.fullname = fullname;
        this.imageURL = imageURL;
    }

    public User(){

    }

    public String getFullName() {
        return fullname;
    }

    public void setFullName(String userFullName) {
        this.fullname = userFullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
