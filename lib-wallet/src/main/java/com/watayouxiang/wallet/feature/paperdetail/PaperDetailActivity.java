package com.watayouxiang.wallet.feature.paperdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watayouxiang.androidutils.page.easy.EasyDarkActivity;
import com.watayouxiang.androidutils.util.ClickUtils;
import com.watayouxiang.httpclient.model.response.PayRedInfoResp;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.databinding.WalletPaperdetailActivityBinding;
import com.watayouxiang.wallet.feature.paperdetail.adapter.ListAdapter;
import com.watayouxiang.wallet.feature.paperdetail.adapter.ListModel;
import com.watayouxiang.wallet.feature.paperdetail.adapter.ReceiveInfoItem;
import com.watayouxiang.wallet.feature.redpacket.RedPacketActivity;
import com.watayouxiang.wallet.feature.wallet.WalletActivity;
import com.watayouxiang.wallet.tools.MoneyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/13
 *     desc   : 红包详情（未领取、领取完、过期）
 * </pre>
 */
public class PaperDetailActivity extends EasyDarkActivity<WalletPaperdetailActivityBinding> {

    public final ObservableField<String> fromInfo = new ObservableField<>("小生的红包");
    public final ObservableField<String> giftInfo = new ObservableField<>("恭喜发财，大吉大利");

    private ListAdapter listAdapter;
    private static final String KEY_SERIAL_NUMBER = "key_serial_Number";
    private PaperDetailViewModel viewModel;

    public static void start(Context context, String serialNumber) {
        Intent starter = new Intent(context, PaperDetailActivity.class);
        starter.putExtra(KEY_SERIAL_NUMBER, serialNumber);
        context.startActivity(starter);
    }

    public String getSerialNumber() {
        return getIntent().getStringExtra(KEY_SERIAL_NUMBER);
    }

    @NonNull
    @Override
    protected View getStatusBarHolder() {
        return binding.statusBar;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wallet_paperdetail_activity;
    }

    @Override
    public Integer getStatusBarColor() {
//        return getResources().getColor(R.color.red_ff5e5e);
        return Color.parseColor("#ffff5440");
    }

    private void convertReceiveInfo(ReceiveInfoItem item) {
        TextView tv_money = findViewById(R.id.tv_money);
        tv_money.setText(item.getInfo());

        TextView tv_tipInfo = findViewById(R.id.tv_tipInfo);
        tv_tipInfo.setOnClickListener(view -> {
            if (ClickUtils.isViewSingleClick(view)) {
                Context context = view.getContext();
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    WalletActivity.start(activity);
                }
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setVm(this);
        viewModel = newViewModel(PaperDetailViewModel.class);

        // 重置ui
        resetUI();
        // 刷新数据
        viewModel.refresh(this);
    }

    private void resetUI() {
        // 标题栏 - 右侧按钮
        binding.titleBar.getTvRight().setOnClickListener(view -> {
            if (ClickUtils.isViewSingleClick(view)) {
                RedPacketActivity.start(PaperDetailActivity.this);
            }
        });
        // 头像
//        binding.ivAvatar.loadStatic(null, 2);
        // 拼手气红包标志 显隐
        setPinDrawableVisibility(false);
        // 红包来源
        fromInfo.set("");
        // 祝福语
        giftInfo.set("");
        // 列表初始化
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new ListAdapter(null);
        binding.recyclerView.setAdapter(listAdapter);
    }

    private void setPinDrawableVisibility(boolean visibility) {
        if (visibility) {
            Drawable drawable = ResourceUtils.getDrawable(R.mipmap.icon_pin);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            binding.tvFromInfo.setCompoundDrawables(null, null, drawable, null);
        } else {
            binding.tvFromInfo.setCompoundDrawables(null, null, null, null);
        }
    }

    public void setRefreshData(List<ListModel> models) {
        List<ListModel> newModels = new ArrayList<>();
        if (models != null && models.size() > 0){
            for (ListModel listModel : models){
                if (listModel.getItemType() == ListModel.ITEM_RECEIVE_INFO){
                    convertReceiveInfo(listModel.getReceiveInfoItem());
                }else {
                    newModels.add(listModel);
                }
            }
        }
        listAdapter.setNewData(newModels);
    }

    /**
     * 红包信息通知
     */
    public void onRedInfoResp(PayRedInfoResp.InfoBean info) {
        // 发红包人昵称
        String nick = info.getNick();
        // 发红包人祝福语
        String remark = info.getRemark();
        String avatar = info.getAvatar();
        avatar = HttpCache.getResUrl(avatar);
        // 是否为拼手气红包
        boolean isPinRed = info.getMode() == 2;

        // 头像
        binding.ivAvatar.loadStatic(avatar);
        // 红包来源
        fromInfo.set(nick+getString(R.string.dehongbao));
        // 祝福语
        giftInfo.set(StringUtils.null2Length0(remark));
        // 拼手气红包标志 显隐
        setPinDrawableVisibility(isPinRed);

        if (info.getStatus().equals("SEND")){
            TextView tv_money = findViewById(R.id.tv_money);
            tv_money.setText(MoneyUtils.fen2yuan(String.valueOf(info.getAmount())));
            TextView tv_tipInfo = findViewById(R.id.tv_tipInfo);
            tv_tipInfo.setVisibility(View.INVISIBLE);
        }
    }
}
