package com.tiocloud.chat.feature.settings.mvp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.account.pwd.ModifyPwdActivity;
import com.tiocloud.chat.feature.account.pwd.OtherModifyPwdActivity;
import com.tiocloud.chat.feature.account.safe.AboutActivity;
import com.tiocloud.chat.feature.account.safe.AccountSafeActivity;
import com.tiocloud.chat.feature.account.safe.DisturbActivity;
import com.tiocloud.chat.feature.account.safe.PrivacyActivity;
import com.tiocloud.chat.feature.main.MainActivity;
import com.tiocloud.chat.feature.settings.SettingsActivity;
import com.tiocloud.account.mvp.logout.LogoutContract;
import com.tiocloud.account.mvp.logout.LogoutPresenter;
import com.tiocloud.chat.service.LiveSerice;
import com.tiocloud.chat.util.AppUpdateTool;
import com.tiocloud.chat.util.LanguageType;
import com.tiocloud.chat.util.MultiLanguageService;
import com.tiocloud.chat.util.PreferencesUtil;
import com.tiocloud.chat.yanxun.groupsend.SelectGroupSendActivity;
import com.watayouxiang.androidutils.listener.SimpleOnCheckedChangeListener;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.db.dao.CurrUserTableCrud;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UpdateRemindReq;
import com.watayouxiang.httpclient.model.response.SysVersionResp;
import com.watayouxiang.httpclient.model.response.UserCurrResp;
import com.watayouxiang.imclient.utils.DeviceUtils;
import com.watayouxiang.wallet.yanxun.utisl.ScreenUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * author : TaoWang
 * date : 2020-02-19
 * desc :
 */
public class SettingsPresenter extends SettingsContract.Presenter {

    private final LogoutPresenter logoutPresenter;
    private AppUpdateTool appUpdateTool;

    public SettingsPresenter(SettingsContract.View view) {
        super(new SettingsModel(), view);
        logoutPresenter = new LogoutPresenter(new LogoutContract.View() {
        });
    }

    @Override
    public void detachView() {
        super.detachView();
        logoutPresenter.detachView();
        if (appUpdateTool != null) {
            appUpdateTool.release();
        }
    }

    // ====================================================================================
    // ui
    // ====================================================================================

    @Override
    public void init() {
        final SettingsActivity.ViewHolder viewHolder = getView().getViewHolder();
        if (viewHolder == null) return;
        // 版本号
        viewHolder.tv_version.setText(String.format(Locale.getDefault(), "%s", DeviceUtils.getAppVersion(getView().getActivity())));
        // 退出登录
        viewHolder.tv_logoutBtn.setOnClickListener(v -> logoutPresenter.showLogoutDialog(getView().getActivity()));
        viewHolder.tv_login_exit.setOnClickListener(v -> logoutPresenter.showLogoutDialog(getView().getActivity()));
        // 清除历史消息
        viewHolder.rl_clearHistoryMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TioToast.showShort("开发中...");
            }
        });
        // 检测版本更新
        viewHolder.rl_version.setOnClickListener(v -> checkAppUpdate());
        viewHolder.rl_notice.setOnClickListener(v -> goToSet());
        viewHolder.rl_version.setOnLongClickListener(view -> {
            String outApkTime = getModel().getOutApkTime(view.getContext());
            if (!TextUtils.isEmpty(outApkTime)) {
                TioToast.showShort("打包时间：" + outApkTime);
                return true;
            }
            return false;
        });
        // 初始化 ui
