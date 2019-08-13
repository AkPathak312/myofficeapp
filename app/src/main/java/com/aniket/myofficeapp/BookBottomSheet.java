package com.aniket.myofficeapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.GmsLogger;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookBottomSheet extends BottomSheetDialogFragment {
    private BottomSheetListener bottomSheetListener;
    static TextView dateview,edtname,edtdesc;
    private DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bookbottomsheet,container,false);
        Button pickdate=view.findViewById(R.id.datebtn);
        Button confirm=view.findViewById(R.id.btnconfirm);
        dateview=view.findViewById(R.id.txtdateshow);
        edtname=view.findViewById(R.id.txtUserName);
        reference=FirebaseDatabase.getInstance().getReference("appointments");
        edtdesc=view.findViewById(R.id.txtdescription);
        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetListener.onButtonClicked("Date");
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     ProgressDialog progressDialog=new ProgressDialog();

                if(dateview.getText().equals(null)||edtname.getText().equals(null)||edtdesc.getText().equals(null)){
                    dismiss();
                    return;
                }

            String date=dateview.getText().toString();
            String can="0";
            String conf="0";
            String name=edtname.getText().toString();
            String reason=edtdesc.getText().toString();
            String mail=GlobalVariables.mailid;
            String officialid= GlobalVariables.id;
            BookAppointmentModel bookAppointmentModel=new BookAppointmentModel(name,can,conf,date,officialid,reason,mail);
            reference.child(name+officialid+date).setValue(bookAppointmentModel);
            dismiss();

            }
        });
        return view;
    }

    public  interface BottomSheetListener{
        void onButtonClicked(String text);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bottomSheetListener= (BottomSheetListener) context;
    }
}
