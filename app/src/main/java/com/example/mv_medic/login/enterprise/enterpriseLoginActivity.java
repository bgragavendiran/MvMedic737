package com.example.mv_medic.login.enterprise;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mv_medic.R;

import com.example.mv_medic.main.LoginClassifierActivity;
import com.example.mv_medic.main.user.login2Activity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class enterpriseLoginActivity extends AppCompatActivity {
    TextView es;
    EditText ename, eid, email, eadd, ephno;
    Button ej;
    ListView ListUser;
    DatabaseReference databaseReference;
    TextView Tx;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_enterprise_login);
        ename = (EditText) findViewById(R.id.ename);
        eid = (EditText) findViewById(R.id.eid);

        email = (EditText) findViewById(R.id.eemail);
        Tx = findViewById(R.id.entloginText);
        eadd = (EditText) findViewById(R.id.eadd);
        ephno = (EditText) findViewById(R.id.ephno);
        ej = (Button) findViewById(R.id.signup);
        databaseReference = FirebaseDatabase.getInstance().getReference("enterpriseStudent");

        ej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addArrayList();
                Intent intphto = new Intent(getApplicationContext(), login2Activity.class);
                startActivity(intphto);

            }
        });
        Tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LoginClassifierActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void addArrayList() {

        String name = ename.getText().toString().trim();
        String id = eid.getText().toString().trim();
        String email1 = email.getText().toString().trim();
        String add = eadd.getText().toString().trim();
        String phone = ephno.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ename.setError("Please enter your Name!");
        } else if (TextUtils.isEmpty(phone)) {
            ephno.setError("Please enter your phone number!");
        } else if (TextUtils.isEmpty(add)) {
            eadd.setError("Please enter your Password!");
        } else if (TextUtils.isEmpty(email1)) {
            email.setError("Please put the same password");
        } else if (TextUtils.isEmpty(id)) {
            eid.setError("enter the correct id");
        } else {

            //String id=  databaseReference.push().getKey();
            enterpriseStudent students = new enterpriseStudent(name, phone, id, email1, add);
            databaseReference.child((phone.toString()).replace(".", "_"));
            databaseReference.child((phone.toString()).replace(".", "_")).child("name").setValue(name.toString());
            databaseReference.child((phone.toString()).replace(".", "_")).child("id").setValue(id.toString());
            databaseReference.child((phone.toString()).replace(".", "_")).child("phone").setValue(phone.toString());
            databaseReference.child((phone.toString()).replace(".", "_")).child("add").setValue(add.toString());
            databaseReference.child((phone.toString()).replace(".", "_")).child("email").setValue(email1.toString());


            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
            Cleartxt();

        }

    }

    private void Cleartxt() {
        ename.setText("");
        eid.setText("");
        email.setText("");
        eadd.setText("");
        ephno.setText("");

    }


}
