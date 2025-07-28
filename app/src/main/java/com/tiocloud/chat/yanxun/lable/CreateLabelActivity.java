package com.tiocloud.chat.yanxun.lable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.user.detail.UserDetailActivity;
import com.watayouxiang.wallet.yanxun.utisl.SkinUtils;
import com.tiocloud.chat.yanxun.base.ViewHolder;
import com.tiocloud.chat.yanxun.lable.create.CreateLableSelectContactActivity;
import com.tiocloud.chat.yanxun.view.PullToRefreshSlideListView;
import com.tiocloud.chat.yanxun.view.slidelistview.SlideBaseAdapter;

import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.androidutils.widget.imageview.TioImageView;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.LableCreateReq;
import com.watayouxiang.httpclient.model.request.LableModListReq;
import com.watayouxiang.httpclient.model.request.LableModNameReq;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.ArrayList;
import java.util.List;

public class CreateLabelActivity extends TioActivity implements View.OnClickListener {
    private EditText mLabelEdit;
    private TextView mLabelUserSizeTv;

    private PullToRefreshSlideListView mListView;
    private LabelAdapter mLabelAdapter;
    private List<MailListResp.Friend> mFriendList = new ArrayList<>();

    private String mLoginUserId;

    private boolean isEditLabel;// 创建  || 编辑 标签
//    private String labelId;// 编辑标签传入的值
    private Label mOldLabel;

