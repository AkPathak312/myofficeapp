package com.aniket.myofficeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void logout(View view) {

        Intent i=new Intent(SettingsActivity.this, LoginScreen.class);
        startActivity(i);
        finish();
    }

    public void settings(View view) {

        Toast.makeText(SettingsActivity.this,"Unavailable in Prototype",Toast.LENGTH_LONG).show();
    }

    public void goback(View view) {
        Intent i=new Intent(SettingsActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
