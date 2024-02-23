package com.barter.hyl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.HylMyCollectionAdapter;
import com.barter.hyl.app.api.DetailApi;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.AppHelper;
import com.barter.hyl.app.dialog.AuthDialog;
import com.barter.hyl.app.dialog.CollectionDialog;
import com.barter.hyl.app.event.CartListHylEvent;
import com.barter.hyl.app.event.JumpCartHylEvent;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.HylCartNumModel;
import com.barter.hyl.app.model.HylMyCollectionModel;
import com.barter.hyl.app.utils.SharedPreferencesUtil;
import com.barter.hyl.app.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/8/16
 */
public class HylActivityCollection extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_edit)
    TextView tv_edit;
    @BindView(R.id.rl_foot)
    RelativeLayout rl_foot;
    @BindView(R.id.cb_all_select)
    CheckBox cb_all_select;
    @BindView(R.id.ll_all_select)
    LinearLayout ll_all_select;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.rl_cart)
    RelativeLayout rl_cart;

    View emptyView;
    private Map<Integer, Boolean> isCheck = new HashMap<>();//存储选择状态

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_collection_hyl);
    }

    HylMyCollectionAdapter hylMyCollectionAdapter;
    private int selectedNum = 0;//被选中的数量,一开始为0
    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        tv_title.setText("我的收藏");
        iv_image.setVisibility(View.VISIBLE);
        emptyView = View.inflate(mActivity, R.layout.no_view, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        hylMyCollectionAdapter = new HylMyCollectionAdapter(R.layout.item_collection_hyl,list,isCheck, new HylMyCollectionAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(int position) {
                String authorFlag = SharedPreferencesUtil.getString(mContext, "authorFlag");
                if(authorFlag.equals("1")) {
                    CollectionDialog collectionDialog = new CollectionDialog(mActivity,list.get(position));
                    collectionDialog.show();
                }else {
                    AuthDialog authDialog = new AuthDialog(mContext);
                    authDialog.show();
                }

            }
        });

        recyclerView.setAdapter(hylMyCollectionAdapter);

        hylMyCollectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext,HylCommonGoodsActivity.class);
                intent.putExtra("mainId",list.get(position).getMainId());
                startActivity(intent);
            }
        });
        hylMyCollectionAdapter.setOnItemClickListener(new HylMyCollectionAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(View view, int position) {
                if (isCheck.get(position)) {
                    //如果取消，则设置map集合中为false
                    isCheck.put(position, false);
                } else {
                    //如果选中，则设置map集合中为true
                    isCheck.put(position, true);
                }
                selectedNum = 0;
                for (int i = 0; i < isCheck.size(); i++) {
                    if (isCheck.get(i)) {
                        selectedNum++;
                    }
                }
                if (selectedNum == list.size()) {
                    //如果用户一个一个单选,选到全部商品,上面的全选自动选中
                    cb_all_select.setChecked(true);
                } else {
                    cb_all_select.setChecked(false);
                }

                hylMyCollectionAdapter.notifyDataSetChanged();
            }
        });
        getMyCollection();

    }

    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
        tv_edit.setOnClickListener(this);
        ll_all_select.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        rl_cart.setOnClickListener(this);
    }

    List<HylMyCollectionModel.DataBean> list = new ArrayList<>();
    private void getMyCollection() {
        MyInfoApi.getCollectionList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylMyCollectionModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HylMyCollectionModel hylMyCollectionModel) {
                        if (hylMyCollectionModel.getCode()==1) {
                            list.clear();
                            if(hylMyCollectionModel.getData()!=null&& hylMyCollectionModel.getData().size()>0) {
                                list.addAll(hylMyCollectionModel.getData());
                                isCheck.clear();
                                for (int i = 0; i < list.size(); i++) {
                                    isCheck.put(i, false);
                                }
                            }else {
                                hylMyCollectionAdapter.setEmptyView(emptyView);
                            }
                            hylMyCollectionAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, hylMyCollectionModel.getMessage());
                        }
                    }
                });
    }

    boolean isShow;
    private boolean isAllSelected;
    private List<Integer> collectionId = new ArrayList<>();
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_cart:
                EventBus.getDefault().post(new JumpCartHylEvent());
                finish();
                break;

            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_edit:
                isShow = !isShow;
                hylMyCollectionAdapter.setIsShow(isShow);
                hylMyCollectionAdapter.notifyDataSetChanged();

                if(isShow) {
                    tv_edit.setText("完成");
                    rl_foot.setVisibility(View.VISIBLE);
                }else {
                    rl_foot.setVisibility(View.GONE);
                    tv_edit.setText("管理");
                }
                break;

            case R.id.ll_all_select:
                if (isAllSelected) {
                    //正在被全选
                    cb_all_select.setChecked(false);
                    for (int i = 0; i < isCheck.size(); i++) {
                        isCheck.put(i, false);
                    }
                    hylMyCollectionAdapter.notifyDataSetChanged();
                    isAllSelected = false;
                } else {
                    //没有全选中
                    cb_all_select.setChecked(true);
                    for (int i = 0; i < isCheck.size(); i++) {
                        isCheck.put(i, true);
                    }
                    hylMyCollectionAdapter.notifyDataSetChanged();
                    isAllSelected = true;
                }

                break;

            case R.id.tv_delete:
                collectionId.clear();
                if(list!=null&&list.size()>0) {
                    for (int i = 0; i < isCheck.size(); i++) {
                        if (isCheck.get(i)) {
                            collectionId.add(list.get(i).getCollectId());
                        }
                    }
                }

                if (collectionId != null && collectionId.size() > 0) {
                    showDeleteCollection();
                } else {
                    AppHelper.showMsg(mActivity, "请先选中商品");
                }
                break;

        }
    }

    private void showDeleteCollection() {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_delete_collection_hyl);
        TextView mTvCancel = (TextView) window.findViewById(R.id.tv_dialog_delete_collection_cancel);
        TextView mTvConfirm = (TextView) window.findViewById(R.id.tv_dialog_delete_collection_confirm);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String collectionIds = StringUtils.join(collectionId, ",");
                deleteCollection(collectionIds);
                alertDialog.dismiss();
            }
        });
    }

    private void deleteCollection(String collectionId) {
        DetailApi.deleteCollection(mActivity,collectionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
                            getMyCollection();
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, baseModel.message);
                        }
                    }
                });
    }

    /**
     * 获取金额和数量
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(CartListHylEvent cartListHylEvent) {
        HylCartNumModel.DataBean data = cartListHylEvent.getCartNumModel().getData();
        if(data.getProdNum()>0) {
            tv_number.setText(data.getProdNum()+"");
            tv_number.setVisibility(View.VISIBLE);
        }else {
            tv_number.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    public static Date getNowDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStrings = formatters.format(currentTimes);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatters.parse(dateStrings, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTimes = new Date();
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy");
        String dateStrings = formatters.format(currentTimes);
        return dateStrings;
    }

}
