package com.example.chatapp.Search;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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

            name = (itemView.findViewById(R.id.textView4));
            description = ( itemView.findViewById(R.id.textView3));
            im=itemView.findViewById(R.id.imageView);
            name.setText(E);
            description.setText(n);
            Picasso.get().load(i).into(im);
        }
    }

