package com.tiocloud.chat.yanxun.lable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.cache.CacheMode;
import com.tiocloud.chat.R;
import com.tiocloud.chat.yanxun.group.MainGroupActivity;
import com.tiocloud.chat.yanxun.view.LineBreakLayout;

import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.LablelListReq;
import com.watayouxiang.httpclient.model.response.LableListResp;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签界面
 */
public class LableActivity extends TioActivity implements View.OnClickListener {
    private LineBreakLayout lineBreakLayout;

    public static void start(Context context) {
        Intent starter = new Intent(context, LableActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lable);
        lineBreakLayout = findViewById(R.id.label_view);

        WtTitleBar titleBar = findViewById(R.id.titleBar);
        titleBar.getTvRight().setOnClickListener(clickCreateLabelListener);
        titleBar.getIvRight().setOnClickListener(clickCreateLabelListener);
        refreshLabelListFromService();

        lineBreakLayout.setOnItemClickListener(new LineBreakLayout.OnItemClickListener() {
            @Override
            public void back(Label label) {
                Intent intent = new Intent(getActivity(), CreateLabelActivity.class);
                intent.putExtra("isEditLabel", true);
                intent.putExtra("labelId", label.getGroupId());
                intent.putExtra("oldLable", JSON.toJSONString(label));
                startActivityForResult(intent, 0x01);
            }
        });
        lineBreakLayout.setOnItemDeleteListener(new LineBreakLayout.OnItemDeleteListener() {
            @Override
            public void back(Label label) {

            }
        });
    }

    View.OnClickListener clickCreateLabelListener  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), CreateLabelActivity.class);
            intent.putExtra("isEditLabel", false);
            getActivity().startActivityForResult(intent, 0x01);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x01) {
            refreshLabelListFromService();
        }
    }

    private void refreshLabelListFromService() {
        LablelListReq mailListReq = new LablelListReq(TioDBPreferences.getCurrUid() + "");
        mailListReq.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        mailListReq.setCancelTag(this);
        TioHttpClient.get(mailListReq, new TioCallback<LableListResp>() {
            @Override
            public void onTioSuccess(LableListResp mailListResp) {
                List<Label> mLabelList = new ArrayList<>();
                for (LableListResp.Lable lable : mailListResp.groupList){
                    Label label2 = new Label();
                    label2.setUserId(lable.getUid()+"");
                    label2.setGroupId(lable.getId()+"");
                    label2.setGroupname(lable.getGroupname());
                    label2.setFriendList(lable.getFriendidlist());
                    mLabelList.add(label2);
                }
                lineBreakLayout.setLables(mLabelList, false);
                //                proxy.onSuccess(mailListResp.group);
//                List<LableListResp.Lable> lables = mailListResp.groupList;

//                update(mLabelList);
//                LogUtils.d("zlb==>"+ JSON.toJSONString(mailListResp));
            }

            @Override
            public void onTioError(String msg) {

//                proxy.onFailure(msg);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
