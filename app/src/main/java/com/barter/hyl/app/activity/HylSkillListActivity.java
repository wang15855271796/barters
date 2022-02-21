package com.barter.hyl.app.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barter.hyl.app.R;
import com.barter.hyl.app.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by ${王涛} on 2021/8/6
 */
public class HylSkillListActivity extends BaseActivity {
    @BindView(R.id.recyclerView_title)
    RecyclerView recyclerView_title;

    @BindView(R.id.recyclerView_list)
    RecyclerView recyclerView_list;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.skill_list_activity);
    }

    @Override
    public void setViewData() {
//        SkillTitleAdapter skillListTitleAdapter = new SkillTitleAdapter(R.layout.item_skill_list_title,list);
        recyclerView_title.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
//        recyclerView_title.setAdapter(skillTitleAdapter);

//                HylSkillListAdapter hylSkillListAdapter = new HylSkillListAdapter(R.layout.item_skill_list,list);
        recyclerView_title.setLayoutManager(new LinearLayoutManager(mActivity));
//        recyclerView_title.setAdapter(HylSkillListAdapter);
    }

    @Override
    public void setClickListener() {

    }
}
