package com.barter.hyl.app.banner;

import android.content.Context;
import android.widget.ImageView;

import com.barter.hyl.app.banner.loader.ImageLoaderUse;
import com.barter.hyl.app.view.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/12.
 */

public class GlideImageLoader extends ImageLoaderUse {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
//        Glide.with(context.getApplicationContext())
//                .load(path)
//                .into(imageView);

        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！ centerCrop()
         */
        RequestOptions myOptions;
        myOptions = new RequestOptions()
                .fitCenter()
                .transform(new GlideRoundTransform(context, 10));
        //Glide 加载图片简单用法
        Glide.with(context)
                .load(path)
                .apply(myOptions)
                .into(imageView);


    }

//    @Override
//    public ImageView createImageView(Context context) {
//        //圆角
//        return new RoundAngleImageView(context);
//    }
}
