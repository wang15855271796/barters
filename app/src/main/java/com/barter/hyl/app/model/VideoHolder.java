package com.barter.hyl.app.model;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.view.MyControllView;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;


public class VideoHolder extends RecyclerView.ViewHolder {
    public StandardGSYVideoPlayer player;
    public VideoHolder(@NonNull View view) {
        super(view);
        player = view.findViewById(R.id.player);

    }
}
