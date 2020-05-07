package com.example.mv_medic.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mv_medic.Home_Activity;
import com.example.mv_medic.R;
import com.example.mv_medic.uploadActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Accountfragment extends Fragment {
    EditText e1, e2, e3, e4, e5, e6, e7;
           TextView no;


    DatabaseReference databaseReference;
    Button b1;
    LinearLayout r1;
    ImageView im,im2;


    @SuppressLint("SourceLockedOrientationActivity")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.account_activity, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        e1 = rootview.findViewById(R.id.proname);
        r1 = rootview.findViewById(R.id.lin);
        e2 = rootview.findViewById(R.id.proemail);
        e3 = rootview.findViewById(R.id.proage);
        e4 = rootview.findViewById(R.id.pronum);
        e5 = rootview.findViewById(R.id.progcity);
        b1=rootview.findViewById(R.id.update);
        e7 = rootview.findViewById(R.id.proadress);
        no = rootview.findViewById(R.id.nothing);
        im=rootview.findViewById(R.id.back);
        im2=rootview.findViewById(R.id.logimg);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String phone = user.getPhoneNumber();
        phone = phone.replace("+91", "");
        System.out.println("something seems to be abnormal" + phone);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students").child(phone);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {


                    String name = dataSnapshot.child("name").getValue().toString();
                    String Phonenumber = dataSnapshot.child("PhoneNumber").getValue().toString();
                    String city = dataSnapshot.child("city").getValue().toString();
                    String dateofbirth = dataSnapshot.child("date of birth").getValue().toString();
                    String email = dataSnapshot.child("Email").getValue().toString();
                    String adress = dataSnapshot.child("Adress").getValue().toString();
                    e1.setText(name);
                    e2.setText(email);
                    e3.setText(dateofbirth);
                    e4.setText(Phonenumber);
                    e5.setText(city);
                    e7.setText(adress);
                } catch (Exception ex) {
                    no.setVisibility(View.VISIBLE);
                    r1.setVisibility(View.INVISIBLE);
                    no.setText("nothing to show");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=e1.getText().toString();
                String number=e4.getText().toString();
                String city=e5.getText().toString();
                String dob=e3.getText().toString();
                String email=e2.getText().toString();
                String add=e7.getText().toString();

                databaseReference.child("name").setValue(name);
                databaseReference.child("PhoneNumber").setValue(number);
                databaseReference.child("city").setValue(city);
                databaseReference.child("date of birth").setValue(dob);
                databaseReference.child("Email").setValue(email);
                databaseReference.child("Adress").setValue(add);

                Toast.makeText(getContext(),"updated Sucessfully",Toast.LENGTH_LONG).show();

            }
        });
       im.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent inte = new Intent(getContext(), Home_Activity.class);
               startActivity(inte);

           }
       });



       im2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FirebaseAuth.getInstance()
                       .signOut();
               startActivity(new Intent(getContext(), Home_Activity.class));

           }
       });



        return rootview;
    }

}
