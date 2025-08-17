package com.watayouxiang.wallet.feature.redpaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.KeyboardUtils;
import com.watayouxiang.androidutils.page.easy.EasyDarkActivity;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletRedpaperActivityBinding;
import com.watayouxiang.wallet.feature.redpacket.RedPacketActivity;
import com.watayouxiang.wallet.feature.redpaper.adapter.RedPacketFragmentAdapter;
import com.watayouxiang.wallet.feature.redpaper.adapter.RedPacketTabAdapter;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/09
 *     desc   : 发红包
 * </pre>
 */
public class RedPaperActivity extends EasyDarkActivity<WalletRedpaperActivityBinding> {

    private static final String RED_PAPER_VO = "red_paper_vo";

    public static void startP2P(Context context, String chatlinkid) {
        RedPaperVo redPaperVo = new RedPaperVo(RedPaperType.P2P, chatlinkid);
        start(context, redPaperVo);
    }

    public static void startGroup(Context context, String chatlinkid) {
        RedPaperVo redPaperVo = new RedPaperVo(RedPaperType.Group, chatlinkid);
        start(context, redPaperVo);
    }

    private static void start(Context context, RedPaperVo vo) {
        Intent starter = new Intent(context, RedPaperActivity.class);
        starter.putExtra(RED_PAPER_VO, vo);
        context.startActivity(starter);
    }

    public RedPaperVo getRedPaperVo() {
        return (RedPaperVo) getIntent().getSerializableExtra(RED_PAPER_VO);
    }

    @NonNull
    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    public Integer getStatusBarColor() {
        return getResources().getColor(R.color.white);
    }

    @Override
    protected Boolean getStatusBarLightMode() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wallet_redpaper_activity;
    }

    @Override
    protected Integer getBackgroundColor() {
        return Color.parseColor("#F8F8F8");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RedPaperVo redPaperVo = getRedPaperVo();
        if (redPaperVo.type == RedPaperType.Group) {
            binding.rvIndicatorContainer.setVisibility(View.VISIBLE);
        } else if (redPaperVo.type == RedPaperType.P2P) {
            binding.rvIndicatorContainer.setVisibility(View.GONE);
        } else {
            return;
        }
        // viewPager
        binding.vpPager.setAdapter(new RedPacketFragmentAdapter(getSupportFragmentManager(), redPaperVo.type));
        binding.vpPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                KeyboardUtils.hideSoftInput(RedPaperActivity.this);
            }
        });
        // indicator
        RedPacketTabAdapter tabAdapter = new RedPacketTabAdapter(binding.rvIndicator);
        tabAdapter.setViewPager(binding.vpPager);
        // 标题栏 - 右侧按钮
        binding.titleBar.getTvRight().setOnClickListener(view -> RedPacketActivity.start(RedPaperActivity.this));

    }
}
