package com.tiocloud.chat.yanxun.groupsend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.cache.CacheMode;
import com.tiocloud.chat.R;
import com.tiocloud.chat.yanxun.groupsend.adapter.CommonAdapter;
import com.tiocloud.chat.yanxun.groupsend.adapter.CommonViewHolder;
import com.tiocloud.chat.yanxun.groupsend.select.SelectFriendItem;
import com.tiocloud.chat.yanxun.lable.Label;

import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.LablelListReq;
import com.watayouxiang.httpclient.model.response.LableListResp;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5 0005.
 * 只是对临时的数据(status)进行更改，来标记是否选中，并不需要去更新数据库 status 100 未选中 101选中
 */

public class SelectLabelActivity extends TioActivity {
    private String mLoginUserId;

    private ListView mLabelLv;
    private LabelAdapter mLabelAdapter;
    private List<Label> mLabelList;

    private List<String> mSelectedLabel;// 已选中的标签

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_label);
        initActionBar();
        initData();

    }

    private void initActionBar() {
//        getSupportActionBar().hide();
        WtTitleBar titleBar = findViewById(R.id.titleBar);
        titleBar.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> lIds = new ArrayList<>();
                List<String> lNames = new ArrayList<>();
                List<SelectFriendItem> fids = new ArrayList<>();
                for (int i = 0; i < mLabelList.size(); i++) {
                    if (mLabelList.get(i).isSelected()) {
                        lIds.add(mLabelList.get(i).getGroupId());
                        lNames.add(mLabelList.get(i).getGroupname());
                        for (MailListResp.Friend friend : mLabelList.get(i).getFriendList()){
                            fids.add(new SelectFriendItem(friend.uid+"", friend.getNick(), 0));
                        }
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("SELECTED_LABEL_IDS", JSON.toJSONString(lIds));
                intent.putExtra("SELECTED_LABEL_NAMES", JSON.toJSONString(lNames));
                intent.putExtra("SELECTED_LABEL_FRIEND_IDS", JSON.toJSONString(fids));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initData() {
        LablelListReq mailListReq = new LablelListReq(TioDBPreferences.getCurrUid()+"");
        mailListReq.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        mailListReq.setCancelTag(this);
        TioHttpClient.get(mailListReq, new TioCallback<LableListResp>() {
            @Override
            public void onTioSuccess(LableListResp mailListResp) {
                List<LableListResp.Lable> lables = mailListResp.groupList;
                mLabelList = new ArrayList<>();
                for (LableListResp.Lable lable : lables){
                    Label label2 = new Label();
                    label2.setUserId(lable.getUid()+"");
                    label2.setGroupId(lable.getId()+"");
                    label2.setGroupname(lable.getGroupname());
                    label2.setFriendList(lable.getFriendidlist());
                    mLabelList.add(label2);
                }

                mLoginUserId = TioDBPreferences.getCurrUid()+"";
                mSelectedLabel = new ArrayList<>();
                String mSelectedLabelIds = getIntent().getStringExtra("SELECTED_LABEL");
                mSelectedLabel = JSON.parseArray(mSelectedLabelIds, String.class);
                if (mSelectedLabel != null && mSelectedLabel.size() > 0) {
                    for (int i = 0; i < mSelectedLabel.size(); i++) {
                        for (int i1 = 0; i1 < mLabelList.size(); i1++) {
                            if (mLabelList.get(i1).getGroupId().equals(mSelectedLabel.get(i))) {
                                mLabelList.get(i1).setSelected(true);
                            }
                        }
                    }
                }

                initView();
                initEvent();
            }

            @Override
            public void onTioError(String msg) {

//                proxy.onFailure(msg);
            }
        });


    }

    private void initView() {
        mLabelLv = (ListView) findViewById(R.id.label_lv);
        mLabelAdapter = new LabelAdapter(this, mLabelList);
        mLabelLv.setAdapter(mLabelAdapter);
    }

    private void initEvent() {
        mLabelLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mLabelList.get(position).isSelected()) {
                    mLabelList.get(position).setSelected(false);
                } else {
                    mLabelList.get(position).setSelected(true);
                }
                mLabelAdapter.notifyDataSetChanged();
            }
        });
    }

    class LabelAdapter extends CommonAdapter<Label> {

        public LabelAdapter(Context context, List<Label> data) {
            super(context, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CommonViewHolder viewHolder = CommonViewHolder.get(mContext, convertView, parent,
                    R.layout.row_select_label, position);
            TextView labelName = viewHolder.getView(R.id.label_name);
//            TextView labelUserNames = viewHolder.getView(R.id.label_user_name);
            CheckBox cb = viewHolder.getView(R.id.select_cb);
            final Label label = mLabelList.get(position);
            if (label != null) {
                List<MailListResp.Friend> friendList = label.getFriendList();
                labelName.setText(label.getGroupname() + "(" + (friendList == null ? "0":friendList.size()) + ")");
                String userNames = "";

                if (friendList != null && friendList.size() > 0) {
                    for (int i = 0; i < friendList.size(); i++) {
                        MailListResp.Friend friend = friendList.get(i);
                        if (friend != null) {
                            if (i == friendList.size() - 1) {
                                userNames += TextUtils.isEmpty(friend.getRemarkname()) ? friend.getNick() : friend.getRemarkname();
                            } else {
                                userNames += TextUtils.isEmpty(friend.getRemarkname()) ? friend.getNick() + "，" : friend.getRemarkname() + "，";
                            }
                        }
                    }
//                    if (TextUtils.isEmpty(userNames)) {
//                        labelUserNames.setVisibility(View.GONE);
//                    } else {
//                        labelUserNames.setVisibility(View.VISIBLE);
//                        labelUserNames.setText(userNames);
//                    }
                }
                if (label.isSelected()) {
                    cb.setChecked(true);
                } else {
                    cb.setChecked(false);
                }
            }
            if (position == 0 && position == mLabelList.size() - 1){
                viewHolder.getConvertView().findViewById(R.id.item_bg).setBackgroundResource(R.drawable.layer_list_all);
            }else if (position == 0){
                viewHolder.getConvertView().findViewById(R.id.item_bg).setBackgroundResource(R.drawable.layer_list_top);
            }else if (position == mLabelList.size() - 1){
                viewHolder.getConvertView().findViewById(R.id.item_bg).setBackgroundResource(R.drawable.layer_list_bottom);
            }else {
                viewHolder.getConvertView().findViewById(R.id.item_bg).setBackgroundResource(R.drawable.layer_list_side);
            }
            if (position == mLabelList.size() - 1){
                viewHolder.getConvertView().findViewById(R.id.view_line).setVisibility(View.GONE);
            }else {
                viewHolder.getConvertView().findViewById(R.id.view_line).setVisibility(View.VISIBLE);
            }
            return viewHolder.getConvertView();
        }
    }
}
