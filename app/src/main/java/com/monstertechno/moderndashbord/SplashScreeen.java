package com.monstertechno.moderndashbord;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreeen extends AppCompatActivity {

    private static int SPLASH_SCREEN=4000;
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView logo,slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
        setContentView(R.layout.activity_splash_screeen);

         image = findViewById(R.id.imageView);

        logo = findViewById(R.id.textView);

        slogan = findViewById(R.id.textView2);
        //Animations

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);

        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

//Set animation to elements

        image.setAnimation(topAnim);

        logo.setAnimation(bottomAnim);

        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable(){

            @Override

            public void run(){

                //Call next screen

                Intent intent=new Intent(SplashScreeen.this,Login.class);

                // Attach all the elements those you want to animate in design

                Pair[]pairs=new Pair[2];
                pairs[0]=new Pair<View, String>(image,"logo_image");
                pairs[1]=new Pair<View, String>(logo,"logo_text");

                //wrap the call in API level 21 or higher

                if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.LOLLIPOP)

                {

                    ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(SplashScreeen.this,pairs);

                    startActivity(intent,options.toBundle());

                }

            }

        },SPLASH_SCREEN);

    }
}