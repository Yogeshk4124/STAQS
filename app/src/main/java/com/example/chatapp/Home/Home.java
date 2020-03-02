package com.example.chatapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import util.BotNavHelper;

public class Home extends AppCompatActivity {
    TextView h;
    RecyclerView homerv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String m="com.example.chatapp.message_key";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        h=findViewById(R.id.head);
        h.setText("Home");
        Intent intent = getIntent();
        String s=intent.getStringExtra(m);
        Toast.makeText(Home.this,s,Toast.LENGTH_LONG).show();
        setupBottomNavigationView();
        homerv=findViewById(R.id.homerv);
        homerv.setHasFixedSize(true);
        homerv.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx) findViewById(R.id.botn);
        BotNavHelper.setup(bottomNavigationViewEx);
        BotNavHelper.selection(Home.this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem MI=menu.getItem(0);
        MI.setChecked(true);
    }
}