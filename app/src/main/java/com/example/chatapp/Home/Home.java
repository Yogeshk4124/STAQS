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
import com.example.chatapp.Search.MyViewHolder;
import com.example.chatapp.Search.Teachers;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import util.BotNavHelper;

public class Home extends AppCompatActivity {
    TextView h;
    RecyclerView homerv;
    String im,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String m="Type",m2="image";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        h=findViewById(R.id.head);
        h.setText("Home");
        Intent intent = getIntent();
        String s=intent.getStringExtra(m);
        im=intent.getStringExtra(m2);
        email=intent.getStringExtra("email");
     //   Toast.makeText(Home.this,"Test"+im,Toast.LENGTH_LONG).show();
        setupBottomNavigationView();
        homerv=findViewById(R.id.homerv);
        homerv.setHasFixedSize(true);
        homerv.setLayoutManager(new LinearLayoutManager(this));
        showPost();
    }
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx) findViewById(R.id.botn);
        BotNavHelper.setup(bottomNavigationViewEx);
        BotNavHelper.selection(Home.this,bottomNavigationViewEx,im,email);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem MI=menu.getItem(0);
        MI.setChecked(true);
    }
    public void showPost(){
        Query Q=FirebaseDatabase.getInstance().getReference("Posts").orderByChild("pId");
        FirebaseRecyclerAdapter<Post, PostViewHolder> FRA=
                new FirebaseRecyclerAdapter<Post, PostViewHolder>(
                        Post.class,
                        R.layout.post_listview,
                        PostViewHolder.class,
                        Q){
                    @Override
                    protected void populateViewHolder(PostViewHolder myViewHolder, Post model, int i) {
                        Toast.makeText(Home.this,"dp"+model.getImageURL(),Toast.LENGTH_LONG).show();
                        myViewHolder.display(getApplicationContext(),model.getImageURL(),model.getUsername(),model.getpTitle(),model.getpImage(),model.getpDescription());
                    }
                };
        homerv.setAdapter(FRA);
    }
}