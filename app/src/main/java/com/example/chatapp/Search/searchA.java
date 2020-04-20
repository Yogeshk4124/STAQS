package com.example.chatapp.Search;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.MainActivity;
import com.example.chatapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import util.BotNavHelper;

public class searchA extends AppCompatActivity {
    RecyclerView rv;
    Button search;
    EditText t;
    TextView h;
    String im,email;
    MyViewHolder myViewHolder;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.topmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.about:
                Toast.makeText(this,"About",Toast.LENGTH_LONG).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }


        return super.onOptionsItemSelected(item);
    }
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        setupBottomNavigationView();
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TeacherList");
        Intent intent = getIntent();
        im=intent.getStringExtra("image");
        email=intent.getStringExtra("email");

        rv=findViewById(R.id.rv1);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
//        h=findViewById(R.id.head);
//        h.setText("Teacher List");
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
        BotNavHelper.selection(searchA.this,bottomNavigationViewEx,im,email);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem MI=menu.getItem(1);
        MI.setChecked(true);
    }
    public void firebaseSearch(final String s){
          Query Q=FirebaseDatabase.getInstance().getReference("TList").orderByChild("username").startAt(s).endAt(s+"\uf8ff");
        FirebaseRecyclerAdapter<Teachers, MyViewHolder>FRA=
                new FirebaseRecyclerAdapter<Teachers, MyViewHolder>(
                        Teachers.class,
                        R.layout.teacher_listview,
                        MyViewHolder.class,
                        Q){
                    @Override
                    protected void populateViewHolder(MyViewHolder myViewHolder, Teachers model, int i) {
                            myViewHolder.display(getApplicationContext(), model.getT_uid(), model.getUsername(), model.getSubject(), model.getImageURL());
                    }
                };
rv.setAdapter(FRA);

//        myViewHolder=new MyViewHolder(findViewById(R.id.rv1));
//        Q.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds: dataSnapshot.getChildren())
//                {
//                    String uname=ds.child("username").getValue(String.class);
//                    if(uname.startsWith(s)){
//                        myViewHolder.display(getApplicationContext(), ds.child("T_uid").getValue(String.class), ds.child("username").getValue(String.class),ds.child("Subject").getValue(String.class),ds.child("ImageURL").getValue(String.class));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
