package com.barter.hyl.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.constant.UserInfoHelper;
import com.barter.hyl.app.model.HylReturnOrderDetailModel;
import com.barter.hyl.app.view.EasySwipeMenuLayout;
import com.barter.hyl.app.view.State;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王文博} on 2019/5/23
 */
public class HylReturnGoodDetailTwoAdapter extends RecyclerView.Adapter<HylReturnGoodDetailTwoAdapter.ReturnOrderViewHolder> {
    private List<HylReturnOrderDetailModel.DataBean.ProdsBean> mListProduct;
    private Context context;
    //侧滑菜单里面的
    HylReturnNumAdapter mHylReturnNumAdapter;
    private int selectPosition;


    private String orderId;
    private int businessId;
    private int businessType;
    public OnReturnClickListener listener;
    String allReturn;
    public OnReturnClickListener getListener() {
        return listener;
    }

    public void setListener(OnReturnClickListener listener) {
        this.listener = listener;
    }

    public interface OnReturnClickListener {
        void onClick();

    }

    public HylReturnGoodDetailTwoAdapter(List<HylReturnOrderDetailModel.DataBean.ProdsBean> mListProduct, Context context, String allReturn) {
        this.mListProduct = mListProduct;
        this.context = context;
        this.allReturn = allReturn;
        for (int i = 0; i < mListProduct.size(); i++) {
            for (int j = 0; j < mListProduct.get(i).getDetails().size(); j++) {
                mListProduct.get(i).setBusinessId(mListProduct.get(i).getBusinessId());
                mListProduct.get(i).setBusinessType(mListProduct.get(i).getBusinessType());
            }
        }
    }

    @NonNull
    @Override
    public ReturnOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.return_good_detail_new, null);
        return new ReturnOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReturnOrderViewHolder holder, int position) {
        double totalPrice = 0.00;

        //必须局部变量，不然editview使用的时候会复用
        //规格
        List<HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean> mListSpec = new ArrayList<>();
        HylReturnSpecsAdapter mSpecAdaPter;
        List<HylReturnOrderDetailModel.DataBean.ProdsBean.DetailsBean> mListReturnNum = new ArrayList<>();
        selectPosition = position;
        holder.tvProductName.setText(mListProduct.get(position).getProdName());

        Glide.with(context).load(mListProduct.get(position).getDefaultPic()).into(holder.ivProduct);
        //侧滑菜单
        //数量

        mListReturnNum.clear();
        mListReturnNum.addAll(mListProduct.get(position).getDetails());
        mHylReturnNumAdapter = new HylReturnNumAdapter(R.layout.return_order_num,mListProduct,mListProduct.get(position).getBusinessType(), mListReturnNum,mListProduct.get(position).getAdditionFlag());
        mHylReturnNumAdapter.setListener(new HylReturnNumAdapter.OnReturnClickListener() {
            @Override
            public void onEventClick() {
                //回调
                listener.onClick();

            }
        });

        holder.mRyNum.setLayoutManager(new GridLayoutManager(context, 1));


        holder.mRyNum.setAdapter(mHylReturnNumAdapter);
        mHylReturnNumAdapter.notifyDataSetChanged();

        //规格
        mListSpec.clear();

        mListSpec.addAll(mListProduct.get(position).getDetails());
        holder.mRySpec.setLayoutManager(new LinearLayoutManager(context));

        orderId = UserInfoHelper.getOrderId(context);

        businessId = mListProduct.get(position).getBusinessId();
        businessType = mListProduct.get(position).getBusinessType();

        final boolean[] isSelect = {true};
        mSpecAdaPter = new HylReturnSpecsAdapter(R.layout.retrun_order_spec, mListSpec,allReturn);


        mSpecAdaPter.setClick(new HylReturnSpecAdapter.OnItemClick() {
            @Override
            public void delete() {
                EasySwipeMenuLayout easySwipeMenuLayout = holder.itemView.findViewById(R.id.es);

                if (isSelect[0]) {
                    easySwipeMenuLayout.handlerSwipeMenu(State.RIGHTOPEN);
                    isSelect[0] = false;
                } else {
                    easySwipeMenuLayout.handlerSwipeMenu(State.CLOSE);
                    isSelect[0] = true;
                }


            }
        });

        holder.mRySpec.setAdapter(mSpecAdaPter);


    }

    @Override
    public int getItemCount() {
        return mListProduct.size();
    }

    public class ReturnOrderViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivProduct;
        private ImageView ivFlag;
        private TextView tvProductName;
        //规格
        private RecyclerView mRySpec;
        private RecyclerView mRyNum;

        public ReturnOrderViewHolder(View itemView) {
            super(itemView);


            ivProduct = itemView.findViewById(R.id.iv_good_item);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            ivFlag = itemView.findViewById(R.id.iv_flag);
            mRySpec = itemView.findViewById(R.id.ry_spc);

            mRyNum = itemView.findViewById(R.id.ry_return_num);
        }
    }
}




