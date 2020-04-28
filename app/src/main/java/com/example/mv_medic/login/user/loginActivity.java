package com.example.mv_medic.login.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mv_medic.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

public class loginActivity extends AppCompatActivity {
    EditText editTextMobile, editText;
    TextView textView;
    DatabaseReference databaseReference;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students");


        editTextMobile = findViewById(R.id.editTextMobile);
        editText = findViewById(R.id.editText);

        textView = findViewById(R.id.textView4);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenti = new Intent(loginActivity.this, Register.class);
                startActivity(intenti);
            }
        });

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pwd = null;
                try {
                    pwd = editText.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logIn(editTextMobile.getText().toString(), pwd);


            }
        });
    }

    private void logIn(final String mphone, final String mpassword) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(mphone.replace(".", "_")).exists()) {


                    try {
                        Log.v("myapp", Security.decrypt(Objects.requireNonNull((dataSnapshot.child(mphone.replace(".", "_")).child("password")).getValue()).toString()));
                        Log.v("myappin", mpassword);
                        if (Security.decrypt(Objects.requireNonNull((dataSnapshot.child(mphone.replace(".", "_")).child("password")).getValue()).toString()).equals(mpassword)) {


                            String Phone = editTextMobile.getText().toString().trim();
                            Toast.makeText(loginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                            Intent intphto = new Intent(getApplicationContext(), otp.class);

                            intphto.putExtra("phone number", Phone);
                            System.out.println("Phone number" + Phone);
                            startActivity(intphto);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(loginActivity.this, "User is not register", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}