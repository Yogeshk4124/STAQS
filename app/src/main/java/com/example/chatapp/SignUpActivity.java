package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    MaterialEditText Email,Password,Username;
    Button sign_up;
    RadioButton R1;
    FirebaseAuth auth;
    DatabaseReference reference;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Username=findViewById(R.id.username);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        sign_up=findViewById(R.id.button);
        rg=findViewById(R.id.rg);
        auth=FirebaseAuth.getInstance();
        sign_up.setOnClickListener(new View.OnClickListener(){
           public void onClick(View view)
           {
            String text_username=Username.getText().toString();
            String text_email=Email.getText().toString();
            String text_password=Password.getText().toString();
            R1=findViewById(rg.getCheckedRadioButtonId());
            String tt=R1.getText().toString();
            if(TextUtils.isEmpty(text_username)||TextUtils.isEmpty(text_email)||TextUtils.isEmpty(text_password)){
                Toast.makeText(SignUpActivity.this,"All fields are required",Toast.LENGTH_SHORT).show();}
            else if(text_password.length()<6)Toast.makeText(SignUpActivity.this,"Password length is less than 6",Toast.LENGTH_SHORT).show();
            else signup(text_username,text_email,text_password,tt);
           }
        });
    }
    private void signup(final String User,final String email,final String pass,final String type)
    {
            auth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                        public void onComplete(Task<AuthResult> task){
                            if(task.isSuccessful()){
                             FirebaseUser firebaseUser=auth.getCurrentUser();
                             assert firebaseUser!=null;
                             String userid=firebaseUser.getUid();

                             reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                              HashMap<String,String> hashMap=new HashMap<>();
                                 hashMap.put("id",userid);
                                 hashMap.put("username",User);
                                 hashMap.put("email",email);
                                 hashMap.put("password",pass);
                                 hashMap.put("Type",type);

                                hashMap.put("ImageURL","default");

                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                        }
                                    }
                                });
                                 }
                                        else{
                                            Toast.makeText(SignUpActivity.this,"You can't register with this email or password",Toast.LENGTH_SHORT).show();
                                        }
                            }

                    });
                    }
                }
