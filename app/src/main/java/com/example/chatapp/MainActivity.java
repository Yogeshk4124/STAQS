package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    TextView create_account;
    TextView Username,Password;
    Button login;
    FirebaseAuth auth;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.button2);
        Username=findViewById(R.id.username);
        Password=findViewById(R.id.password);
        create_account=findViewById(R.id.create_account);
        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_username=Username.getText().toString();
                String text_password=Password.getText().toString();

                if(TextUtils.isEmpty(text_username)||TextUtils.isEmpty(text_password)){
                    Toast.makeText(MainActivity.this,"All fields are required",Toast.LENGTH_SHORT).show();}
                else if(text_password.length()<6)Toast.makeText(MainActivity.this,"Password length is less than 6",Toast.LENGTH_SHORT).show();
                else{
                    auth.signInWithEmailAndPassword(text_username,text_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
//                            reff= FirebaseDatabase.getInstance().getReference().getKey().
                                Intent intent = new Intent(MainActivity.this,StudentM.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });


                }
            }
        });

        create_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
