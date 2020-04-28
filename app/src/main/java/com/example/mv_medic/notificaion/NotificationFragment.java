package com.example.mv_medic.notificaion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mv_medic.R;
import com.example.mv_medic.payment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotificationFragment extends Fragment {
    public static final String TAG = "tag";
    RecyclerView mRecyclerView;
    ArrayList<DownModel> downModelArrayList = new ArrayList<>();
    MyAdapter myAdapter;
    DatabaseReference mref;
    TextView textView, textView1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notificationfragment, container, false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
        FirebaseUser user1 = mAuth1.getCurrentUser();
        final DatabaseReference datref = FirebaseDatabase.getInstance().getReference().child(user1.getUid());
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();
                    datref.child("token").child("id").setValue(token);
                    Log.d(TAG, "Token:" + token);
                } else {
                    Log.d(TAG, "Token generation failed");
                }
            }
        });


        mref = FirebaseDatabase.getInstance().getReference(user.getUid()).child("invoice");
        mRecyclerView = view.findViewById(R.id.recyclerview);
        textView = view.findViewById(R.id.nothing2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        if (downModelArrayList.size() > 0)
            downModelArrayList.clear();


        mref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {


                    DownModel downModel = dataSnapshot.getValue(DownModel.class);
                    downModelArrayList.add(downModel);

                    myAdapter = new MyAdapter(NotificationFragment.this, downModelArrayList);
                    mRecyclerView.setAdapter(myAdapter);
                } catch (Exception ex) {
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    textView.setText("nothing to show");
                    textView.setVisibility(View.VISIBLE);
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}