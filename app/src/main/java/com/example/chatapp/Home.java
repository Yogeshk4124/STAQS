package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String m="com.example.chatapp.message_key";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        String s=intent.getStringExtra(m);
        Toast.makeText(Home.this,s,Toast.LENGTH_LONG).show();
    }
}