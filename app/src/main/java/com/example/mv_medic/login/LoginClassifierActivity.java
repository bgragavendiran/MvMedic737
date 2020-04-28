package com.example.mv_medic.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mv_medic.R;

public class LoginClassifierActivity extends AppCompatActivity {
    EditText uname, uphone, uadd, ename, eid, ephone, eadd;
    Button ulog, ilog;
    String un, up, ua, en, ei, ep, ea;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_classifier);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


    }

    public void ejump(View view) {
        Intent intent = new Intent(LoginClassifierActivity.this, com.example.mv_medic.login.enterprise.enterpriseLoginActivity.class);
        startActivity(intent);
    }

    public void ulog(View view) {
        Intent intent = new Intent(LoginClassifierActivity.this, com.example.mv_medic.login.user.loginActivity.class);
        startActivity(intent);
    }
}
