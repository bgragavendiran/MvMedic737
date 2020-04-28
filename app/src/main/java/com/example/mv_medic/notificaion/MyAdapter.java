package com.example.mv_medic.notificaion;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mv_medic.R;
import com.example.mv_medic.payment;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.view.View.INVISIBLE;
import static java.security.AccessController.getContext;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    NotificationFragment mainActivity;
    Context context;
    ArrayList<DownModel> downModels;
    CardView cd;

    public MyAdapter(NotificationFragment mainActivity, ArrayList<DownModel> downModels) {
        this.mainActivity = mainActivity;
        this.context = mainActivity.getContext();
        this.downModels = downModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getContext());
        View view = layoutInflater.inflate(R.layout.pdfcardview, null, false);
        cd = view.findViewById(R.id.cardview20);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        try {


            myViewHolder.mtitle.setText(downModels.get(i).getTitle());
            myViewHolder.mDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadFile(myViewHolder.mtitle.getContext(), downModels.get(i).getTitle(), ".pdf", DIRECTORY_DOWNLOADS, downModels.get(i).getPdfurl());
                }
            });

            myViewHolder.mpay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, payment.class);
                    context.startActivity(intent);

                }
            });

        } catch (Exception exp) {

            cd.setVisibility(INVISIBLE);


        }


    }

    public void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadmanager.enqueue(request);
    }


    @Override
    public int getItemCount() {
        return downModels.size();
    }
}