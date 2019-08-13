package com.aniket.myofficeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                //SharedPreferences sp= getSharedPreferences("TAG", Context.MODE_PRIVATE);
                //String a=sp.getString("userid","0");
                if(GlobalVariables.mailid.equals("")){
                    Intent i = new Intent(SplashScreen.this, LoginScreen.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        },3000);
    }
}
