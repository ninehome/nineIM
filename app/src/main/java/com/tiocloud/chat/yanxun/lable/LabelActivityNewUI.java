package com.tiocloud.chat.yanxun.lable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.page.TioActivity;

import java.util.ArrayList;
import java.util.List;

public class LabelActivityNewUI extends TioActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager mViewPager;

    public static void start(Context ctx) {
        Intent intent = new Intent(ctx, LabelActivityNewUI.class);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_new_ui);
        initView();
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.label_tab_vp);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LabelFragment());
        mViewPager.setAdapter(new LabelActivityNewUI.MyTabAdapter(getSupportFragmentManager(), fragments));

        tabLayout = (TabLayout) findViewById(R.id.label_tab_layout);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black_4d000000), getResources().getColor(R.color.blue_2e52a3));
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.iv_title_left:
//                finish();
//                break;
        }
    }

    class MyTabAdapter extends FragmentPagerAdapter {
        List<String> listTitle = new ArrayList<>();
        private List<Fragment> mFragments;

        MyTabAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            if (mFragments != null) {
                return mFragments.size();
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }
}
