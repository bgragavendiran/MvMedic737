package com.example.mv_medic.fragments;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mv_medic.Myorder;
import com.example.mv_medic.R;
import com.example.mv_medic.main.LoginClassifierActivity;
import com.example.mv_medic.mainacts.medical_tourism_Activity;
import com.example.mv_medic.uploadActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.google.firebase.inappmessaging.internal.Logging.TAG;

public class Homefragment extends Fragment {
    ViewFlipper viewFlipper;
    int[] imageos = {R.drawable.banner_1, R.drawable.banner_2};
    CardView cd, cd1;
    private ImageButton but3, order1, tourism1, need;
    private FirebaseAuth mAuth;

    @SuppressLint("SourceLockedOrientationActivity")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.home_activity, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewFlipper = (ViewFlipper) rootview.findViewById(R.id.fliper);
        for (int i = 0; i < imageos.length; i++) {
            flip_image(imageos[i]);
        }

        cd = rootview.findViewById(R.id.cardview23);
        cd1 = rootview.findViewById(R.id.cardned);

        mAuth = FirebaseAuth.getInstance();
        ImageView but2 = rootview.findViewById(R.id.image2);
        but3 = rootview.findViewById(R.id.image3);

        order1 = rootview.findViewById(R.id.order);
        tourism1 = rootview.findViewById(R.id.tourism);


        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {


                    Intent intent = new Intent(getContext(), LoginClassifierActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), Myorder.class);
                    startActivity(intent);

                }
            }
        });
        order1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {


                    Intent intent = new Intent(getContext(), LoginClassifierActivity.class);
                    startActivity(intent);
                } else {


                    Toast.makeText(getContext(), "Welcome " + user.getPhoneNumber(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), uploadActivity.class);
                    startActivity(intent);

                }


            }
        });
        tourism1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), medical_tourism_Activity.class);
                startActivity(intent);

            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String number = "tel:8778002472";

                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net");
                    startActivity(sendIntent);

                } catch (Exception e) {
                    Log.e(TAG, "ERROR_OPEN_MESSANGER" + e.toString());
                }
            }
        });
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                View mview = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                Button but_no = (Button) mview.findViewById(R.id.but_no);
                Button but_yes = (Button) mview.findViewById(R.id.but_yes);
                alert.setView(mview);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                but_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                but_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s = "877800247";

                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", s, null));
                        startActivity(intent);

                    }
                });
                alertDialog.show();

            }
        });


        return rootview;
    }

    private void flip_image(int i) {
        ImageView view = new ImageView(getContext());
        view.setBackgroundResource(i);
        viewFlipper.addView(view);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }

}


