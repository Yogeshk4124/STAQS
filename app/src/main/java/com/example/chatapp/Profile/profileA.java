package com.example.chatapp.Profile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import util.BotNavHelper;

public class profileA extends AppCompatActivity {
    TextView h;
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        h=findViewById(R.id.head);
        h.setText("Profile");
        setupBottomNavigationView();
    }
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx) findViewById(R.id.botn);
        BotNavHelper.setup(bottomNavigationViewEx);
        BotNavHelper.selection(profileA.this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem MI=menu.getItem(4);
        MI.setChecked(true);
    }

}
