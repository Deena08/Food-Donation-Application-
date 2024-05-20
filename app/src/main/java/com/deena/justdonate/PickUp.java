package com.deena.justdonate;
import static com.deena.justdonate.R.layout.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
//import com.google.android.material.animation.AnimationUtils;

public class PickUp extends AppCompatActivity {

    private ImageView logo,logo1;
    private TextView title,slogan;
    Animation topAnimation,bAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pick_up);

        logo =findViewById(R.id.logo);
        title=findViewById(R.id.title);
        slogan=findViewById(R.id.slogan);
        //  logo1=findViewById(R.id.logo1);

      //  topAnimation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bAnimation= AnimationUtils.loadAnimation(this,R.anim.b_animation);
        //  topAnimation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        //  logo1.setAnimation(topAnimation);
        logo.setAnimation(bAnimation);
     //   title.setAnimation(bottomAnimation);
     //   slogan.setAnimation(bottomAnimation);

        int SPLASH_SCREEN=3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(PickUp.this,History.class);
                Toast.makeText(PickUp.this, "Your Doantion added to History",
                        Toast.LENGTH_LONG).show();
                startActivity(intent);

                finish();
            }
        },SPLASH_SCREEN);
    }
}