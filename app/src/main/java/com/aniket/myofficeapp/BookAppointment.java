package com.aniket.myofficeapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class BookAppointment extends AppCompatActivity implements BookBottomSheet.BottomSheetListener, DatePickerDialog.OnDateSetListener {

    String number[],desg[],isavailable[],official[],appointmenthours[];

    FirebaseDatabase database;
    DatabaseReference ref;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        listView=findViewById(R.id.listview2);

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
                    number[i]=ds.child("name").getValue()+"";
                    official[i]=ds.child("officialid").getValue()+"";
                    desg[i]=ds.child("designation").getValue()+"";
                    isavailable[i]=ds.child("Available").getValue()+"";
                    appointmenthours[i]=ds.child("apphours").getValue()+"";
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

    @Override
    public void onButtonClicked(String text) {

        if(text.equals("Date")) {
            DialogFragment dialogFragment = new DatePickerFragment();
            dialogFragment.show(getSupportFragmentManager(), "date picker");
        }
        if(text.equals("Confirm")){



        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String selecteddate= DateFormat.getDateInstance().format(c.getTime());
        BookBottomSheet.dateview.setText(selecteddate);
    }

    class CustomAdapter extends BaseAdapter {

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater().inflate(R.layout.officialavailabilityrow,null);
            TextView officialname=convertView.findViewById(R.id.servicename);
            TextView officialid=convertView.findViewById(R.id.xd);
            TextView designation =convertView.findViewById(R.id.status);
            Button btn=convertView.findViewById(R.id.btn);
            TextView apphours=convertView.findViewById(R.id.date);
            officialid.setText(official[position]);
            designation.setText(desg[position]);
            apphours.setText(appointmenthours[position]+"");
            officialname.setText(number[position]);
            btn.setText("Schedule Appointment");
            btn.setBackgroundColor(getResources().getColor(R.color.buttonbackground));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    GlobalVariables.id=official[position];
                    BookBottomSheet bookBottomSheet=new BookBottomSheet();
                    bookBottomSheet.show(getSupportFragmentManager(),"example");
                }
            });
            return convertView;
        }
    }
}