//        viewHolder.tv_account.setText("账号：");
        viewHolder.switch_verifyAddFriend.setEnabled(false);
        viewHolder.switch_searchMeAuth.setEnabled(false);
        viewHolder.switch_msgRemind.setEnabled(false);
        viewHolder.switch_msgRemind.setEnabled(false);
        // 加载数据
        loadRemoteData(viewHolder);

        viewHolder.rl_group_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectGroupSendActivity.start(getView().getActivity());
            }
        });

        viewHolder.rl_account_safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountSafeActivity.startActivity(getView().getActivity());
            }
        });

        viewHolder.rl_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyActivity.startActivity(getView().getActivity());
            }
        });

        viewHolder.rl_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.startActivity(getView().getActivity());
            }
        });

        viewHolder.rl_disturb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisturbActivity.startActivity(getView().getActivity());
            }
        });

        viewHolder.rl_language_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBotomDialog();
            }
        });

    }

    public void showBotomDialog(){
        Dialog bottomDialog = new Dialog(getView().getActivity(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getView().getActivity()).inflate(
                R.layout.tio_language_type_dialog, null);
        TextView tv_chinese = contentView.findViewById(R.id.tv_chinese);
        tv_chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                MultiLanguageService.changeLanguage(getView().getActivity(), LanguageType.LANGUAGE_ZH_CN);
                getView().getActivity().startActivity(new Intent(getView().getActivity(), MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
        TextView tv_vi = contentView.findViewById(R.id.tv_vi);
        tv_vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                MultiLanguageService.changeLanguage(getView().getActivity(), LanguageType.LANGUAGE_VI);
                getView().getActivity().startActivity(new Intent(getView().getActivity(), MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
        TextView tv_cancel = contentView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        bottomDialog.setContentView(contentView);
        bottomDialog.setCanceledOnTouchOutside(false);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidth(getView().getActivity());
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialogAnimation);
        bottomDialog.getWindow().setDimAmount(0);
        bottomDialog.show();
    }
    private void loadRemoteData(final SettingsActivity.ViewHolder viewHolder) {
        getModel().requestCurrUserInfo(new BaseModel.DataProxy<UserCurrResp>() {
            @Override
            public void onSuccess(UserCurrResp resp) {
                // 账号
                String account = resp.phone;
//                viewHolder.tv_account.setText(String.format(Locale.getDefault(), "账号：%s", account));
                // 加我好友时需要验证
                boolean verifyAddFriend = resp.fdvalidtype == 1;
                viewHolder.switch_verifyAddFriend.setEnabled(true);
                viewHolder.switch_verifyAddFriend.setChecked(verifyAddFriend);
                viewHolder.switch_verifyAddFriend.setOnCheckedChangeListener(mSwitchCheckedChangeListener);
                // 允许别人搜索到我
                boolean searchMeAuth = resp.searchflag == 1;
                viewHolder.switch_searchMeAuth.setEnabled(true);
                viewHolder.switch_searchMeAuth.setChecked(searchMeAuth);
                viewHolder.switch_searchMeAuth.setOnCheckedChangeListener(mSwitchCheckedChangeListener);
                // 消息提醒开关
                boolean isMsgRemind = resp.msgremindflag == 1;
                viewHolder.switch_msgRemind.setEnabled(true);
                viewHolder.switch_msgRemind.setChecked(isMsgRemind);
                viewHolder.switch_msgRemind.setOnCheckedChangeListener(mSwitchCheckedChangeListener);

                boolean isMsgNotice = PreferencesUtil.getBoolean("msg_notice", true);
                viewHolder.switch_msgNotice.setEnabled(true);
                viewHolder.switch_msgNotice.setChecked(isMsgNotice);
                viewHolder.switch_msgNotice.setOnCheckedChangeListener(mSwitchCheckedChangeListener);
            }
        });
    }

    private final CompoundButton.OnCheckedChangeListener mSwitchCheckedChangeListener = new SimpleOnCheckedChangeListener() {
        @Override
        public void onUserCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            super.onUserCheckedChanged(compoundButton, isChecked);

            final SettingsActivity.ViewHolder viewHolder = getView().getViewHolder();
            if (viewHolder == null) return;

            if (compoundButton == viewHolder.switch_verifyAddFriend) {
                updateValidReq(isChecked, viewHolder.switch_verifyAddFriend);
            } else if (compoundButton == viewHolder.switch_searchMeAuth) {
                updateSearchFlagReq(isChecked, viewHolder.switch_searchMeAuth);
            } else if (compoundButton == viewHolder.switch_msgRemind) {
                updateMsgRemindFlagReq(isChecked, viewHolder.switch_msgRemind);
            }else if (compoundButton == viewHolder.switch_msgNotice){
                if (isChecked){
                    PreferencesUtil.saveBoolean("msg_notice", true);
                    goToSet();
                }else {
                    PreferencesUtil.saveBoolean("msg_notice", false);
                }
                if (PreferencesUtil.getBoolean("msg_notice", true)){
                    LiveSerice.start(compoundButton.getContext());
                }
            }
        }
    };
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private void goToSet(){
//        if (isNotificationEnabled(getView().getActivity())){
//            return;
//        }
        try {
            // 根据通知栏开启权限判断结果，判断是否需要提醒用户跳转系统通知管理页面
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getView().getActivity().getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, getView().getActivity().getString(R.string.new_message_inform));
            //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
            intent.putExtra("app_package", getView().getActivity().getPackageName());
            intent.putExtra("app_uid", getView().getActivity().getString(R.string.new_message_inform));
            getView().getActivity().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常则跳转到应用设置界面
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getView().getActivity().getPackageName(), null);
            intent.setData(uri);
            getView().getActivity().startActivity(intent);
        }
    }

    // ====================================================================================
    // 版本更新
    // ====================================================================================

    private void checkAppUpdate() {
        if (appUpdateTool == null) {
            appUpdateTool = new AppUpdateTool(getView().getActivity()) {
                @Override
                public void onCheckUpdateSuccess(SysVersionResp sysVersionResp) {
                    super.onCheckUpdateSuccess(sysVersionResp);
                    if (sysVersionResp.getUpdateflag() == 2) {
                        TioToast.showShort(getView().getActivity().getString(R.string.current_new_version));
                    }
                }
            };
        }
        appUpdateTool.checkUpdate();
    }

    // ====================================================================================
    // 请求操作
    // ====================================================================================

    private void updateMsgRemindFlagReq(boolean isChecked, CheckBox switch_msgRemind) {
        // 消息提醒开关
        UpdateRemindReq updateRemindReq = new UpdateRemindReq(isChecked ? "1" : "2");
        updateRemindReq.setCancelTag(this);
        updateRemindReq.post(new TioCallback<Void>() {
            @Override
            public void onTioSuccess(Void aVoid) {
                try {
                    int remindflag = Integer.parseInt(updateRemindReq.getRemindflag());
                    // 更新当前用户的消息开关状态
                    CurrUserTableCrud.curr_update_msgremindflag(remindflag);
                } catch (Exception ignored) {
                }
            }

            @Override
            public void onTioError(String msg) {
                switch_msgRemind.setChecked(!isChecked);
            }
        });
    }

    private void updateSearchFlagReq(final boolean isChecked, final CheckBox switch_searchMeAuth) {
        // 允许别人搜索到我
        getModel().updateSearchFlagReq(isChecked, new BaseModel.DataProxy<String>() {
            @Override
            public void onSuccess(String s) {
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                switch_searchMeAuth.setChecked(!isChecked);
            }
        });
    }

    private void updateValidReq(final boolean isChecked, final CheckBox switch_verifyAddFriend) {
        // 加我好友时需要验证
        getModel().updateValidReq(isChecked, new BaseModel.DataProxy<String>() {
            @Override
            public void onSuccess(String s) {
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                switch_verifyAddFriend.setChecked(!isChecked);
            }
        });
    }
}
