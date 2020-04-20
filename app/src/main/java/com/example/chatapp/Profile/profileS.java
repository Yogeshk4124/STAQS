package com.example.chatapp.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.MainActivity;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import util.BotNavHelper;

public class profileS extends AppCompatActivity {
    String email,fimage,fuser,femail,fdept,fpass,ftype;
    FirebaseAuth fa;
    TextView uname,uemail,Dept,type;
    ImageView im,edit1,edit2;
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

    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_sprofile);
        setupBottomNavigationView();
        getAddress();
        fa=FirebaseAuth.getInstance();
        FirebaseUser u=fa.getCurrentUser();
        String email=u.getEmail();
        final String id=u.getUid();
        Toast.makeText(profileS.this,"Updated"+id,Toast.LENGTH_LONG).show();
        Query Q= FirebaseDatabase.getInstance().getReference("Users");
        Q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fimage=dataSnapshot.child(id).child("ImageURL").getValue(String.class);
                fuser= dataSnapshot.child(id).child("username").getValue(String.class);
                femail=dataSnapshot.child(id).child("email").getValue(String.class);
                fdept=dataSnapshot.child(id).child("Department").getValue(String.class);
                fpass=dataSnapshot.child(id).child("password").getValue(String.class);
                ftype=dataSnapshot.child(id).child("Type").getValue(String.class);

                uemail.setText(femail);
                uname.setText(fuser);
                Dept.setText(fdept);
                type.setText(ftype);
                Picasso.get().load(fimage).into(im);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder update1=new AlertDialog.Builder(profileS.this);
                update1.setTitle("Update Email");
                final EditText input1=new EditText(profileS.this);
                input1.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                update1.setView(input1);
                update1.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HashMap<String,Object>update=new HashMap<>();
                        update.put("/"+id+"/email",input1.getText().toString());
                        FirebaseDatabase.getInstance().getReference("Users").updateChildren(update);
                    }
                });
                update1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                update1.show();
            }
        });
        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder update1=new AlertDialog.Builder(profileS.this);
                update1.setTitle("Update Password");
                final EditText input1=new EditText(profileS.this);
                input1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                update1.setView(input1);
                update1.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                            HashMap<String, Object> update = new HashMap<>();
                            update.put("/" + id + "/password", input1.getText().toString());
                            FirebaseDatabase.getInstance().getReference("Users").updateChildren(update);
                        }
                });
                update1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                update1.show();
            }
        });

    }
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx) findViewById(R.id.botn);
        BotNavHelper.setup(bottomNavigationViewEx);
        BotNavHelper.selection(profileS.this,bottomNavigationViewEx," ",email);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem MI=menu.getItem(4);
        MI.setChecked(true);
    }

    public void getAddress()
    {
        uname=findViewById(R.id.textView15);
        im=findViewById(R.id.imageView8);
        Dept=findViewById(R.id.textView18);
        uemail=findViewById(R.id.textView16);
        type=findViewById(R.id.textView19);
        edit1=findViewById(R.id.imageView10);
        edit2=findViewById(R.id.imageView11);
    }

}
