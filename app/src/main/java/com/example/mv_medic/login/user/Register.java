package com.example.mv_medic.login.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mv_medic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Register extends AppCompatActivity {
    EditText tName, tEmail, tPassword, tConfirmPassword, tphonenumber, tadd, tcity, tdob;
    Button btnRegister;

    DatabaseReference databaseReference;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        result = (TextView) findViewById(R.id.textView2);
        tName = (EditText) findViewById(R.id.tName);
        tphonenumber = (EditText) findViewById(R.id.tph);
        tEmail = (EditText) findViewById(R.id.tEmail);
        tadd = (EditText) findViewById(R.id.taddres);

        tcity = (EditText) findViewById(R.id.tcity);

        tdob = (EditText) findViewById(R.id.dob);

        tPassword = (EditText) findViewById(R.id.tPassword);
        tConfirmPassword = (EditText) findViewById(R.id.tConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        databaseReference = FirebaseDatabase.getInstance().getReference("Students");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addArrayList();
                Intent intphto = new Intent(getApplicationContext(), loginActivity.class);
                startActivity(intphto);

            }
        });
    }

    private void addArrayList() {
        String email = tEmail.getText().toString().trim();
        String name = tName.getText().toString().trim();
        String phone = tphonenumber.getText().toString().trim();
        String address = tadd.getText().toString().trim();
        String city = tcity.getText().toString().trim();
        String datebirt = tdob.getText().toString().trim();


        String password = tPassword.getText().toString().trim();
        String comfirmpassword = tConfirmPassword.getText().toString().trim();
        String resulthash = result.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            tName.setError("Please enter your Name!");
        } else if (TextUtils.isEmpty(email)) {
            tEmail.setError("Please enter your Email!");
        } else if (TextUtils.isEmpty(address)) {
            tadd.setError("Please enter your address!");
        } else if (TextUtils.isEmpty(city)) {
            tcity.setError("Please enter your city!");
        } else if (TextUtils.isEmpty(datebirt)) {
            tdob.setError("Please enter your date of birth!");
        } else if (TextUtils.isEmpty(password)) {
            tPassword.setError("Please enter your Password!");
        } else if (!password.equals(comfirmpassword)) {
            tConfirmPassword.setError("Please put the same password");
        } else if (TextUtils.isEmpty(phone)) {
            tphonenumber.setError("Please enter the password");
        } else {
            Students students = new Students(name, email, phone, datebirt, city, address);
            databaseReference.child((phone.toString()).replace(".", "_"));

            databaseReference.child((phone.toString()).replace(".", "_")).child("name").setValue(name.toString());
            databaseReference.child((phone.toString()).replace(".", "_")).child("PhoneNumber").setValue(phone.toString());

            databaseReference.child((phone.toString()).replace(".", "_")).child("Email").setValue(email.toString());
            databaseReference.child((phone.toString()).replace(".", "_")).child("Adress").setValue(address.toString());
            databaseReference.child((phone.toString()).replace(".", "_")).child("city").setValue(city.toString());
            databaseReference.child((phone.toString()).replace(".", "_")).child("date of birth").setValue(datebirt.toString());


            try {
                databaseReference.child((phone.toString()).replace(".", "_")).child("password").setValue(Security.encrypt(password));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "Registered sucessfully", Toast.LENGTH_LONG).show();
            Cleartxt();

        }

    }

    private void Cleartxt() {
        tEmail.setText("");
        tName.setText("");
        tphonenumber.setText("");
        tadd.setText("");
        tcity.setText("");
        tdob.setText("");
        tPassword.setText("");
        tConfirmPassword.setText("");
        result.setText("");

    }

    public void computeMD5Hash(String password) {

        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }

            result.setText(MD5Hash);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}