    private WtTitleBar wtTitleBar;
    /**
     * Todo add 2018.6.20 intent from 发布说说-谁可以看
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_label);
//        mLoginUserId = coreManager.getSelf().getUserId();
        isEditLabel = getIntent().getBooleanExtra("isEditLabel", false);
        if (isEditLabel) {
//            labelId = getIntent().getStringExtra("labelId");
            mOldLabel = JSONObject.parseObject(getIntent().getStringExtra("oldLable"), Label.class);
            mFriendList = mOldLabel.getFriendList();
        }
        initActionBar();
        initView();
        initEvent();
    }

    private View line1;
    private View line2;
    private void initActionBar() {
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        wtTitleBar = findViewById(R.id.titleBar);

//        getSupportActionBar().hide();
//        ImageView mTitleLeft = (ImageView) findViewById(R.id.iv_title_left);
//        mTitleLeft.setVisibility(View.VISIBLE);
//        mTitleLeft.setOnClickListener(this);
//        mTvTitle = (TextView) findViewById(R.id.tv_title_center);
        if (isEditLabel) {
            wtTitleBar.setTitle(R.string.edit_tag);
        } else {
            wtTitleBar.setTitle(R.string.create_tag);
        }
//        mTitleRight = (TextView) findViewById(R.id.tv_title_right);
//        wtTitleBar.getTvRight().setTextColor(getResources().getColor(R.color.white));
//        wtTitleBar.getTvRight().setBackground(getResources().getDrawable(R.drawable.bg_btn_grey_circle));
        ViewCompat.setBackgroundTintList(wtTitleBar.getTvRight(), ColorStateList.valueOf(SkinUtils.getSkin(this).getAccentColor()));
        wtTitleBar.getTvRight().setText(getString(R.string.finish));
        wtTitleBar.getTvRight().setVisibility(View.VISIBLE);
        wtTitleBar.getTvRight().setOnClickListener(this);
        changeTitle(1, "");
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        mLabelEdit = (EditText) findViewById(R.id.label_name_et);
        mLabelUserSizeTv = (TextView) findViewById(R.id.label_user_size);

        if (isEditLabel) {
//            List<String> list = JSON.parseArray(mOldLabel.getUserIdList(), String.class);
//            if (list != null) {
//                for (String s : list) {
//                    Friend friend = FriendDao.getInstance().getFriend(mLoginUserId, s);
//                    if (friend != null) {
//                        mFriendList.add(friend);
//                    }
//                }
//            }
            mLabelEdit.setTextColor(getResources().getColor(R.color.app_black));
            mLabelEdit.setText(mOldLabel.getGroupname());
            mLabelUserSizeTv.setText(getString(R.string.tag_member) + "(" + mFriendList.size() + ")");
        }

        findViewById(R.id.add_label_user).setOnClickListener(this);
        ImageView iv_add_people = findViewById(R.id.iv_add_people);
        ImageViewCompat.setImageTintList(iv_add_people, ColorStateList.valueOf(SkinUtils.getSkin(this).getAccentColor()));
        TextView tv_add_people = findViewById(R.id.tv_add_people);
        tv_add_people.setTextColor(ColorStateList.valueOf(SkinUtils.getSkin(this).getAccentColor()));
        mListView = (PullToRefreshSlideListView) findViewById(R.id.pull_refresh_list);
        mLabelAdapter = new LabelAdapter(this);
        mListView.setAdapter(mLabelAdapter);
        mListView.getRefreshableView().setAdapter(mLabelAdapter);
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mListView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    position = (int) id;
                    UserDetailActivity.start(CreateLabelActivity.this, String.valueOf(mFriendList.get(position).uid));
                }catch (Exception e){
                    e.printStackTrace();
                }

//                Friend friend = mFriendList.get(position);
//                if (friend != null) {
//                    Intent intent = new Intent(CreateLabelActivity.this, BasicInfoActivity.class);
//                    intent.putExtra(AppConstant.EXTRA_USER_ID, friend.getUserId());
//                    startActivity(intent);
//                }
            }
        });
        if(mFriendList != null && mFriendList.size() > 0) {
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
        }else {
            line1.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
        }
    }

    private void initEvent() {
        mLabelEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeTitle(0, s.toString().trim());
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    mLabelEdit.setTextColor(getResources().getColor(R.color.app_black));
                    changeTitle(2, "");
                } else {
                    mLabelEdit.setTextColor(getResources().getColor(R.color.Grey_400));
                    changeTitle(1, "");
                }
            }
        });
    }

    private void changeTitle(int i, String str) {
//        wtTitleBar.getTvRight().setTextColor(getResources().getColor(R.color.white));
        if (i == 0) {
            if (TextUtils.isEmpty(str)) {
                if (isEditLabel) {
                    wtTitleBar.setTitle(R.string.edit_tag);
                } else {
                    wtTitleBar.setTitle(R.string.create_tag);
                }
            } else {
                wtTitleBar.setTitle(str);

            }
        } else if (i == 1) {
            wtTitleBar.getTvRight().setAlpha(1.0f);
            wtTitleBar.getTvRight().setOnClickListener(this);
        } else {
            wtTitleBar.getTvRight().setAlpha(1.0f);
            wtTitleBar.getTvRight().setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == wtTitleBar.getTvRight()){
            if (isEditLabel) {
//                    DialogHelper.showDefaulteMessageProgressDialog(this);
                if (!mOldLabel.getGroupname().equals(mLabelEdit.getText().toString())) {// 标签名已改变
                    updateLabelName();
                }
                updateLabelUserIdList(mOldLabel);
            } else {
                createLabel();
            }
            return;
        }
        switch (v.getId()) {
//            case R.id.iv_title_left:
//                finish();
//                break;
//            case .getId():
//
//                break;
            case R.id.add_label_user:
                Intent intent = new Intent(this, CreateLableSelectContactActivity.class);
                List<String> ids = new ArrayList<>();
                for (int i = 0; i < mFriendList.size(); i++) {
                    ids.add(mFriendList.get(i).uid+"");
                }
                intent.putExtra("exist_ids", JSON.toJSONString(ids));
                startActivityForResult(intent, 0x01);
                break;
        }
    }

    // 创建标签
    private void createLabel() {
        if (mLabelEdit.getText().toString().trim().length() < 1){
            ToastUtils.showShort(getString(R.string.label_name_not_empty));
            return;
        }
        String userIdListStr = "";
        for (int i = 0; i < mFriendList.size(); i++) {
            if (i == mFriendList.size() - 1) {
                userIdListStr += mFriendList.get(i).uid;
            } else {
                userIdListStr += mFriendList.get(i).uid + ",";
            }
//            strings.add(mFriendList.get(i).getUserId());
        }
        LableCreateReq mailListReq = new LableCreateReq(TioDBPreferences.getCurrUid()+"",mLabelEdit.getText().toString(), userIdListStr);
        mailListReq.setCancelTag(this);
        TioHttpClient.get(this, mailListReq, new TaoCallback<BaseResp<String>>() {
            @Override
            public void onSuccess(Response<BaseResp<String>> response) {

                if (response.body().isOk()){
                    ToastUtils.showShort(getString(R.string.creat_success));
                    finish();
                }else {
                    ToastUtils.showShort(response.body().getMsg());
                }
            }
        });
    }

    // 修改标签名称
    private void updateLabelName() {
        LableModNameReq mailListReq = new LableModNameReq(mOldLabel.getGroupId()+"",mLabelEdit.getText().toString());
        mailListReq.setCancelTag(this);
        TioHttpClient.get(this, mailListReq, new TaoCallback<BaseResp<String>>() {
            @Override
            public void onSuccess(Response<BaseResp<String>> response) {

                if (response.body().isOk()){
                    ToastUtils.showShort(getString(R.string.repair_success));
                }else {
                    ToastUtils.showShort(response.body().getMsg());
                }
            }
        });
//        TioHttpClient.get(mailListReq, new TioCallback<CommonResp>() {
//
//            @Override
//            public void onTioSuccess(CommonResp lableListResp) {
//                LogUtils.d("zlb-lablelist===>"+JSON.toJSONString(lableListResp));
////                setResult(Activity.RESULT_OK);
//            }
//
//            @Override
//            public void onTioError(String msg) {
//                LogUtils.d("zlb-lablelist===>"+msg);
//            }
//        });
//        Map<String, String> params = new HashMap<>();
//        params.put("access_token", coreManager.getSelfStatus().accessToken);
//        params.put("groupId", mOldLabel.getGroupId());
//        params.put("groupName", mLabelEdit.getText().toString());
//
//        HttpUtils.get().url(coreManager.getConfig().FRIENDGROUP_UPDATE)
//                .params(params)
//                .build()
//                .execute(new BaseCallback<Label>(Label.class) {
//                    @Override
//                    public void onResponse(ObjectResult<Label> result) {
//                        if (Result.checkSuccess(mContext, result)) {
//                            LabelDao.getInstance().updateLabelName(mLoginUserId, mOldLabel.getGroupId(), mLabelEdit.getText().toString());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e) {
//                    }
//                });
    }

    // 修改标签下的成员
    private void updateLabelUserIdList(final Label label) {
        String userIdListStr = "";
        for (int i = 0; i < mFriendList.size(); i++) {
            if (i == mFriendList.size() - 1) {
                userIdListStr += mFriendList.get(i).uid;
            } else {
                userIdListStr += mFriendList.get(i).uid + ",";
            }
//            strings.add(mFriendList.get(i).getUserId());
        }
        LableModListReq mailListReq = new LableModListReq(mOldLabel.getGroupId()+"",userIdListStr);
        mailListReq.setCancelTag(this);
        TioHttpClient.get(this, mailListReq, new TaoCallback<BaseResp<String>>() {
            @Override
            public void onSuccess(Response<BaseResp<String>> response) {

                if (response.body().isOk()){
                    ToastUtils.showShort(getString(R.string.update_success));
                    finish();
                }else {
                    ToastUtils.showShort(response.body().getMsg());
                }
            }
        });

//        Map<String, String> params = new HashMap<>();
//        params.put("access_token", coreManager.getSelfStatus().accessToken);
//        params.put("groupId", label.getGroupId());
//        String userIdListStr = "";
//        List<String> strings = new ArrayList<>();
//        for (int i = 0; i < mFriendList.size(); i++) {
//            if (i == mFriendList.size() - 1) {
//                userIdListStr += mFriendList.get(i).getUserId();
//            } else {
//                userIdListStr += mFriendList.get(i).getUserId() + ",";
//            }
//            strings.add(mFriendList.get(i).getUserId());
//        }
//        params.put("userIdListStr", userIdListStr);
//        final String userIdList = JSON.toJSONString(strings);
//
//        HttpUtils.get().url(coreManager.getConfig().FRIENDGROUP_UPDATEGROUPUSERLIST)
//                .params(params)
//                .build()
//                .execute(new BaseCallback<Label>(Label.class) {
//                    @Override
//                    public void onResponse(ObjectResult<Label> result) {
//                        DialogHelper.dismissProgressDialog();
//                        if (Result.checkSuccess(mContext, result)) {
//                            LabelDao.getInstance().updateLabelUserIdList(mLoginUserId, label.getGroupId(), userIdList);
//                            setResult(RESULT_OK, new Intent());// 通知界面刷新
//                            finish();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e) {
//                        DialogHelper.dismissProgressDialog();
//                    }
//                });
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x01 && resultCode == RESULT_OK) {
            String friendStr = data.getStringExtra("friends");

            List<MailListResp.Friend> friends = JSONArray.parseArray(friendStr, MailListResp.Friend.class);
            for (MailListResp.Friend friend : friends){
                if (!isexist(friend)){
                    mFriendList.add(friend);
                }
            }

            mLabelAdapter.notifyDataSetChanged();
            mLabelUserSizeTv.setText(getString(R.string.tag_member)  + "(" + mFriendList.size() + ")");
        }
    }

    private boolean isexist(MailListResp.Friend friend){
        if (mFriendList == null){
            return false;
        }
        for (MailListResp.Friend friend1 : mFriendList){
            if (friend1.uid == friend.uid){
                return true;
            }
        }
        return false;
    }

    class LabelAdapter extends SlideBaseAdapter {

        public LabelAdapter(Context context) {
            super(context);
        }

        @Override
        public int getFrontViewId(int position) {
            return R.layout.row_create_label;
        }

        @Override
        public int getLeftBackViewId(int position) {
            return 0;
        }

        @Override
        public int getRightBackViewId(int position) {
            return R.layout.row_item_delete;
        }

        @Override
        public int getCount() {
            if (mFriendList != null) {
                return mFriendList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mFriendList != null) {
                return mFriendList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = createConvertView(position);
            }
            TioImageView labelIv = ViewHolder.get(convertView, R.id.label_avatar);
            TextView labelUserNameTv = ViewHolder.get(convertView, R.id.label_user_name);
            TextView delete_tv = ViewHolder.get(convertView, R.id.delete_tv);
            View line = ViewHolder.get(convertView, R.id.line);
//            View view = ViewHolder.get(convertView, R.id.view);

            final MailListResp.Friend friend = mFriendList.get(position);
            if (friend != null) {
                labelIv.tio_roundAvatar(friend.avatar);
//                AvatarHelper.getInstance().displayAvatar(friend.getUserId(), labelIv);
//                labelUserNameTv.setText(!TextUtils.isEmpty(friend.getRemarkName()) ? friend.getRemarkName() : friend.getNickName());
                labelUserNameTv.setText(!TextUtils.isEmpty(friend.getRemarkname()) ? friend.getRemarkname() : friend.nick);
            }
            delete_tv.setOnClickListener(v -> {
                changeTitle(2, "");
                mFriendList.remove(position);
                notifyDataSetChanged();
                mLabelUserSizeTv.setText(getString(R.string.tag_member) + "(" + mFriendList.size() + ")");
            });

            if(position == mFriendList.size()-1){
                line.setVisibility(View.GONE);
            }else {
                line.setVisibility(View.VISIBLE);
            }

//            view.setVisibility(position == mFriendList.size() - 1 ? View.GONE : View.VISIBLE);

            return convertView;
        }
    }
}
