package com.example.chatapp.Chat;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    public void display(final Context c, final String S, final String r, final String M, final String rid, final String sname) {
        final RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) itemView.getTag();
        int position = viewHolder.getAdapterPosition();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v=viewHolder.itemView;
                TextView r=v.findViewById(R.id.textView3);
                Intent intent=new Intent(c, MessageActivity.class);
                intent.putExtra("rid",rid);
                intent.putExtra("rname",sname);
                intent.putExtra("receiveimg",M);
                intent.putExtra("sname",S);
                c.startActivity(intent);
            }
        });
// viewHolder.getItemId();
// viewHolder.getItemViewType();
// viewHolder.itemView;
//        Query q= FirebaseDatabase.getInstance().getReference("Users");
//        q.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(Objects.equals(dataSnapshot.child("id").getValue(String.class), r))
//                {
//                    name.setText(dataSnapshot.child("username").getValue(String.class));
//                    Toast.makeText(c,dataSnapshot.child("username").getValue(String.class),Toast.LENGTH_LONG);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        name = (itemView.findViewById(R.id.textView4));
        description = ( itemView.findViewById(R.id.textView3));
        im=itemView.findViewById(R.id.imageView);
        Toast.makeText(c,"username"+r,Toast.LENGTH_LONG).show();

        name.setText(r);
        description.setText(S);
        Picasso.get().load(M).into(im);
    }
}

