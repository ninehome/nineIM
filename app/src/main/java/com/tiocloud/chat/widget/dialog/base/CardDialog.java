package com.tiocloud.chat.widget.dialog.base;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;

/**
 * author : TaoWang
 * date : 2020/4/13
 * desc :
 */
public class CardDialog extends BaseDialog {
    public TioImageView hiv_avatar;
    public TextView tv_positiveBtn;
    private TextView tv_negativeBtn;
    public TextView tv_usrName;
    public TextView tv_title;

    public CardDialog(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(LayoutInflater.from(context).inflate(R.layout.tio_card_dialog_layout, null));
        initViews();
    }

    private void initViews() {
        tv_title = findViewById(R.id.tv_title);
        tv_usrName = findViewById(R.id.tv_usrName);
        tv_negativeBtn = findViewById(R.id.tv_negativeBtn);
        tv_positiveBtn = findViewById(R.id.tv_positiveBtn);
        hiv_avatar = findViewById(R.id.hiv_avatar);

        tv_negativeBtn.setOnClickListener(view -> dismiss());
    }
}
