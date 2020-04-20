package com.example.chatapp.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chatapp.Home.Home;
import com.example.chatapp.Home.Post;
import com.example.chatapp.Home.PostViewHolder;
import com.example.chatapp.MainActivity;
import com.example.chatapp.R;
import com.example.chatapp.Search.MyViewHolder;
import com.example.chatapp.Search.Teachers;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import util.BotNavHelper;

public class ChatlistActivity extends AppCompatActivity {
    String im,email,reciever,senderid;
    FirebaseUser fa;
    ChatviewHolder hl;
    DatabaseReference dref;
    RecyclerView homerv;

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
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                Toast.makeText(this,"about",Toast.LENGTH_LONG).show();
                break;
            default:return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);
        setupBottomNavigationView();
        fa=FirebaseAuth.getInstance().getCurrentUser();
        senderid=fa.getUid();
        //dref= FirebaseDatabase.getInstance().getReference("Chats");
        Toolbar toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ChatList");
        homerv=findViewById(R.id.homerv);
        homerv.setHasFixedSize(true);
        homerv.setLayoutManager(new LinearLayoutManager(this));
        Query Q=FirebaseDatabase.getInstance().getReference("Chats").orderByChild("sender").equalTo(senderid);

        FirebaseRecyclerAdapter<Chat, ChatviewHolder> FRA=
                        new FirebaseRecyclerAdapter<Chat, ChatviewHolder>(
                                Chat.class,
                                R.layout.teacher_listview,
                                ChatviewHolder.class,
                                Q){
                            @Override
                            protected void populateViewHolder(ChatviewHolder myViewHolder, Chat model, int i) {
                            myViewHolder.display(getApplicationContext(),model.getReceiver(),model.getSender(),model.getReceiverimg());
                            }
                        };
                homerv.setAdapter(FRA);

//        hl.setOnItemClickListener(new ChatviewHolder.OnItemClickListener() {
//            @Override
//            public void OnItemClick(int position) {
//                Toast.makeText(ChatlistActivity.this,"I am in list",Toast.LENGTH_LONG).show();
//            }
//        });
    }
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx) findViewById(R.id.botn);
        BotNavHelper.setup(bottomNavigationViewEx);
        BotNavHelper.selection(ChatlistActivity.this,bottomNavigationViewEx,im,email);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem MI=menu.getItem(0);
        MI.setChecked(true);
    }
}