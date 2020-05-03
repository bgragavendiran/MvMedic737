package com.example.mv_medic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mv_medic.fragments.Accountfragment;
import com.example.mv_medic.fragments.Articlefragment;
import com.example.mv_medic.fragments.Homefragment;
import com.example.mv_medic.main.LoginClassifierActivity;
import com.example.mv_medic.notificaion.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home_Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private FirebaseAuth mAuth;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(this);
        LoadFragment(new Homefragment());

    }

    private boolean LoadFragment(Fragment fragment) {
        if (fragment != null) {
            checkPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    STORAGE_PERMISSION_CODE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {


            case R.id.navigation_home:
                fragment = new Homefragment();
                break;


            case R.id.navigation_notifications:
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser3 = mAuth.getCurrentUser();
                if (currentUser3 == null) {
                    Toast.makeText(Home_Activity.this, "Login to continue", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Home_Activity.this, LoginClassifierActivity.class);
                    startActivity(intent);
                } else {

                    fragment = new NotificationFragment();
                }
                break;
            case R.id.navigation_article:
                fragment = new Articlefragment();
                break;


            case R.id.navigation_account:
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                //  String ui=currentUser.getUid();
                if (currentUser == null) {
                    Toast.makeText(Home_Activity.this, "Login to continue", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Home_Activity.this, LoginClassifierActivity.class);
                    startActivity(intent);
                } else {

                    fragment = new Accountfragment();

                }

                break;

        }
        return LoadFragment(fragment);
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
        } else {

        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

}
