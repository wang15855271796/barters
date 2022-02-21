package com.barter.hyl.app.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.JiFenAdapter;
import com.barter.hyl.app.api.MyInfoApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.model.MyJifenModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/9/1
 */
public class JiFenActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_Jifen)
    TextView tv_Jifen;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;

    JiFenAdapter jiFenAdapter;
    View emptyView;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_jifen);
    }

    @Override
    public void setViewData() {
        getData();
        tv_title.setText("积分商城");
        emptyView = View.inflate(mActivity, R.layout.no_view, null);
        jiFenAdapter = new JiFenAdapter(R.layout.item_jifen, list, new JiFenAdapter.Onclick() {
            @Override
            public void changeJifen(int position,int point) {
                showJifenDialog(position,point);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(jiFenAdapter);
    }




    @Override
    public void setClickListener() {
        iv_back.setOnClickListener(this);
    }

    List<MyJifenModel.DataBean.GiftsBean> list = new ArrayList<>();
    MyJifenModel myJifenModels;
    private void getData() {
        MyInfoApi.getJifenList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyJifenModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MyJifenModel myJifenModel) {
                        if (myJifenModel.getCode()==1) {
                            if(myJifenModel.getData()!=null) {
                                myJifenModels = myJifenModel;
                                tv_Jifen.setText(myJifenModel.getData().getPoint()+"");
                                if(myJifenModel.getData().getGifts()!=null&&myJifenModel.getData().getGifts().size()>0) {
                                    list.clear();
                                    list.addAll(myJifenModel.getData().getGifts());
                                    jiFenAdapter.notifyDataSetChanged();
                                }else {
                                    jiFenAdapter.setEmptyView(emptyView);
                                }
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, myJifenModel.getMessage());
                        }
                    }
                });
    }


    private void showJifenDialog(final int position,int point) {
        CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
        final CustomDialog dialog = builder.view(R.layout.dialog_delete_cart_hyl)
                .style(R.style.DialogStyle)
                .heightdp(128)
                .widthdp(270)
                .build();

        dialog.show();

        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
        TextView tv_ok = dialog.findViewById(R.id.tv_ok);
        TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText("确定使用"+point+"个积分兑换优惠券？");
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exChange(position);
                dialog.dismiss();
            }
        });
    }

    private void exChange(int position) {
        MyInfoApi.exChange(mActivity,myJifenModels.getData().getGifts().get(position).getPoolNo())
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
                            ToastUtil.showSuccessMsg(mActivity, baseModel.message);
                        } else {
                            ToastUtil.showSuccessMsg(mActivity, baseModel.message);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
