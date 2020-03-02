package com.example.chatapp.Search;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import util.BotNavHelper;

public class searchA extends AppCompatActivity {
    RecyclerView rv;
    Button search;
    EditText t;
    TextView h;
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        setupBottomNavigationView();
        rv=findViewById(R.id.rv1);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        h=findViewById(R.id.head);
        h.setText("Teacher List");
        search=findViewById(R.id.button3);
        t=findViewById(R.id.ST);
        firebaseSearch("");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String st=t.getText().toString();
                firebaseSearch(st);
            }
        });
    }
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=findViewById(R.id.botn);
        BotNavHelper.setup(bottomNavigationViewEx);
        BotNavHelper.selection(searchA.this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem MI=menu.getItem(1);
        MI.setChecked(true);
    }
    public void firebaseSearch(String s){
        Query Q=FirebaseDatabase.getInstance().getReference("TList").orderByChild("name").startAt(s).endAt(s+"\uf8ff");
        FirebaseRecyclerAdapter<Teachers, MyViewHolder>FRA=
                new FirebaseRecyclerAdapter<Teachers, MyViewHolder>(
                        Teachers.class,
                        R.layout.teacher_listview,
                        MyViewHolder.class,
                        Q){
                    @Override
                    protected void populateViewHolder(MyViewHolder myViewHolder, Teachers model, int i) {
                    myViewHolder.display(getApplicationContext(),model.getT_uid(),model.getname(),model.getSubject(),model.getImage());
                    }
                };
rv.setAdapter(FRA);
    }
}
