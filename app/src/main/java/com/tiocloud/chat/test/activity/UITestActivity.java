package com.tiocloud.chat.test.activity;

import android.graphics.Color;
import android.widget.TextView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.util.KeywordUtil;
import com.watayouxiang.androidutils.widget.dialog.confirm.TioConfirmDialog;
import com.tiocloud.chat.widget.fragmentDialog.TestBottomDialog;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.demoshell.ListActivity;
import com.watayouxiang.demoshell.ListData;
import com.watayouxiang.wallet.widget.RedPaperDialog;

/**
 * author : TaoWang
 * date : 2020-01-08
 * desc :
 */
public class UITestActivity extends ListActivity {
    @Override
    protected ListData getListData() {
        return new ListData()
                .addSection("KeywordUtil")
                .addClick("Lemo", v -> {
                    TextView tv = (TextView) v;
                    tv.setText(KeywordUtil.matcherSearchTitle(
                            Color.parseColor("#FF4C94E8"),
                            tv.getText().toString(),
                            "le"));
                })
                .addSection("Dialog")
                .addClick("show EasyProgressDialog", v -> SingletonProgressDialog.show_cancelable(v.getContext(), getString(R.string.loading)))
                .addClick("show TioConfirmDialog", v -> new TioConfirmDialog("注册申请成功，激活链接已发送到您的注册 邮箱watayouxian@qq.com，有效时间为15分钟（邮件可能在您的垃圾邮箱）", (view, dialog) -> dialog.dismiss()).show_unCancel(v.getContext()))
                .addSection("BottomSheetDialogFragment")
                .addClick("TestBottomDialog", v -> new TestBottomDialog().show(getSupportFragmentManager()))
                .addSection("新版 Dialog")
                .addClick("显示红包弹窗", v -> new RedPaperDialog(v.getContext(), null).show())
                ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SingletonProgressDialog.show_cancelable(this, getString(R.string.loading));
    }
}
