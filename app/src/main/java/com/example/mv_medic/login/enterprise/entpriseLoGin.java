package com.example.mv_medic.login.enterprise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mv_medic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class entpriseLoGin extends AppCompatActivity {
    public Button bt;
    private EditText name, phone;
    private FirebaseAuth auth;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entprise_lo_gin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(entpriseLoGin.this, enterpriseActivity.class));
            finish();
        }

        name = findViewById(R.id.entname);
        phone = findViewById(R.id.entPhone);
        bt = findViewById(R.id.entlogin);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mame1 = name.getText().toString();
                final String pho = phone.getText().toString();
                if (TextUtils.isEmpty(mame1)) {
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pho)) {
                    Toast.makeText(getApplicationContext(), "Enter phone!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(mame1, pho).addOnCompleteListener(entpriseLoGin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(entpriseLoGin.this, enterpriseActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(entpriseLoGin.this, "incorrect credintals", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

    }
}
