package com.example.chatapp.Search;

public class Teachers {
    String T_uid, Subject, image, name;

    public Teachers() {
    }

    public String getT_uid() {
        return T_uid;
    }

    public String getname() {
        return name;
    }

    public void setname(String n) {
        this.name = n;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}