package com.example.chatapp.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Home.Post;
import com.example.chatapp.Home.PostViewHolder;
import com.example.chatapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import util.BotNavHelper;

public class Message extends AppCompatActivity
{
CircularImageView civ;
ImageView send;
TextView username;
EditText msg;
FirebaseUser fa;
DatabaseReference dref;
String uname,im,email,receiver,receiverimg;
RecyclerView chatview;
List<Chat> mchat;
MessageViewHolder messageAdapter;
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        username=findViewById(R.id.textView17);
        civ=findViewById(R.id.circleid);
        msg=findViewById(R.id.message);
        chatview=findViewById(R.id.chatmsg);
        Intent intent=getIntent();
        receiverimg=intent.getStringExtra("receiveimg");
        receiver=intent.getStringExtra("receive");
        send=findViewById(R.id.mb);
        setupBottomNavigationView();
        chatview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        chatview.setLayoutManager(linearLayoutManager);
        fa=FirebaseAuth.getInstance().getCurrentUser();
        Query q=FirebaseDatabase.getInstance().getReference("Users").orderByChild("id").equalTo(receiver);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            uname=dataSnapshot.child(fa.getUid()).child("username").getValue(String.class);
//            im=dataSnapshot.child(fa.getUid()).child("ImageURL").getValue(String.class);
                Toast.makeText(Message.this,"here :"+receiver,Toast.LENGTH_LONG).show();
              uname=dataSnapshot.child(receiver).child("username").getValue(String.class);
              im=dataSnapshot.child(receiver).child("ImageURL").getValue(String.class);
            username.setText(uname);
            Picasso.get().load(im).into(civ);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
//        display();
    send.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dref=FirebaseDatabase.getInstance().getReference("Chats");
            HashMap<String,Object>hm=new HashMap<>();
            hm.put("sender",fa.getUid().toString());
            hm.put("receiver",receiver);
            hm.put("msg",msg.getText().toString());
            hm.put("receiverimg",receiverimg);
            msg.setText("");
            dref.push().setValue(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Message.this,"msg Published",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Message.this,e.getMessage(),Toast.LENGTH_LONG).show();
               }
            });
        }
    });
    read();
    }

//    private void display() {
//       dref=FirebaseDatabase.getInstance().getReference("Chats");
//        dref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String sender,receive,msg,receiverimg;
//                for(DataSnapshot ds: dataSnapshot.getChildren())
//                {
//                    sender=ds.child("sender").getValue(String.class);
//                    receive=ds.child("receiver").getValue(String.class);
//                    receiverimg=ds.child("receiverimg").getValue(String.class);
//                    msg=ds.child("msg").getValue(String.class);
//                    if(sender.equals(fa.getUid())&&receive.equals(receiver)){
//                        View v= LayoutInflater.from(parent.get)
//                        Toast.makeText(Message.this,"view Problem:"+v,Toast.LENGTH_LONG).show();
//                        MessageViewHolder mvh = new MessageViewHolder(v);
//                        mvh.displayl(getApplicationContext(),receiverimg, msg);
//                    }
//                    else if(sender.equals(receiver)&&receive.equals(fa.getUid()))
//                    {
//                        View v = findViewById(R.id.lmsg);
//                        //MessageViewHolder mvh = new MessageViewHolder(v);
//                        //mvh.displayr(getApplicationContext(), msg);
//                    }
//                    }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

   public void read()
    {
//       Query q=FirebaseDatabase.getInstance().getReference("Chats");
//       FirebaseRecyclerAdapter<Post, PostViewHolder> FRA=
//                new FirebaseRecyclerAdapter<Post, PostViewHolder>(
//                        Post.class,
//                        R.layout.post_listview,
//                        PostViewHolder.class,
//                        q){
//                    @Override
//                    protected void populateViewHolder(PostViewHolder myViewHolder, Post model, int i) {
//                        Toast.makeText(Message.this,"dp"+model.getImageURL(),Toast.LENGTH_LONG).show();
//                        myViewHolder.display(getApplicationContext(),model.getImageURL(),model.getUsername(),model.getpTitle(),model.getpImage(),model.getpDescription());
//                    }
//                };
//
//
        mchat = new ArrayList<>();

        dref = FirebaseDatabase.getInstance().getReference("Chats");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(fa.getUid()) && chat.getSender().equals(receiver) ||
                            chat.getReceiver().equals(receiver) && chat.getSender().equals(fa.getUid())){
                        mchat.add(chat);
                    }
                    messageAdapter = new MessageViewHolder(Message.this, mchat, chat.getReceiverimg());
                    chatview.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=findViewById(R.id.botn);
        BotNavHelper.setup(bottomNavigationViewEx);
        BotNavHelper.selection(Message.this,bottomNavigationViewEx,im,email);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem MI=menu.getItem(3);
        MI.setChecked(true);
    }
}
