package com.aniket.myofficeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView loggedin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loggedin=findViewById(R.id.textView4);
        loggedin.setText(GlobalVariables.mailid);
    }


    public void officialview(View view) {

        Intent i= new Intent(MainActivity.this,OfficialAvailability.class);
        startActivity(i);
    }

    public void bookappointment(View view) {

        Intent i=new Intent(MainActivity.this,BookAppointment.class);
        startActivity(i);
    }

    public void myappointment(View view) {

        Intent i=new Intent(MainActivity.this,MyAppointments.class);
        startActivity(i);
    }

    public void gotosettings(View view) {
        Intent i=new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(i);
    }

    public void gottoterms(View view) {

        Intent i=new Intent(MainActivity.this,TermsofService.class);
        startActivity(i);
    }
}
