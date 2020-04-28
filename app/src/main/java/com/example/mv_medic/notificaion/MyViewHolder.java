package com.example.mv_medic.notificaion;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mv_medic.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView mtitle;
    ImageView mimgurl;
    Button mDownload, mpay;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        mtitle = itemView.findViewById(R.id.textViewTitle);
        mimgurl = itemView.findViewById(R.id.imageView);
        mDownload = itemView.findViewById(R.id.downbut);
        mpay = itemView.findViewById(R.id.paybutt);


    }
}
