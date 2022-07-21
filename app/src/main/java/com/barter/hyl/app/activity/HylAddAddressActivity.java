package com.barter.hyl.app.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.api.AddressApi;
import com.barter.hyl.app.base.BaseActivity;
import com.barter.hyl.app.constant.StringHelper;
import com.barter.hyl.app.event.AddAddressHylEvent;
import com.barter.hyl.app.model.HylAreaModel;
import com.barter.hyl.app.model.BaseModel;
import com.barter.hyl.app.utils.ToastUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import org.greenrobot.eventbus.EventBus;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/8/16
 */
public class HylAddAddressActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_shop)
    EditText et_shop;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.tv_address)
    AutoCompleteTextView tv_address;
    @BindView(R.id.bt_add)
    Button bt_add;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rl_default)
    RelativeLayout rl_default;
    @BindView(R.id.cb_default)
    CheckBox cb_default;
    @BindView(R.id.iv_switch)
    ImageView iv_switch;
    //  省
    private List<HylAreaModel.DataBean> options1Items = new ArrayList<>();
    //  市
    private ArrayList<ArrayList<HylAreaModel.DataBean.CityListBean>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<HylAreaModel.DataBean.CityListBean.AreaListBean>>> options3Items = new ArrayList<>();
    String proviceCode;
    String cityCode;
    String areaCode;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_add_address_hyl);
    }

    @Override
    public void setViewData() {
        bt_add.setEnabled(false);
//        bt_add.setTextColor(getResources().getColor(R.color.app_btn_unable));
        getAddressArea();
    }



    @Override
    public void setClickListener() {
        tv_title.setText("新增收货地址");
        iv_back.setOnClickListener(this);
        bt_add.setOnClickListener(this);
        tv_area.setOnClickListener(this);
        et_shop.addTextChangedListener(textWatcher);
        et_name.addTextChangedListener(textWatcher);
        et_phone.addTextChangedListener(textWatcher);
        tv_address.addTextChangedListener(textWatcher);
        rl_default.setOnClickListener(this);
    }

    /**
     * 获取区域地址
     */
    private void getAddressArea() {
        AddressApi.AddressArea(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HylAreaModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HylAreaModel hylAreaModel) {
                        if (hylAreaModel.getCode()==1) {
                            parseData(hylAreaModel.getData());
                        } else {
                            ToastUtil.showSuccessMsg(mContext, hylAreaModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 处理地址数据结构
     * @param data
     */
    private void parseData(List<HylAreaModel.DataBean> data) {
        options1Items = data;
    //     遍历省
        for(int i = 0; i <data.size() ; i++) {
//         存放城市
            ArrayList<HylAreaModel.DataBean.CityListBean> cityList = new ArrayList<>();
//         存放区
            ArrayList<ArrayList<HylAreaModel.DataBean.CityListBean.AreaListBean>> province_AreaList = new ArrayList<>();
            List<HylAreaModel.DataBean.CityListBean> children1 = data.get(i).getCityList();
            cityList.addAll(children1);
//         遍历市
            for(int c = 0; c <data.get(i).getCityList().size() ; c++) {
                //该城市的所有地区列表
                ArrayList<HylAreaModel.DataBean.CityListBean.AreaListBean> city_AreaList = new ArrayList<>();

                if (data.get(i).getCityList().get(c).getAreaList() == null || data.get(i).getCityList().get(c).getAreaList().size() == 0) {
                    HylAreaModel.DataBean.CityListBean.AreaListBean childrenBean = new HylAreaModel.DataBean.CityListBean.AreaListBean();
                    childrenBean.setAreaName("");
                    city_AreaList.add(childrenBean);
                } else {

                    List<HylAreaModel.DataBean.CityListBean.AreaListBean> children = data.get(i).getCityList().get(c).getAreaList();
                    city_AreaList.addAll(children);
                    province_AreaList.add(city_AreaList);

                }
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);
            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);

        }

        isLoaded = true;
    }

    /**
     * 添加地址
     */
    private void addAddress(String username,String contactPhone,String shopName,
                            String provinceCode,String cityCode,String areaCode,String detailAddress,int isDefault) {
        AddressApi.AddAddress(mContext,username,contactPhone,shopName,provinceCode,cityCode,areaCode,detailAddress,isDefault)
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
                            finish();
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                            EventBus.getDefault().post(new AddAddressHylEvent());
                        } else {
                            ToastUtil.showSuccessMsg(mContext, baseModel.message);
                        }
                    }
                });
    }


    boolean isLoaded = false;
    int isDefault = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_default:
                if (isDefault==1) {
                    //现在就是默认的,点击变成不是默认的
                    iv_switch.setImageResource(R.mipmap.iv_closes);
                    isDefault = 0;
                } else {
                    cb_default.setChecked(true);
                    iv_switch.setImageResource(R.mipmap.iv_opens);
                    isDefault = 1;

                }
                break;
            case R.id.bt_add:

                if (StringHelper.notEmptyAndNull(et_name.getText().toString())
                        &&StringHelper.notEmptyAndNull(et_phone.getText().toString())
                        && StringHelper.notEmptyAndNull(et_shop.getText().toString())
                        && StringHelper.notEmptyAndNull(tv_address.getText().toString())
                        && StringHelper.notEmptyAndNull(tv_area.getText().toString())
                        && !tv_area.getText().toString().equals("请选择所在地区")) {
                    String tvArea = tv_area.getText().toString();
                    String etShop = et_shop.getText().toString();
                    String etName = et_name.getText().toString();
                    String etPhone = et_phone.getText().toString();
                    String tvAddress = tv_address.getText().toString();

                    addAddress(etName,etPhone,etShop,proviceCode,cityCode,areaCode,tvAddress,isDefault);
                }else {
                    ToastUtil.showSuccessMsg(mContext,"请填入对应正确的信息");
                }


                break;

            case R.id.tv_area:
                hintKbTwo();
                if(isLoaded) {
                    showPickerView();
                }
                break;

            case R.id.iv_back:
                finish();
                break;
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (StringHelper.notEmptyAndNull(et_name.getText().toString())
                    && StringHelper.notEmptyAndNull(et_shop.getText().toString())
                    && StringHelper.notEmptyAndNull(tv_address.getText().toString())
                    && StringHelper.notEmptyAndNull(tv_area.getText().toString())
                    && !tv_area.getText().toString().equals("请选择所在地区")) {
                bt_add.setEnabled(true);
                bt_add.setTextColor(getResources().getColor(R.color.app_color_white));
            } else {
                bt_add.setEnabled(false);
//                bt_add.setTextColor(getResources().getColor(R.color.app_btn_unable));
            }
        }
    };

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                proviceCode = options1Items.get(options1).getProvinceCode();
                cityCode = options2Items.get(options1).get(options2).getCityCode();
                areaCode = options3Items.get(options1).get(options2).get(options3).getAreaCode();
                String tx = options1Items.get(options1).getProvinceName() +
                        options2Items.get(options1).get(options2).getCityName() +
                        options3Items.get(options1).get(options2).get(options3).getAreaName();
                tv_area.setText(tx);
                tv_area.setTextColor(Color.parseColor("#333333"));
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setFlag(false)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
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
