package com.aniket.myofficeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OfficialAvailability extends AppCompatActivity {

    String number[],desg[],isavailable[],official[],appointmenthours[];

    FirebaseDatabase database;
    DatabaseReference ref;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_availability);
        listView=findViewById(R.id.listview);

        number=new String[100];
        database=FirebaseDatabase.getInstance();
        ref=database.getReference("officials");
        final ProgressDialog progressDialog=new ProgressDialog(this);

        progressDialog.setTitle("Fetching Details...");
        progressDialog.setMessage("Please wait while we get official details.");
        progressDialog.show();


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int i=0;
                int j=(int)dataSnapshot.getChildrenCount();
                number=new String[j];
                official=new String[j];
                appointmenthours=new String[j];
                desg=new String[j];
                isavailable=new String[j];
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                  //  Log.v("Aniket",ds.child("name").getValue()+"");
                    number[i]=ds.child("name").getValue()+"";
                    official[i]=ds.child("officialid").getValue()+"";
                    desg[i]=ds.child("designation").getValue()+"";
                    isavailable[i]=ds.child("Available").getValue()+"";
                    appointmenthours[i]=ds.child("apphours").getValue()+"";
                  //  Log.v("Aniket",number[i]);
                    i=i+1;
                }
                CustomAdapter adapter=new CustomAdapter();
                listView.setAdapter(adapter);
                progressDialog.hide();
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return number.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater().inflate(R.layout.officialavailabilityrow,null);
            TextView officialname=convertView.findViewById(R.id.servicename);
            TextView officialid=convertView.findViewById(R.id.xd);
            TextView designation =convertView.findViewById(R.id.status);
            Button btn=convertView.findViewById(R.id.btn);
            TextView apphours=convertView.findViewById(R.id.date);
            officialid.setText(official[position]);
            designation.setText(desg[position]);
            apphours.setText(appointmenthours[position]+"");
            if(isavailable[position].equals("0")){
                btn.setText("Unavailable");
                btn.setBackgroundColor(getResources().getColor(R.color.red));
            }
            officialname.setText(number[position]);
            return convertView;
        }
    }

}
