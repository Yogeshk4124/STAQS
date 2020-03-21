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
    public static void selection(final Context context ,BottomNavigationViewEx view,final String im,final String email){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.home:
                        Intent intent1=new Intent(context, Home.class);
                        intent1.putExtra("image",im);
                        intent1.putExtra("email",email);
                        context.startActivity(intent1);
                        break;
                    case R.id.post:
                        Intent intent2=new Intent(context, post.class);
                        intent2.putExtra("image",im);
                        intent2.putExtra("email",email);
                        context.startActivity(intent2);
                        break;
                    case R.id.search:
                        Intent intent3=new Intent(context, searchA.class);
                        intent3.putExtra("image",im);
                        intent3.putExtra("email",email);
                        context.startActivity(intent3);
                        break;
                    case R.id.chat:
                        Intent intent4=new Intent(context, ChatA.class);
                        intent4.putExtra("image",im);
                        intent4.putExtra("email",email);
                        context.startActivity(intent4);
                        break;
                    case R.id.profile:
                        Intent intent5=new Intent(context, profileA.class);
                        intent5.putExtra("image",im);
                        intent5.putExtra("email",email);
                        context.startActivity(intent5);
                        break;
                }
                return false;
            }
        });

    }
}
