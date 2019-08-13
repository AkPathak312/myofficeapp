package com.aniket.myofficeapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAppointments extends AppCompatActivity {
    String[] doa;
    String[] appto,cancelled,confirmed;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);
        listView=findViewById(R.id.applist);

        firebaseDatabase=FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference("appointments");
        final ProgressDialog progressDialog=new ProgressDialog(this);

        progressDialog.setTitle("Fetching Details...");
        progressDialog.setMessage("Please wait while we get your appointments.");
        progressDialog.show();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int j= (int) dataSnapshot.getChildrenCount();
                int i=0;
                doa=new String[j];
                appto=new String[j];
                cancelled=new String[j];
                confirmed=new String[j];
             //   mail=new String[j];
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                       doa[i] = ds.child("date").getValue() + "";
                       appto[i] = ds.child("officialid").getValue() + "";
                       cancelled[i] = ds.child("cancelled").getValue() + "";
                       confirmed[i] = ds.child("confirmed").getValue() + "";
                       i = i + 1;


                   // Log.d("TAG","in datasnapshot");
                }
                CustomAdapter customAdapter=new CustomAdapter();
                listView.setAdapter(customAdapter);
                progressDialog.hide();
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return doa.length;
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
            convertView=getLayoutInflater().inflate(R.layout.myappointmentrow,null);
            TextView appoiintmentdate=convertView.findViewById(R.id.dateofappointment);
            TextView appointedto=convertView.findViewById(R.id.appointedto);
            Button btncancel=convertView.findViewById(R.id.btncancel);
            Button btnconf=convertView.findViewById(R.id.btnconfstatus);
            appoiintmentdate.setText(doa[position]);
            appointedto.setText(appto[position]);
          //  Log.d("TAG","in adapter");
            if(confirmed[position].equals("1")){
                btnconf.setBackgroundColor(getResources().getColor(R.color.green));
                btnconf.setText("Confirmed");
            }
            if(cancelled[position].equals("1")){
             //  btnconf.setBackgroundColor(getResources().getColor(R.color.red));
                btncancel.setText("Cancelled");
                btnconf.setVisibility(View.INVISIBLE);
            }
            btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(MyAppointments.this);
                    builder.setMessage("Cancelling will cancel your appointmnt and is irreversible.").setTitle("Are you Sure?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                          //  String str=ref.child("appointment1").toString();
                        //    Toast.makeText(MyAppointments.this,str,Toast.LENGTH_LONG).show();

                        }
                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }
            });
            return convertView;
        }
    }
}
