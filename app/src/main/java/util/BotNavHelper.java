package util;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.chatapp.Chat.ChatA;
import com.example.chatapp.Home.Home;
import com.example.chatapp.R;
import com.example.chatapp.Post.post;
import com.example.chatapp.Profile.profileA;
import com.example.chatapp.Search.searchA;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BotNavHelper {
    public static void setup(BottomNavigationViewEx b) {
        b.enableAnimation(false);
        b.enableItemShiftingMode(false);
        b.enableShiftingMode(false);
        b.setTextVisibility(false);
    }
    public static void selection(final Context context ,BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.home:
                        Intent intent1=new Intent(context, Home.class);
                        context.startActivity(intent1);
                        break;
                    case R.id.post:
                        Intent intent2=new Intent(context, post.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.search:
                        Intent intent3=new Intent(context, searchA.class);
                        context.startActivity(intent3);
                        break;
                    case R.id.chat:
                        Intent intent4=new Intent(context, ChatA.class);
                        context.startActivity(intent4);
                        break;
                    case R.id.profile:
                        Intent intent5=new Intent(context, profileA.class);
                        context.startActivity(intent5);
                        break;
                }
                return false;
            }
        });

    }
}
