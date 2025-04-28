package com.watayouxiang.wallet.feature.bill.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.watayouxiang.wallet.feature.bill.fragment.BillVo;
import com.watayouxiang.wallet.feature.bill.fragment.BillFragment;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/03
 *     desc   :
 * </pre>
 */
public class BillFragmentAdapter extends FragmentStatePagerAdapter {
    private final String[] titles = {
            BillVo.ALL.name,
            BillVo.RECHARGE.name,
            BillVo.WITHDRAW.name,
            BillVo.RED_PAPER.name
    };
    private final Fragment[] fragments = {
            BillFragment.newInstance(BillVo.ALL),
            BillFragment.newInstance(BillVo.RECHARGE),
            BillFragment.newInstance(BillVo.WITHDRAW),
            BillFragment.newInstance(BillVo.RED_PAPER),
    };

    public BillFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
