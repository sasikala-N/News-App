package com.example.news.WellcomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.news.MainActivity;
import com.example.news.R;
import com.example.news.RegistrationAndLogin.MyLoginActivity;
import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        auth= FirebaseAuth.getInstance();
        PreferenceManager preferenceManager=new PreferenceManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(preferenceManager.isFirstTime()){
                    preferenceManager.setFirstLunch(false);
                    startActivity(new Intent(getApplicationContext(), WellcomeActivity.class));
                }else {
                    if (auth.getCurrentUser() != null) {
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(SplashScreen.this,MyLoginActivity.class));
                    }
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }
        },2000);

    }
}