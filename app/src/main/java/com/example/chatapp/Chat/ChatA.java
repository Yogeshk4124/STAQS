package com.example.chatapp.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import util.BotNavHelper;

public class ChatA extends AppCompatActivity
{
    TextView h;
    String im,email;
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        im=intent.getStringExtra("image");
        email=intent.getStringExtra("email");
        h=findViewById(R.id.head);
        h.setText("Messages");
        setupBottomNavigationView();
    }
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=findViewById(R.id.botn);
        BotNavHelper.setup(bottomNavigationViewEx);
        BotNavHelper.selection(ChatA.this,bottomNavigationViewEx,im,email);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem MI=menu.getItem(3);
        MI.setChecked(true);
    }
}
