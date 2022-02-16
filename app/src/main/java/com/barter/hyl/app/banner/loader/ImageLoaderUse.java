package com.barter.hyl.app.banner.loader;

import android.content.Context;
import android.widget.ImageView;


public abstract class ImageLoaderUse implements ImageLoaderInterface<ImageView> {

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }

}
