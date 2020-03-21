package com.example.chatapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.chatapp.Post.post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    MaterialEditText Email,Password,Username;
    Button sign_up;
    FirebaseAuth auth;
    DatabaseReference reference;
    Spinner s;
    String dept;
    ProgressDialog pd;
    ImageView camera;
    Uri images_uri=null;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int IMAGE_CHOOSE_CODE = 1002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        camera=findViewById(R.id.imageView5);
        Username=findViewById(R.id.username);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        sign_up=findViewById(R.id.button);
        pd=new ProgressDialog(this);
        s=findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SignUpActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.department));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(myAdapter);
        auth=FirebaseAuth.getInstance();
        sign_up.setOnClickListener(new View.OnClickListener(){
           public void onClick(View view)
           {
            String text_username=Username.getText().toString();
            String text_email=Email.getText().toString();
            String text_password=Password.getText().toString();
            Toast.makeText(SignUpActivity.this,"Dept "+dept,Toast.LENGTH_LONG).show();
            String tt="Student";
            if(TextUtils.isEmpty(text_username)||TextUtils.isEmpty(text_email)||images_uri==null||TextUtils.isEmpty(text_password)){
                Toast.makeText(SignUpActivity.this,"All fields are required",Toast.LENGTH_SHORT).show();
               }
            else if(text_password.length()<6)Toast.makeText(SignUpActivity.this,"Password length is less than 6",Toast.LENGTH_SHORT).show();
            else signup(text_username,text_email,text_password,tt);
           }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        imagefrom();
                    }
                }
                else {
                    imagefrom();
                }
            }
        });
    }
    public void imagefrom(){
        Toast.makeText(SignUpActivity.this, "I have done my work111", Toast.LENGTH_LONG).show();
        String[] options={"Gallery","Camera"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SignUpActivity.this, "I have done my work", Toast.LENGTH_LONG).show();
                if(i==1)
                    takephoto();
                else
                    choosePhoto();

            }

        });
        builder.create().show();

    }
    public void choosePhoto(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_CHOOSE_CODE);

    }
    public void takephoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "FROM THE CAMERA");
        images_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, images_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Toast.makeText(SignUpActivity.this,"I am checking for code"+requestCode,Toast.LENGTH_LONG).show();
        if(resultCode==RESULT_OK){
            if (requestCode == IMAGE_CAPTURE_CODE) {

                camera.setImageURI(images_uri);
            }
            else if(requestCode == IMAGE_CHOOSE_CODE)
            {
                images_uri=data.getData();
                camera.setImageURI(images_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                                 hashMap.put("Department",dept);

                                hashMap.put("ImageURL",images_uri.toString());

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        dept=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
