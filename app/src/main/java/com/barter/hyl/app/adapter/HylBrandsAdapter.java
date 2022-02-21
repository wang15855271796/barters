package com.barter.hyl.app.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.barter.hyl.app.R;
import com.barter.hyl.app.model.HylMarketBeanModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/9/23
 */
public class HylBrandsAdapter extends RecyclerView.Adapter<HylBrandsAdapter.BrandViewHolder>{

    private Context context;
    private List<HylMarketBeanModel> list;
    private List<HylMarketBeanModel> selectList =new ArrayList<>();
    BrandViewHolder holder;
    private OnItemClickListener onItemClickListener;

    public HylBrandsAdapter(Context context, List<HylMarketBeanModel> list) {
        this.context = context;
        this.list = list;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setStat() {
        for (HylMarketBeanModel bean:list) {
            bean.setState(false);
        }

        for (int i = 0; i < list.size(); i++) {
            selectList.remove(list.get(i));
        }

        if(onItemClickListener!=null) {
            onItemClickListener.onItemClick(selectList);
        }
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_brand_hyl, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BrandViewHolder holder, final int position) {
        this.holder = holder;
        if (list!=null&&list.get(position) !=null){
            if (list.get(position).isState()){

                holder.mTvContent.setTextColor(Color.parseColor("#FF5000"));

            }else {
                holder.mTvContent.setTextColor(Color.parseColor("#636363"));
            }
            holder.mTvContent.setText(list.get(position).getS());
        }


        holder.mTvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mTvContent.isChecked()){
                    selectList.add(list.get(position));
                    holder.mTvContent.setTextColor(Color.parseColor("#FF5000"));

                }else {
                    selectList.remove(list.get(position));
                    holder.mTvContent.setTextColor(Color.parseColor("#636363"));

                }
                onItemClickListener.onItemClick(selectList);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

  /*  private TextView mTvContent;

    public MarketGoodBrandAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        mTvContent = helper.getView(R.id.tv_content);
        mTvContent.setText(item);

    }*/


    public class BrandViewHolder extends RecyclerView.ViewHolder {
        CheckBox mTvContent;

        public BrandViewHolder(View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.cb_content);


        }
    }



    public interface OnItemClickListener {
        void onItemClick(List<HylMarketBeanModel> position);


    }
}
