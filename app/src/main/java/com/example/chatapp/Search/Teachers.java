package com.example.chatapp.Search;

public class Teachers {
    String T_uid, Subject, ImageURL, username,Type;

    public Teachers() {
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getT_uid() {
        return T_uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String n) {
        this.username = n;
    }


    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        this.Subject = subject;
    }

    public void setT_uid(String t_uid) {
        this.T_uid = t_uid;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}