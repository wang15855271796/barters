package com.barter.hyl.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.PayInfoListAdapter;
import com.barter.hyl.app.api.OrderApi;
import com.barter.hyl.app.event.InfoPayEvent;
import com.barter.hyl.app.model.PayInfoListModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.barter.hyl.app.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InfoPayDialog extends Dialog {
    Context context;
    View view;
    Unbinder binder;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Integer flag;
    String amount;
    public InfoPayDialog(Context context, String amount) {
        super(context, R.style.dialog);
        this.context = context;
        this.amount = amount;
        init();

    }

    private void init() {
        view = View.inflate(context, R.layout.dialog_pay, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
        tv_amount.setText(amount);
        getPayList();
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new InfoPayEvent(flag));
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        payListAdapter = new PayInfoListAdapter(R.layout.item_pay_info_list,list);
        recyclerView.setAdapter(payListAdapter);

        payListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                payListAdapter.selectionPosition(position);
                flag = list.get(position).getFlag();
            }
        });


    }

    /**
     * 提交订单
     */
    List<PayInfoListModel.DataBean> data;
    List<PayInfoListModel.DataBean> list = new ArrayList<>();
    PayInfoListAdapter payListAdapter;

    private void getPayList() {
        OrderApi.getPayList(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PayInfoListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(PayInfoListModel payListModel) {
                        if (payListModel.getCode()==1) {
                            data = payListModel.getData();
                            list.clear();
                            list.addAll(data);
                            payListAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showSuccessMsg(context,payListModel.getMessage());
                        }
                    }
                });
    }
}
