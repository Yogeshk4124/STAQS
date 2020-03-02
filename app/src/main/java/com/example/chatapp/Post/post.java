package com.example.chatapp.Post;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CaptureRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import util.BotNavHelper;

public class post extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    TextView h;
    ImageView camera,capture;
    Uri images_uri;
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);
        h=findViewById(R.id.head);
        h.setText("Post");
        setupBottomNavigationView();
        camera=findViewById(R.id.imageView6);
        capture=findViewById(R.id.imageView7);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String[] permission={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else
                        takephoto();
                }
                else
                    takephoto();
            }
        });
    }
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx) findViewById(R.id.botn);
        BotNavHelper.setup(bottomNavigationViewEx);
        BotNavHelper.selection(post.this,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem MI=menu.getItem(2);
        MI.setChecked(true);
    }
public void takephoto(){
ContentValues values=new ContentValues();
values.put(MediaStore.Images.Media.TITLE,"New Picture");
values.put(MediaStore.Images.Media.TITLE,"FROM THE CAMERA");
images_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, images_uri);
startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch(requestCode){
        case PERMISSION_CODE:{
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                takephoto();
            }
            else
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
        }

    }

}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK){
            camera.setImageURI(images_uri);
        }
    }
}
