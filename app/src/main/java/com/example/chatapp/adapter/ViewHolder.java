package com.example.chatapp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView username;
    public ImageView profile_image;

    public ViewHolder(View itemView) {
        super(itemView);

        username = itemView.findViewById(R.id.username);
        profile_image = itemView.findViewById(R.id.profile_image);
    }

}