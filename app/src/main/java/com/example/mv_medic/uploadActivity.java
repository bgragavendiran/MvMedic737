package com.example.mv_medic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class uploadActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final String IMAGE_DIRECTORY = "/medici";
    final Context context = this;
    Button upload, choose;
    ImageView img;
    TextView alert;
    String currentDateTimeString;
    FirebaseStorage storage;
    StorageReference storageReference;
    ArrayList<Uri> imageList = new ArrayList<Uri>();
    FirebaseUser user;
    String phone;
    private ProgressDialog mProgress;
    private ProgressDialog progressDialog;
    private int upload_count = 0;
    private Uri IndiviualImage;
    private FirebaseAuth mAuth;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        img = findViewById(R.id.uploadimg);
        alert = findViewById(R.id.alert);
        upload = findViewById(R.id.upload);
        choose = findViewById(R.id.chooser);
        currentDateTimeString = (String) android.text.format.DateFormat.format("yyyy-MM-dd hh:mm ", new Date());
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        phone = mAuth.getCurrentUser().getPhoneNumber();
        user = mAuth.getCurrentUser();
        checkPermission(Manifest.permission.CAMERA,
                CAMERA_PERMISSION_CODE);


        // mop= intent.getStringExtra("phone number");
        // final String number =intent.getStringExtra("phone number");

        System.out.println(currentDateTimeString);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Image Uploading Please Wait ..................");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("choose...");
                Button dialogButton = (Button) dialog.findViewById(R.id.daicamera);
                Button dialogButton1 = (Button) dialog.findViewById(R.id.daisingle);
                Button dialogButton2 = (Button) dialog.findViewById(R.id.multiple);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        camera();
                        dialog.dismiss();
                    }
                });
                dialogButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        single();
                        dialog.dismiss();
                    }
                });
                dialogButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        multiimage();
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (IndiviualImage != null) {
                    progressDialog.setTitle("Uploading........");

                    progressDialog.show();
                    //mop= intent.getStringExtra("phone number");


                    final StorageReference ref = storageReference.child("orders").child(phone).child(currentDateTimeString);
                    ref.putFile(IndiviualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = String.valueOf(uri);
                                    StoreLink(url);
                                }
                            });


                        }
                    })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "not uploaded", Toast.LENGTH_SHORT).show();
                }


            }


            //}
        });

    }

    private void multiimage() {
        Intent intent2 = new Intent();
        intent2.setType("image/*");
        intent2.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent2.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent2, 2);
    }


    private void single() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }


    private void camera() {


        Intent camera_intent
                = new Intent(MediaStore
                .ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, 0);
    }


    @SuppressLint("SetTextI18n")
    private void StoreLink(String url) {
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("orders").child(phone).child(currentDateTimeString);
        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("ImageLink", url);
        databaseReference1.push().setValue(hashMap1);

        progressDialog.dismiss();
        alert.setText("ImageUploaded Successfully ");
        upload.setVisibility(View.GONE);

        imageList.clear();


    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                if (data.getClipData() != null) {

                    int countclipdata = data.getClipData().getItemCount();
                    int currentimageSelect = 0;

                    while (currentimageSelect < countclipdata) {
                        IndiviualImage = data.getClipData().getItemAt(currentimageSelect).getUri();
                        imageList.add(IndiviualImage);


                        currentimageSelect += 1;

                    }
                    try {
                        Picasso.get().load(IndiviualImage).fit().centerCrop().into(img);

                        alert.setVisibility(View.VISIBLE);
                        alert.setText("you have selected" + imageList.size() + "Images");
                        choose.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            } else {
                Toast.makeText(this, "pls  select multiple files ", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            try {
                IndiviualImage = data.getData();
                Picasso.get().load(IndiviualImage).fit().centerCrop().into(img);

                alert.setVisibility(View.VISIBLE);
                alert.setText("you have selected The Images");
                choose.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "pls select files ", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == 0 && resultCode == RESULT_OK) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");


                IndiviualImage = getImageUri(getApplicationContext(), photo);
                Picasso.get().load(IndiviualImage).fit().centerCrop().into(img);
                choose.setVisibility(View.GONE);
            } catch (Exception ex) {
                Toast.makeText(this, "pls Select properly", Toast.LENGTH_SHORT).show();
                System.out.println("thskbgakjg" + ex);
            }

        }


    }

    private Uri getImageUri(Context applicationContext, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }


    public void returnback(View view) {
        Intent inte = new Intent(uploadActivity.this, Home_Activity.class);
        startActivity(inte);
    }


    public void logoutactivity(View view) {
        FirebaseAuth.getInstance()
                .signOut();
        startActivity(new Intent(uploadActivity.this, Home_Activity.class));
        finish();
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
        }

    }

}
