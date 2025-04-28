package com.tiocloud.chat.feature.webrtc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.BarUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.databinding.TioCallActivityBinding;
import com.tiocloud.chat.feature.webrtc.data.CallConst;
import com.tiocloud.chat.feature.webrtc.data.CallNtf;
import com.tiocloud.chat.feature.webrtc.data.CallReq;
import com.tiocloud.chat.feature.webrtc.mvp.CallContract;
import com.tiocloud.chat.feature.webrtc.mvp.CallPresenter;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.page.TioFragment;

/**
 * author : TaoWang
 * date : 2020/5/20
 * desc :
 */
public class CallActivity extends TioActivity implements CallContract.View {

    private TioCallActivityBinding binding;
    private CallPresenter presenter;
    private Boolean bellVibrateOpen = null;

    /**
     * 去电
     *
     * @param context
     * @param callReq
     */
    public static void start(Context context, CallReq callReq) {
        Intent intent = new Intent(context, CallActivity.class);
        intent.putExtra(CallConst.EXTRA_CALL_REQ, callReq);
        openActivity(context, intent);
    }

    /**
     * 来电
     *
     * @param context
     * @param callNtf
     */
    public static void start(Context context, CallNtf callNtf) {
        Intent intent = new Intent(context, CallActivity.class);
        intent.putExtra(CallConst.EXTRA_CALL_NTF, callNtf);
        openActivity(context, intent);
    }

    private static void openActivity(Context context, Intent intent) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    public CallNtf getCallNtf() {
        return (CallNtf) getIntent().getSerializableExtra(CallConst.EXTRA_CALL_NTF);
    }

    @Override
    public CallReq getCallReq() {
        return (CallReq) getIntent().getSerializableExtra(CallConst.EXTRA_CALL_REQ);
    }

    @Override
    public boolean isOpenMsgRemind() {
        if (bellVibrateOpen == null) {
            bellVibrateOpen = CurrUserTableCrud.curr_isOpenMsgRemind(true);
        }
        return bellVibrateOpen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0xFF000000));
        BarUtils.setStatusBarLightMode(this, false);
        BarUtils.transparentStatusBar(this);

        binding = DataBindingUtil.setContentView(this, R.layout.tio_call_activity);
        presenter = new CallPresenter(this);
        presenter.init();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public TioCallActivityBinding getBinding() {
        return binding;
    }

    @Override
    public void replaceFragment(TioFragment fragment) {
        super.replaceFragment(fragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
