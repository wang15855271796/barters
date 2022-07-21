package com.barter.hyl.app.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.adapter.CateAdapter;
import com.barter.hyl.app.listener.PopWindowListener;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by ${王涛} on 2021/1/8
 */
public class CatePopWindow extends PopupWindow {
    Activity context;
    List<String> strings;
    PopWindowListener popWindowListener;
    public CatePopWindow(Activity context, List<String> strings) {
        super(context);
        this.context=context;
        this.strings = strings;
        setOutsideTouchable(true);
        setFocusable(true);
        setTouchable(true);
        init();
    }

    public void setPopWindowListener(PopWindowListener popWindowListener) {
        this.popWindowListener = popWindowListener;
    }


    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_recycleview, null);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setContentView(view);
        CateAdapter cateAdapter = new CateAdapter(R.layout.item_market, strings, new CateAdapter.Onclick() {
            @Override
            public void getCate(String item) {

            }
        });
        recyclerView.setAdapter(cateAdapter);
        cateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                cateAdapter.setPosition(position);
                cateAdapter.notifyDataSetChanged();

                if(popWindowListener!=null) {
                    popWindowListener.getCateStyle(strings.get(position),position);
                }
            }
        });
    }


}
