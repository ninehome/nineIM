package com.watayouxiang.wallet.yanxun.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.UserRecieverAccountReq;
import com.watayouxiang.httpclient.model.response.UserRecieverAccountResp;
import com.watayouxiang.wallet.R;
import com.watayouxiang.wallet.yanxun.entity.ScanWithDrawSelectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * 选择提现方式
 */
public class ScanWithdrawListActivity extends BaseListActivity {
    List<UserRecieverAccountResp.UserWithdrawAccount> scanWithDrawSelectTypes = new ArrayList<>();
    private boolean isEdit;
    private boolean isResumed;

    @Override
    protected void onResume() {
        super.onResume();
        if (isResumed) {
            initDatas(0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResumed = true;
    }

    @Override
    public void initView() {
        // 添加两个空数据，表示去添加item
        scanWithDrawSelectTypes.add(new UserRecieverAccountResp.UserWithdrawAccount());
        scanWithDrawSelectTypes.add(new UserRecieverAccountResp.UserWithdrawAccount());
        initActionBar();
    }

    private void initActionBar() {
//        findViewById(R.id.iv_title_left).setOnClickListener(v -> finish());
        WtTitleBar tvTitle = findViewById(R.id.titleBar);
        tvTitle.setTitle(getString(R.string.select_withdraw_type));
        TextView mTvRightTitle = tvTitle.getTvRight();
        mTvRightTitle.setVisibility(View.VISIBLE);
        mTvRightTitle.setText(getString(R.string.edit));
        mTvRightTitle.setOnClickListener(v -> {
            isEdit = !isEdit;
            mTvRightTitle.setText(isEdit ? getString(R.string.cancel) : getString(R.string.edit));
            notifyDataSetChanged();
        });
    }

    @Override
    public void initDatas(int pager) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageIndex", String.valueOf(pager));
        params.put("pageSize", String.valueOf(PAGE_SIZE));

        UserRecieverAccountReq recieverAccountReq = new UserRecieverAccountReq();
        recieverAccountReq.setCancelTag(this);
        recieverAccountReq.get(new TioCallback<UserRecieverAccountResp>() {
            @Override
            public void onTioSuccess(UserRecieverAccountResp userRecieverAccountResp) {
                scanWithDrawSelectTypes.clear();
                scanWithDrawSelectTypes.add(new UserRecieverAccountResp.UserWithdrawAccount());
                scanWithDrawSelectTypes.add(new UserRecieverAccountResp.UserWithdrawAccount());
                            scanWithDrawSelectTypes.addAll(userRecieverAccountResp.getData());
                            update(scanWithDrawSelectTypes);
                            more = false;
            }

            @Override
            public void onTioError(String msg) {
                LogUtils.e("==>"+msg);
            }
        });

//        HttpUtils.get().url(coreManager.getConfig().MANUAL_PAY_GET_WITHDRAW_ACCOUNT_LIST)
//                .params(params)
//                .build()
//                .execute(new ListCallback<ScanWithDrawSelectType>(ScanWithDrawSelectType.class) {
//                    @Override
//                    public void onResponse(ArrayResult<ScanWithDrawSelectType> result) {
//                        if (Result.checkSuccess(mContext, result)) {
//                            if (pager == 0) {
//                                scanWithDrawSelectTypes.clear();
//                                scanWithDrawSelectTypes.add(new ScanWithDrawSelectType());
//                                scanWithDrawSelectTypes.add(new ScanWithDrawSelectType());
//                            }
//                            scanWithDrawSelectTypes.addAll(result.getData());
//                            update(scanWithDrawSelectTypes);
//                            if (result.getData().size() != PAGE_SIZE) {
//                                more = false;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e) {
//                        update(scanWithDrawSelectTypes);
//                    }
//                });
    }

    @Override
    public RecyclerView.ViewHolder initHolder(ViewGroup parent) {
        View v = mInflater.inflate(R.layout.item_scan_withdraw_add, parent, false);
        return new ScanWithDrawSelectTypeViewHolder(v);
    }

    @Override
    public void fillData(RecyclerView.ViewHolder holder, int position) {
        ScanWithDrawSelectTypeViewHolder drawSelectTypeViewHolder = (ScanWithDrawSelectTypeViewHolder) holder;
        if (position == 0 || position == 1) {
            drawSelectTypeViewHolder.addTv.setVisibility(View.VISIBLE);
            drawSelectTypeViewHolder.typeIv.setVisibility(View.GONE);
            drawSelectTypeViewHolder.typeTv.setVisibility(View.GONE);
            drawSelectTypeViewHolder.nextIv.setVisibility(View.GONE);
            drawSelectTypeViewHolder.addTv.setText(position == 0 ? getString(R.string.select_withdraw_add_alipay_account) : getString(R.string.select_withdraw_add_band_card_account));
            drawSelectTypeViewHolder.item.setOnClickListener(v -> ScanWithdrawAddActivity.start(this, position + 1));
            drawSelectTypeViewHolder.cc.setVisibility(View.VISIBLE);
        } else {
            UserRecieverAccountResp.UserWithdrawAccount drawSelectType = scanWithDrawSelectTypes.get(position);
            drawSelectTypeViewHolder.addTv.setVisibility(View.GONE);
            drawSelectTypeViewHolder.typeIv.setVisibility(View.VISIBLE);
            drawSelectTypeViewHolder.typeTv.setVisibility(View.VISIBLE);
            drawSelectTypeViewHolder.nextIv.setVisibility(isEdit ? View.VISIBLE : View.GONE);
            drawSelectTypeViewHolder.cc.setVisibility(View.GONE);
            if (drawSelectType.getAccounttype() == 2) {//支付宝
                drawSelectTypeViewHolder.typeIv.setImageResource(R.drawable.icon_pay01);
                drawSelectTypeViewHolder.typeTv.setText(drawSelectType.getAccountno());
            } else {// 银行卡
                drawSelectTypeViewHolder.typeIv.setImageResource(R.mipmap.ic_band_small);
                drawSelectTypeViewHolder.typeTv.setText(drawSelectType.getBankname() + "(" + drawSelectType.getAccountno() + ")");
            }
            drawSelectTypeViewHolder.item.setOnClickListener(v -> {
                if (isEdit) {
                    ScanWithdrawUpdateActivity.start(this, new Gson().toJson(drawSelectType));
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("drawSelectType", new Gson().toJson(drawSelectType));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    public class ScanWithDrawSelectTypeViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout item;
        public TextView addTv;
        public ImageView typeIv;
        public TextView typeTv;
        public ImageView nextIv;
        public View cc;

        public ScanWithDrawSelectTypeViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_add);
            addTv = itemView.findViewById(R.id.add_tv);
            typeIv = itemView.findViewById(R.id.type_iv);
            typeTv = itemView.findViewById(R.id.type_tv);
            nextIv = itemView.findViewById(R.id.next_iv);
            cc = itemView.findViewById(R.id.cc);
        }
    }
}
