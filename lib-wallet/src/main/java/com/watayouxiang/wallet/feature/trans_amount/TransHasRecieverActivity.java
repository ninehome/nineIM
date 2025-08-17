package com.watayouxiang.wallet.feature.trans_amount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jaeger.library.StatusBarUtil;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.db.dao.TransAmountCrud;
import com.watayouxiang.db.table.TransAmountStatusTable;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.TransAmountRecieverReq;
import com.watayouxiang.httpclient.model.response.TransDetailResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.feature.wallet.WalletActivity;
import com.watayouxiang.wallet.tools.MoneyUtils;

public class TransHasRecieverActivity extends TioActivity {
    public TransDetailResp transDetailResp;

    private TextView etAmount;
    private TextView tvTransTime;
    private TextView tvRecieverTime;

    public static void start(Context context, TransDetailResp transDetailResp, String chatlinkId) {
        Intent starter = new Intent(context, TransHasRecieverActivity.class);
        starter.putExtra("transDetailResp", transDetailResp);
        starter.putExtra("chatlinkId", chatlinkId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucent(this);
        setContentView(R.layout.activity_trans_has_reciever);
        transDetailResp = (TransDetailResp) getIntent().getSerializableExtra("transDetailResp");
        etAmount = findViewById(R.id.et_amount);
        tvTransTime = findViewById(R.id.trans_time);
        tvRecieverTime = findViewById(R.id.reciever_time);
        String transId = String.valueOf(transDetailResp.id);

        etAmount.setText(MoneyUtils.fen2yuan(String.valueOf(transDetailResp.amount)));
        tvTransTime.setText(transDetailResp.createtime);
        tvRecieverTime.setText(transDetailResp.recievertime);
        findViewById(R.id.to_wallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WalletActivity.start(getActivity());
                finish();
            }
        });
    }
}
