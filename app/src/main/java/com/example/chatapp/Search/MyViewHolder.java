package com.example.chatapp.Search;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Chat.ChatlistActivity;
import com.example.chatapp.R;
import com.squareup.picasso.Picasso;

public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView description;
        ImageView im;
        View itemView;
        public MyViewHolder(final View iView) {
            super(iView);
            itemView=iView;
        }

        public void display(Context c, String E, String n, String s,String i) {
      //      Toast.makeText(c,"msg from:"+E,Toast.LENGTH_LONG).show();
            Toast.makeText(c,"view:"+itemView,Toast.LENGTH_LONG).show();
            name = (itemView.findViewById(R.id.textView4));
            description = ( itemView.findViewById(R.id.textView3));
            im=itemView.findViewById(R.id.imageView);
            name.setText(E);
            description.setText(n);
            Picasso.get().load(i).into(im);
        }
    }

