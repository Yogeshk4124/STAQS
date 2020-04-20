package com.example.chatapp.Chat;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.squareup.picasso.Picasso;

public class ChatviewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView description;
    ImageView im;
    View itemView;
    private View.OnClickListener onItemClickListener;
//    private OnItemClickListener mListener;
    public void setItemClickListener(View.OnClickListener clickListener) {
    onItemClickListener = clickListener;
    }
    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
//    public void setOnItemClickListener(OnItemClickListener listener){
//        mListener=listener;
//    }

    public ChatviewHolder(final View iView) {//,final OnItemClickListener listener
        super(iView);
        itemView=iView;
        itemView.setTag(this);
        itemView.setOnClickListener(onItemClickListener);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener != null) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        listener.OnItemClick(position);
//                    }
//                }
//            }
//        });
    }

    public void display(final Context c, String S, String r,final String M) {
        final RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) itemView.getTag();
        int position = viewHolder.getAdapterPosition();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v=viewHolder.itemView;
                TextView r=v.findViewById(R.id.textView3);
                Intent intent=new Intent(c, Message.class);
                intent.putExtra("receive",r.getText());
                intent.putExtra("receiveimg",M);
                c.startActivity(intent);
            }
        });
// viewHolder.getItemId();
// viewHolder.getItemViewType();
// viewHolder.itemView;
        name = (itemView.findViewById(R.id.textView4));
        description = ( itemView.findViewById(R.id.textView3));
        im=itemView.findViewById(R.id.imageView);
        name.setText(r);
        description.setText(S);
        Picasso.get().load(M).into(im);
    }
}

