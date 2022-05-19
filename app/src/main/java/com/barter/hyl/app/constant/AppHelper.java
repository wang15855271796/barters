package com.barter.hyl.app.constant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.ShopImageViewAdapter;
import com.barter.hyl.app.event.DeletePicEvent;
import com.barter.hyl.app.view.FingerFrameLayout;
import com.barter.hyl.app.view.PhotoViewAdapter;
import com.barter.hyl.app.view.PhotoViewPager;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

//import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by GuoGai on 2016/7/26.
 */
public class AppHelper {
    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        String packageName;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            packageName = packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("getPackageName", e.toString());
            return "com.puyue.www.qiaoge";
        }
        return packageName;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        String version;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (Exception e) {
            Log.e("getVersion", e.toString());
            return "1.0.0";
        }
        return version;
    }



    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }

    /**
     * 检测某ActivityUpdate是否在当前Task的栈顶
     *
     * @param context
     * @return
     */
//    public static boolean isTopActivy(Context context) {
//        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(Integer.MAX_VALUE);
//        String cmpNameTemp = null;
//        if (null != runningTaskInfos) {
//            cmpNameTemp = (runningTaskInfos.get(0).topActivity).toString();
//        }
//
//        if (null == cmpNameTemp) {
//            return false;
//        }
//
//        return cmpNameTemp.equals(AppHelper.getPackageName(context));
//
//    }

    public static String AuthorizationCode;
    public static AlertDialog mDialogAuth;
    /**
     * 显示授权弹窗
     */



    public static void hideAuthorizationDialog() {
        mDialogAuth.dismiss();
    }

    /**
     * 获取授权码
     */
    public static String getAuthorizationCode() {
        return AuthorizationCode;
    }

    public static void setAuthorizationCode(String authorizationCode) {
        AuthorizationCode = authorizationCode;
    }

    /**
     * 平板返回 True，手机返回 False
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * //在不加载图片情况下获取图片大小
     */
    public static int[] getImageWidthHeight(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth, options.outHeight};
    }

    public static Dialog dialog;
    public static View dialogView;
    public static boolean isShow = false;

    /**
     * 查看大图
     */
    public static void showPhotoDetailDialog(Context mContext, final List<String> mListUrl, int position) {
        dialog = new Dialog(mContext, R.style.Theme_Light_Dialog);
        dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_show_photo_hyl, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
       // window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        final TextView mTv = dialog.findViewById(R.id.tv_dialog_photo);
        PhotoViewPager mVp = dialog.findViewById(R.id.vp_dialog_photo);
        FingerFrameLayout mFl = dialog.findViewById(R.id.ffl_dialog_photo);
        mFl.setOnAlphaChangeListener(new FingerFrameLayout.onAlphaChangedListener() {
            @Override
            public void onAlphaChanged(float alpha) {
                Log.e("fengan", "[onAlphaChanged]:alpha=" + alpha);
            }

            @Override
            public void onTranslationYChanged(float translationY) {
                Log.e("fengan", "[onTranslationYChanged]:translationY=" + translationY);
            }

            @Override
            public void onFinishAction() {
                hidePhotoDetailDialog();
            }
        });
        PhotoViewAdapter photoViewAdapter = new PhotoViewAdapter(mListUrl, mContext);
        mVp.setAdapter(photoViewAdapter);
        mVp.setCurrentItem(position);
        mTv.setText(position  + 1+"/" + mListUrl.size());
        photoViewAdapter.setPhotoListener(new PhotoViewAdapter.OnPhotoListener() {
            @Override
            public void onPhotoListenter() {
                if (dialog!=null){
                    dialog.dismiss();
                }
            }
        });

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTv.setText(position + 1 + "/" + mListUrl.size());
                mTv.getBackground().setAlpha(100);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        dialog.show();
        isShow = true;
    }

    public static void showPhotoDetailsDialog(Context mContext, final List<String> mListUrl, int position,
                                              List<LocalMedia> selectList, ShopImageViewAdapter shopImageViewAdapter) {
        dialog = new Dialog(mContext, R.style.Theme_Light_Dialog);
        dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_show_photos_hyl, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        // window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);


        final ImageView iv_delete = dialog.findViewById(R.id.iv_delete);
        final TextView mTv = dialog.findViewById(R.id.tv_dialog_photo);
        PhotoViewPager mVp = dialog.findViewById(R.id.vp_dialog_photo);
        FingerFrameLayout mFl = dialog.findViewById(R.id.ffl_dialog_photo);

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("asfdsdsfsd.....",position+"aa");
                selectList.remove(position);
                shopImageViewAdapter.notifyItemRemoved(position);
                shopImageViewAdapter.notifyItemRangeChanged(position, selectList.size());
                EventBus.getDefault().post(new DeletePicEvent(position));

                if (dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        mFl.setOnAlphaChangeListener(new FingerFrameLayout.onAlphaChangedListener() {
            @Override
            public void onAlphaChanged(float alpha) {
                Log.e("fengan", "[onAlphaChanged]:alpha=" + alpha);
            }

            @Override
            public void onTranslationYChanged(float translationY) {
                Log.e("fengan", "[onTranslationYChanged]:translationY=" + translationY);
            }

            @Override
            public void onFinishAction() {
                hidePhotoDetailDialog();
            }
        });
        PhotoViewAdapter photoViewAdapter = new PhotoViewAdapter(mListUrl, mContext);
        mVp.setAdapter(photoViewAdapter);
        mVp.setCurrentItem(position);
        mTv.setText(position  + 1+"/" + mListUrl.size());
        photoViewAdapter.setPhotoListener(new PhotoViewAdapter.OnPhotoListener() {
            @Override
            public void onPhotoListenter() {
                if (dialog!=null){
                    dialog.dismiss();
                }
            }
        });


        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTv.setText(position + 1 + "/" + mListUrl.size());
                mTv.getBackground().setAlpha(100);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        dialog.show();
        isShow = true;
    }

    /**
     * 隐藏查看大图
     */
    public static void hidePhotoDetailDialog() {
        if (isShow) {
            dialog.dismiss();
            isShow = false;
        }
    }

    /**
     * 显示吐司
     */
    public static Toast mToast;

    public static void showMsg(Context context, String Msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), Msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setText(Msg);
        }
        mToast.show();
    }

    /**
     * 设置缺省页
     */
    public static View getEmptyView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.no_view, null, false);
        return view;
    }

    /**
     * 设置错误页
     */
    public static View getErrorView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.no_view, null, false);
        return view;
    }

    /**
     * 设置加载动画
     */
    public static View getLoadingView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_loading, null, false);
        return view;
    }

    /**
     * 隐藏加载动画
     **/
    public static void cancleLottieAnimation(View view) {
      LottieAnimationView lav = view.findViewById(R.id.lav_loading);
       lav.cancelAnimation();
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

}
