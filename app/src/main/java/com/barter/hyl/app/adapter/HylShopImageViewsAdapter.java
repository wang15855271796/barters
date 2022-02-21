package com.barter.hyl.app.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.barter.hyl.app.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
//import com.luck.picture.lib.tools.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/9/18
 */
public class HylShopImageViewsAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private List<String> list = new ArrayList<>();
    private int selectMax = 6;

    /**
     * 点击添加图片跳转
     */
    onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick(int pos);
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        if (list.size() < selectMax) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }


    public HylShopImageViewsAdapter(int layoutResId, @Nullable List<String> data, onAddPicClickListener mOnAddPicClickListener) {
        super(layoutResId, data);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
        this.list=data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        helper.setIsRecyclable(false);
        ImageView mImg = helper.getView(R.id.fiv);
        LinearLayout ll_del = helper.getView(R.id.ll_del);
        RelativeLayout rl_root = helper.getView(R.id.rl_root);
        //少于6张，显示继续添加的图标
        if (getItemViewType(helper.getAdapterPosition()) == TYPE_CAMERA) {
            mImg.setImageResource(R.mipmap.iv_post_camera_return);
            mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddPicClickListener.onAddPicClick(helper.getAdapterPosition());
                }
            });
            ll_del.setVisibility(View.INVISIBLE);
        } else {
            ll_del.setVisibility(View.VISIBLE);
            ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.deletPic(helper.getAdapterPosition());
                    int index = helper.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION) {
                        list.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, list.size());
//                        DebugUtil.i("delete position:", index + "--->remove after:" + list.size());
                    }
                }
            });
            String media = item;
//            int mimeType = media.getMimeType();
//            String path = "";
//            if (media.isCut() && !media.isCompressed()) {
//                // 裁剪过
//                path = media.getCutPath();
//            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
//                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
//                path = media.getCompressPath();
//            } else {
//                // 原图
//                path = media.getPath();
//            }
//            // 图片
//            if (media.isCompressed()) {
//                Log.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
//                Log.i("压缩地址::", media.getCompressPath());
//            }
//
//            Log.i("原图地址::", media.getPath());
//            if (media.isCut()) {
//                Log.i("裁剪地址::", media.getCutPath());
//            }
//            if (mimeType == PictureMimeType.ofAudio()) {
////                mImg.setImageResource(R.drawable.audio_placeholder);
//            } else {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(mContext)
                        .load(media)
                        .apply(options)
                        .into(mImg);
            }
            //itemView 的点击事件
            if (mItemClickListener != null) {
                rl_root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = helper.getAdapterPosition();
                        mItemClickListener.onItemClick(adapterPosition, v);
                    }
                });
            }
        }
//    }


    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
        void deletPic(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
