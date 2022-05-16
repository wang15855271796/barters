package com.barter.hyl.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.ShopTypeAdapter;
import com.barter.hyl.app.event.ShopStyleEvent;
import com.barter.hyl.app.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ${王涛} on 2021/1/4
 */
public class ShopStyleDialog extends Dialog{

    Context context;
    public View view;
    public Unbinder binder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<String> list = new ArrayList<>();

    String[] data = {"店铺转让","器具转让","厨师招聘","其它信息"};
    public ShopStyleDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
        init();
    }



    public void init() {
        view = View.inflate(context, R.layout.dialog_shop_style, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);

        List<String> strings = Arrays.asList(data);
        ShopTypeAdapter shopTypeAdapter = new ShopTypeAdapter(R.layout.dialog_shop_type,strings);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(shopTypeAdapter);

        shopTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                shopTypeAdapter.setSelectionPosition(position);
                shopTypeAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(new ShopStyleEvent(data[position],position+1));
                dismiss();
            }
        });

    }

}
