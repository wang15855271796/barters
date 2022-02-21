package com.barter.hyl.app.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ${王文博} on 2019/5/23
 */
public class HylReturnGoodDetailChangeAdapter extends RecyclerView.Adapter<HylReturnGoodDetailChangeAdapter.ReturnOrderViewHolder> {
    @NonNull
    @Override
    public ReturnOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnOrderViewHolder returnOrderViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

//    private List<HylReturnGoodModel.DataBean.ProdsBean> mListProduct;
//    private Context context;
//    //侧滑菜单里面的
//    ReturnNumChangeAdapter mHylReturnNumAdapter;
//    public int selectPosition;
//    public String orderId;
//    public int businessId;
//    public int businessType;
//    int allReturn;
//    public OnReturnClickListener listener;
//    public OnReturnClickListener getListener() {
//        return listener;
//    }
//    public void setListener(OnReturnClickListener listener) {
//        this.listener = listener;
//    }
//    public interface OnReturnClickListener {
//        void onClick();
//
//    }
//
//    public HylReturnGoodDetailChangeAdapter(List<HylReturnGoodModel.DataBean.ProdsBean> mListProduct, Context context, int allReturn) {
//        this.mListProduct = mListProduct;
//        this.context = context;
//        this.allReturn = allReturn;
//    }
//
//    @NonNull
//    @Override
//    public ReturnOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = View.inflate(context, R.layout.item_return_goods, null);
//        return new ReturnOrderViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final ReturnOrderViewHolder holder, int position) {
//        //必须局部变量，不然editview使用的时候会复用
//        //规格
//        List<HylReturnGoodModel.DataBean.ProdsBean.DetailsBean> mListSpec = new ArrayList<>();
//        HylReturnSpecAdapter mSpecAdaPter;
//
//        List<HylReturnGoodModel.DataBean.ProdsBean.DetailsBean> mListReturnNum = new ArrayList<>();
//
//        selectPosition = position;
//        Glide.with(context).load(mListProduct.get(position).getDefaultPic()).into(holder.ivFlag);
//        holder.tvProductName.setText(mListProduct.get(position).getProdName());
//        Glide.with(context).load(mListProduct.get(position).getDefaultPic()).into(holder.ivProduct);
//        //侧滑菜单
//        //数量
//        mListReturnNum.clear();
//        mListReturnNum.addAll(mListProduct.get(position).getDetails());
//        if(allReturn==1) {
//
//        }else {
//            mHylReturnNumAdapter = new ReturnNumChangeAdapter(R.layout.return_order_num, mListReturnNum);
//            mHylReturnNumAdapter.setListener(new ReturnNumChangeAdapter.OnReturnClickListener() {
//                @Override
//                public void onEventClick() {
//                    listener.onClick();
//                }
//            });
//
//            holder.mRyNum.setLayoutManager(new GridLayoutManager(context, 1));
//            holder.mRyNum.setAdapter(mHylReturnNumAdapter);
//            mHylReturnNumAdapter.notifyDataSetChanged();
//        }
//
//        //规格
//        mListSpec.clear();
//        mListSpec.addAll(mListProduct.get(position).getDetails());
//        holder.mRySpec.setLayoutManager(new LinearLayoutManager(context));
//        mSpecAdaPter = new HylReturnSpecAdapter(R.layout.retrun_order_spec, mListSpec,allReturn);
//        orderId = UserInfoHelper.getOrderId(context);
//
//        businessId = mListProduct.get(position).getBusinessId();
//        businessType = mListProduct.get(position).getBusinessType();
//
//        final boolean[] isSelect = {true};
//
//
//        mSpecAdaPter.setClick(new HylReturnSpecAdapter.OnItemClick() {
//            @Override
//            public void delete() {
//                EasySwipeMenuLayout easySwipeMenuLayout = holder.itemView.findViewById(R.id.es);
//
//                if (isSelect[0]) {
//                    easySwipeMenuLayout.handlerSwipeMenu(State.RIGHTOPEN);
//                    isSelect[0] = false;
//                } else {
//                    easySwipeMenuLayout.handlerSwipeMenu(State.CLOSE);
//                    isSelect[0] = true;
//                }
//            }
//        });
//        holder.mRySpec.setAdapter(mSpecAdaPter);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mListProduct.size();
//    }

    public class ReturnOrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProduct;
        private ImageView ivFlag;
        private TextView tvProductName;
        private RecyclerView mRySpec;
        private RecyclerView mRyNum;

        public ReturnOrderViewHolder(View itemView) {
            super(itemView);
//            ivProduct = itemView.findViewById(R.id.iv_good_item);
//            tvProductName = itemView.findViewById(R.id.tv_product_name);
//            ivFlag = itemView.findViewById(R.id.iv_flag);
//            mRySpec = itemView.findViewById(R.id.ry_spc);
//            mRyNum = itemView.findViewById(R.id.ry_return_num);
        }
    }
}




