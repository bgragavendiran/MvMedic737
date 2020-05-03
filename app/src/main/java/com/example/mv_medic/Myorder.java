package com.example.mv_medic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Myorder extends AppCompatActivity {
    TextView t1;
    private RecyclerView datalist;
    private DatabaseReference mdata;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        t1 = findViewById(R.id.nothing1);

        datalist = (RecyclerView) findViewById(R.id.myrecyclerview);
        datalist.setHasFixedSize(true);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        datalist.setLayoutManager(new LinearLayoutManager(this));
        mdata = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Myordersss");
        mdata.keepSynced(true);


    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        try {


            FirebaseRecyclerAdapter<data, dataViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<data, dataViewHolder>
                    (data.class, R.layout.data_row, dataViewHolder.class, mdata) {
                @Override
                protected void populateViewHolder(dataViewHolder viewHolder, data mo, int i) {
                    viewHolder.setName(mo.getPhonenumber());
                    viewHolder.setInvoiceno(mo.getPaymentId());
                    viewHolder.setPrice(mo.getPrice());
                    viewHolder.setDate(mo.getDate());

                }
            };
            datalist.setAdapter(firebaseRecyclerAdapter);
        } catch (Exception ex) {
            t1.setVisibility(View.VISIBLE);
            datalist.setVisibility(View.INVISIBLE);
            t1.setText("nothing to show");

        }
    }

    public static class dataViewHolder extends RecyclerView.ViewHolder {
        View view;

        public dataViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setName(String name2) {
            TextView name1 = (TextView) view.findViewById(R.id.name);
            name1.setText(name2);
        }

        public void setInvoiceno(String invoiceno1) {
            TextView inv = (TextView) view.findViewById(R.id.invno);
            inv.setText(invoiceno1);
        }

        public void setPrice(String price1) {
            TextView pr = (TextView) view.findViewById(R.id.price);
            pr.setText(price1);
        }

        public void setDate(String date1) {
            TextView da = (TextView) view.findViewById(R.id.date);
            da.setText(date1);
        }

    }
}
