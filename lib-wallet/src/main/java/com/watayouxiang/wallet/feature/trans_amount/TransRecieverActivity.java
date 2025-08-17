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
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;
import com.watayouxiang.db.dao.TransAmountCrud;
import com.watayouxiang.db.table.TransAmountStatusTable;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.TransAmountBackReq;
import com.watayouxiang.httpclient.model.request.TransAmountPayReq;
import com.watayouxiang.httpclient.model.request.TransAmountRecieverReq;
import com.watayouxiang.httpclient.model.response.TransDetailResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.tools.MoneyUtils;

public class TransRecieverActivity extends TioActivity {
    public TransDetailResp transDetailResp;

    private TextView etAmount;
    private TextView tvTransTime;

    private String fromNick;
    private String transId;

    static TransAmountRecieverListener transAmountRecieverListener;

    public static void setTransAmountRecieverListener(TransAmountRecieverListener transAmountRecieverListener) {
        TransRecieverActivity.transAmountRecieverListener = transAmountRecieverListener;
    }

    public static void start(Context context, TransDetailResp transDetailResp, String chatlinkId) {
        Intent starter = new Intent(context, TransRecieverActivity.class);
        starter.putExtra("transDetailResp", transDetailResp);
        starter.putExtra("chatlinkId", chatlinkId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucent(this);
        setContentView(R.layout.activity_trans_reciever);
        transDetailResp = (TransDetailResp) getIntent().getSerializableExtra("transDetailResp");
        fromNick = transDetailResp.fromNick;
        etAmount = findViewById(R.id.et_amount);
        tvTransTime = findViewById(R.id.trans_time);
        transId = String.valueOf(transDetailResp.id);

        etAmount.setText(MoneyUtils.fen2yuan(String.valueOf(transDetailResp.amount)));
        if (transDetailResp.createtime != null){
            tvTransTime.setText(transDetailResp.createtime);
        }
//        tvTransTime

        findViewById(R.id.btnSure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransAmountRecieverReq recieverReq = new TransAmountRecieverReq(transId, getIntent().getStringExtra("chatlinkId"));
                recieverReq.setCancelTag(this);
                recieverReq.post(new TioCallback<TransDetailResp>() {
                    @Override
                    public void onTioSuccess(TransDetailResp transDetailResp) {
                        int status = transDetailResp.status;
                        TransAmountStatusTable transAmountStatusTable = TransAmountCrud.queryByRedId(transDetailResp.id);
                        if (transAmountStatusTable == null){
                            transAmountStatusTable = new TransAmountStatusTable();
                            transAmountStatusTable.setTransId(transDetailResp.id);
                            transAmountStatusTable.setTransStatus(status);
                            TransAmountCrud.insert(transAmountStatusTable);
                        }else {
                            transAmountStatusTable.setTransStatus(status);
                            TransAmountCrud.update(transAmountStatusTable);
                        }
                        if (transAmountRecieverListener != null){
                            transAmountRecieverListener.back(1);
                        }
                        TransHasRecieverActivity.start(getActivity(), transDetailResp, getIntent().getStringExtra("chatlinkId"));
                        finish();
                    }

                    @Override
                    public void onTioError(String msg) {
                        ToastUtils.showShort(msg);
                    }
                });
            }
        });

        findViewById(R.id.to_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EasyOperDialog.Builder(String.format(getString(R.string.tuihuanzhuanzhang), fromNick))
                        .setPositiveBtnTxt(getString(R.string.tuihuan))
                        .setNegativeBtnTxt(getString(R.string.zanbutuihuan))
                        .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                            @Override
                            public void onClickPositive(View view, EasyOperDialog dialog) {
                                dialog.dismiss();
                                toBack();
                            }

                            @Override
                            public void onClickNegative(View view, EasyOperDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show_unCancel(getActivity());
            }
        });
    }

    private void toBack(){
        TransAmountBackReq transAmountBackReq = new TransAmountBackReq(transId, getIntent().getStringExtra("chatlinkId"));
        transAmountBackReq.setCancelTag(this);
        transAmountBackReq.post(new TioCallback<TransDetailResp>() {
            @Override
            public void onTioSuccess(TransDetailResp transDetailResp) {
                int status = transDetailResp.status;
                TransAmountStatusTable transAmountStatusTable = TransAmountCrud.queryByRedId(transDetailResp.id);
                if (transAmountStatusTable == null){
                    transAmountStatusTable.setTransId(transDetailResp.id);
                    transAmountStatusTable.setTransStatus(status);
                    TransAmountCrud.insert(transAmountStatusTable);
                }else {
                    transAmountStatusTable.setTransStatus(status);
                    TransAmountCrud.update(transAmountStatusTable);
                }
                if (transAmountRecieverListener != null){
                    transAmountRecieverListener.back(2);
                }
                finish();
            }

            @Override
            public void onTioError(String msg) {
                ToastUtils.showShort(msg);
            }
        });
    }

    public interface TransAmountRecieverListener{
        //1-收款 2-退还
        void back(int flag);
    }
}
