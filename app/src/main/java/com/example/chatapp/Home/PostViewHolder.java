package com.example.chatapp.Home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Post.post;
import com.example.chatapp.R;
import com.squareup.picasso.Picasso;

public class PostViewHolder extends RecyclerView.ViewHolder {
    TextView username;
    TextView description;
    TextView title;
    ImageView im;
    ImageView pImage;
    View itemView;
    public PostViewHolder(@NonNull View iView) {
        super(iView);
        itemView=iView;
    }

    public void display(Context c, String p, String un, String ti, String pIm,String Desc) {

        username = (itemView.findViewById(R.id.textView6));
        description = ( itemView.findViewById(R.id.textView8));
        title = ( itemView.findViewById(R.id.textView10));
        im=itemView.findViewById(R.id.imageView7);
        pImage=itemView.findViewById(R.id.imageView4);


        username.setText(un);
        description.setText(Desc);
        Picasso.get().load(p).into(im);
        title.setText(ti);
        Picasso.get().load(pIm).into(pImage);
//        Toast.makeText(c,"Display p"+p,Toast.LENGTH_LONG).show();
    }
}
