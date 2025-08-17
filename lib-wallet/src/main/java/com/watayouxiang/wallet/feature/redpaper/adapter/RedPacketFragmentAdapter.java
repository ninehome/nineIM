package com.watayouxiang.wallet.feature.redpaper.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.watayouxiang.wallet.feature.redpaper.RedPaperType;
import com.watayouxiang.wallet.feature.redpaper.feature.p2p.P2PFragment;
import com.watayouxiang.wallet.feature.redpaper.feature.pin.PinFragment;
import com.watayouxiang.wallet.feature.redpaper.feature.single.SingleFragment;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/09
 *     desc   :
 * </pre>
 */
public class RedPacketFragmentAdapter extends FragmentStatePagerAdapter {
    private final String[] titles;
    private final Fragment[] fragments;

    /**
     * @param type 1 群聊，2 私聊
     */
    public RedPacketFragmentAdapter(@NonNull FragmentManager fm, @RedPaperType int type) {
        super(fm);
        if (type == RedPaperType.Group) {
            titles = new String[]{"拼人品红包", "普通红包"};
            fragments = new Fragment[]{PinFragment.newInstance(), SingleFragment.newInstance()};
        } else if (type == RedPaperType.P2P) {
            titles = new String[]{"私聊红包"};
            fragments = new Fragment[]{P2PFragment.newInstance()};
        } else {
            titles = null;
            fragments = null;
        }
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
