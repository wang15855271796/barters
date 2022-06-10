package com.barter.hyl.app.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.ImageHolder;
import com.barter.hyl.app.model.PicVideoModel;
import com.barter.hyl.app.model.VideoHolder;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.io.File;
import java.util.List;

/**
 * 自定义布局,多个不同UI切换
 */
public class MultipleTypesAdapter extends BannerAdapter<PicVideoModel.DatasBean, RecyclerView.ViewHolder> {
    private Context context;
    private SparseArray<RecyclerView.ViewHolder> mVHMap = new SparseArray<>();

    public MultipleTypesAdapter(Context context, List<PicVideoModel.DatasBean> mDatas) {
        super(mDatas);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new ImageHolder(BannerUtils.getView(parent, R.layout.banner_image));
            case 2:
                return new VideoHolder(BannerUtils.getView(parent, R.layout.banner_video));
        }
        return new ImageHolder(BannerUtils.getView(parent, R.layout.banner_image));
    }

    @Override
    public int getItemViewType(int position) {
        //先取得真实的position,在获取实体
//        return getData(getRealPosition(position)).viewType;
        //直接获取真实的实体
        return getRealData(position).getType();
        //或者自己直接去操作集合
//        return mDatas.get(getRealPosition(position)).viewType;
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, PicVideoModel.DatasBean data, int position, int size) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case 1:
//                ImageHolder imageHolder = (ImageHolder) holder;
//                mVHMap.append(position,imageHolder);
//                imageHolder.imageView.setImageResource(data.imageRes);
                break;
            case 2:
//                VideoHolder videoHolder = (VideoHolder) holder;
//                mVHMap.append(position,videoHolder);
//                videoHolder.player.setUp(data.imageUrl, true, null);
//                videoHolder.player.getBackButton().setVisibility(View.GONE);
//                videoHolder.player.startPlayLogic();
                //增加封面
//                Glide.with(videoHolder.player.getContext())
//                        .setDefaultRequestOptions(
//                                new RequestOptions()
//                                        .frame(0)
//                                        .centerCrop())
//                        .load(data.imageUrl)
//                        .into(((VideoHolder) holder).player);
//                ImageView imageView = new ImageView(context);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setImageResource(R.drawable.image4);
//                File videoFile = new File(data.imageUrl);
//
//                Bitmap videoThumbnail = ThumbnailUtils.createVideoThumbnail(videoFile.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
//                Log.d("dsgfsdfesw.....",videoThumbnail+"ss");
//                videoHolder.player.setThumbImageView(imageView);
//                break;

        }
    }

    public SparseArray<RecyclerView.ViewHolder> getVHMap() {
        return mVHMap;
    }


}
