package com.example.mv_medic.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mv_medic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Accountfragment extends Fragment {
    TextView e1, e2, e3, e4, e5, e6, e7, no;


    DatabaseReference databaseReference;
    LinearLayout r1;


    @SuppressLint("SourceLockedOrientationActivity")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.account_activity, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        e1 = rootview.findViewById(R.id.proname);
        r1 = rootview.findViewById(R.id.lin);
        e2 = rootview.findViewById(R.id.proemail);
        e3 = rootview.findViewById(R.id.proexp);
        e4 = rootview.findViewById(R.id.pronum);
        e5 = rootview.findViewById(R.id.progcity);
        e7 = rootview.findViewById(R.id.proadress);
        no = rootview.findViewById(R.id.nothing);
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


        return rootview;
    }
}
