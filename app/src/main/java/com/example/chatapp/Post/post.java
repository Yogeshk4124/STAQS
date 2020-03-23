package com.example.chatapp.Post;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.Home.Home;
import com.example.chatapp.R;
import com.example.chatapp.Search.Teachers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import util.BotNavHelper;

public class post extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int IMAGE_CHOOSE_CODE = 1002;
    Button upload;
    ProgressDialog pd;
    TextView h;
    ImageView camera;
    Uri images_uri=null;
    static String im, email,uid,name,type,dp;
    EditText title, description;
    FirebaseAuth firebaseAuth;
    DatabaseReference drf,ref1;
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);
        h = findViewById(R.id.head);
        h.setText("Post");
        setupBottomNavigationView();
        camera = findViewById(R.id.imageView6);
        title=findViewById(R.id.editText3);
        description=findViewById(R.id.editText2);
        pd=new ProgressDialog(this);
        upload=findViewById(R.id.button5);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser u=firebaseAuth.getCurrentUser();
        email=u.getEmail();
        uid=u.getUid();



        drf=FirebaseDatabase.getInstance().getReference("Users");
        Query q=drf.orderByChild("email").equalTo(email);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    name=""+ds.child("username").getValue();
                    email=""+ds.child("email").getValue();
                    dp=""+ds.child("ImageURL").getValue();
                    type=""+ds.child("Type").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(images_uri==null)
                uploaddata("noImage");
            else
                uploaddata(String.valueOf(images_uri));

    }
        });
    }
    public void uploaddata(String uri) {
        Toast.makeText(post.this,"checking for this "+type,Toast.LENGTH_LONG).show();
        if (type.equals("Teacher")){
            Toast.makeText(post.this,"Here"+type,Toast.LENGTH_LONG).show();
            pd.setMessage("Uploading");
            pd.show();
            final String timespan=String.valueOf(System.currentTimeMillis());
            String address="Posts/"+"post_"+timespan;

            if(!uri.equals("noImage")){
                StorageReference ref=FirebaseStorage.getInstance().getReference().child(address);
                ref.putFile(Uri.parse(uri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        String Downloaduri=uriTask.getResult().toString();
//                    Toast.makeText(post.this,"dassssssssssssssssssssssss",Toast.LENGTH_LONG).show();
                        if(uriTask.isSuccessful())
                        {
                            HashMap<String,String>hm=new HashMap<>();
                            hm.put("id",uid);
                            hm.put("username",name);
                            hm.put("email",email);
                            hm.put("ImageURL",dp);
                            hm.put("pId",timespan);
                            hm.put("pTitle",title.getText().toString());
                            hm.put("pDescription",description.getText().toString());
                            hm.put("pImage",Downloaduri);
                            hm.put("pTime",timespan);

                            DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("Posts");

                            ref1.child(timespan).setValue(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(post.this,"Post Published",Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(post.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(post.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                Toast.makeText(post.this,"wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",Toast.LENGTH_LONG).show();

                HashMap<String,String>hm=new HashMap<>();
                hm.put("id",uid);
                hm.put("username",name);
                hm.put("email",email);
                hm.put("ImageURL",dp);
                hm.put("pId",timespan);
                hm.put("pTitle",title.getText().toString());
                hm.put("pDescription",description.getText().toString());
                hm.put("pImage","noImage");
                hm.put("pTime",timespan);
                Toast.makeText(post.this,"gere2",Toast.LENGTH_LONG).show();
                ref1=FirebaseDatabase.getInstance().getReference("Posts");
                ref1.child(timespan).setValue(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(post.this,"Post Published",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(post.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        else if(type.equals("Student"))
        {
            pd.setMessage("Uploading");
            pd.show();
            final String timespan=String.valueOf(System.currentTimeMillis());
            String address="Query/"+"query_"+timespan;

            if(!uri.equals("noImage")){
                StorageReference ref=FirebaseStorage.getInstance().getReference().child(address);
                ref.putFile(Uri.parse(uri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        String Downloaduri=uriTask.getResult().toString();
//                    Toast.makeText(post.this,"dassssssssssssssssssssssss",Toast.LENGTH_LONG).show();
                        if(uriTask.isSuccessful())
                        {
                            HashMap<String,String>hm=new HashMap<>();
                            hm.put("id",uid);
                            hm.put("username",name);
                            hm.put("email",email);
                            hm.put("ImageURL",dp);
                            hm.put("qId",timespan);
                            hm.put("qTitle",title.getText().toString());
                            hm.put("qDescription",description.getText().toString());
                            hm.put("qImage",Downloaduri);
                            hm.put("qTime",timespan);

                            DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("Query");

                            ref1.child(timespan).setValue(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(post.this,"Query Published",Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(post.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(post.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                Toast.makeText(post.this,"wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",Toast.LENGTH_LONG).show();

                HashMap<String,String>hm=new HashMap<>();
                hm.put("id",uid);
                hm.put("username",name);
                hm.put("email",email);
                hm.put("ImageURL",dp);
                hm.put("qId",timespan);
                hm.put("qTitle",title.getText().toString());
                hm.put("qDescription",description.getText().toString());
                hm.put("qImage","noImage");
                hm.put("qTime",timespan);

                ref1=FirebaseDatabase.getInstance().getReference("Query");
                ref1.child(timespan).setValue(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(post.this,"Query Published",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(post.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
public void imagefrom(){
        Toast.makeText(post.this, "I have done my work111", Toast.LENGTH_LONG).show();
        String[] options={"Gallery","Camera"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(post.this, "I have done my work", Toast.LENGTH_LONG).show();
            if(i==1)
                takephoto();
            else
                choosePhoto();

            }

        });
        builder.create().show();

    }

    private void setupBottomNavigationView() {
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.botn);
        BotNavHelper.setup(bottomNavigationViewEx);
        BotNavHelper.selection(post.this, bottomNavigationViewEx, im, email);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem MI = menu.getItem(2);
        MI.setChecked(true);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takephoto();
                } else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Toast.makeText(post.this,"I am checking for code"+requestCode,Toast.LENGTH_LONG).show();
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
}
