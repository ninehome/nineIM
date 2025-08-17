package com.tiocloud.chat.widget.dialog.base;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.tiocloud.chat.R;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UpdateSexReq;

/**
 * author : TaoWang
 * date : 2020/3/3
 * desc : 性别修改弹窗
 */
public class GenderDialog extends BaseDialog implements View.OnClickListener {
    private View tv_unknown;
    private View v_line01;
    private View tv_man;
    private View v_line02;
    private View tv_woman;
    private View tv_cancel;

    private final TioCallback<Void> mCallback;

    public GenderDialog(final Context context, TioCallback<Void> callback) {
        super(context);
        mCallback = callback;
        setAnimation(R.style.tio_bottom_dialog_anim);
        setFullScreenWidth();
        setGravity(Gravity.BOTTOM);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(LayoutInflater.from(context).inflate(R.layout.tio_bottom_dialog_gender, null));
        initViews();
    }

    private void initViews() {
        tv_unknown = findViewById(R.id.tv_unknown);
        v_line01 = findViewById(R.id.v_line01);
        tv_man = findViewById(R.id.tv_man);
        v_line02 = findViewById(R.id.v_line02);
        tv_woman = findViewById(R.id.tv_woman);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_unknown.setOnClickListener(this);
        tv_man.setOnClickListener(this);
        tv_woman.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        TioHttpClient.cancel(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tv_cancel) {
            dismiss();
        } else if (v == tv_unknown) {
            reqUpdateSex("3");
        } else if (v == tv_man) {
            reqUpdateSex("1");
        } else if (v == tv_woman) {
            reqUpdateSex("2");
        }
    }

    private void reqUpdateSex(String sex) {
        UpdateSexReq req = new UpdateSexReq(sex);
        req.setCancelTag(this);
        req.post(mCallback);
    }

    /**
     * @param sex 1：男；2：女；3:保密
     */
    public void setSex(int sex) {
        switch (sex) {
            case 1:
                tv_unknown.setSelected(false);
                tv_man.setSelected(true);
                tv_woman.setSelected(false);
                break;
            case 2:
                tv_unknown.setSelected(false);
                tv_man.setSelected(false);
                tv_woman.setSelected(true);
                break;
            case 3:
                tv_unknown.setSelected(true);
                tv_man.setSelected(false);
                tv_woman.setSelected(false);
                break;
            default:
                tv_unknown.setSelected(false);
                tv_man.setSelected(false);
                tv_woman.setSelected(false);
                break;
        }
    }
}
