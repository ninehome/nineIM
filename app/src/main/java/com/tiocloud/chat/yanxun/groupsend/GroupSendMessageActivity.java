package com.tiocloud.chat.yanxun.groupsend;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.SessionActivity;
import com.tiocloud.chat.feature.session.common.action.model.FileAction;
import com.tiocloud.chat.feature.session.common.action.model.ImageAction;
import com.tiocloud.chat.feature.session.common.action.model.MapAction;
import com.tiocloud.chat.feature.session.common.action.model.ShootAction;
import com.tiocloud.chat.feature.session.common.action.model.base.BaseAction;
import com.tiocloud.chat.feature.session.common.model.SessionType;
import com.tiocloud.chat.widget.titlebar.SessionTitleBar;
import com.tiocloud.chat.yanxun.groupsend.fragment.GroupSendSessionFragment;
import com.tiocloud.chat.yanxun.groupsend.select.SelectFriendItem;

import com.watayouxiang.androidutils.widget.WtTitleBar;

import java.util.ArrayList;
import java.util.List;

public class GroupSendMessageActivity extends SessionActivity {
    public static List<SelectFriendItem> items;
    public static List<String> chatLinkIdList = new ArrayList<>();

    private TextView tvSendSize;
    private TextView tvNames;



    ArrayList<BaseAction> baseActionList;


    @SuppressLint("StringFormatMatches")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        baseActionList = getGroupSendAction();
        super.onCreate(savedInstanceState);
//        activityView = LayoutInflater.from(this).inflate(R.layout.message_fragment, null);
//        setContentView(R.layout.groupsend_message_fragment);
        if (items == null || items.size() <= 0){
            finish();
            return;
        }
        SessionTitleBar wtTitleBar = findViewById(R.id.titleBar);
        wtTitleBar.setTitle(getString(R.string.group_send));
        tvSendSize = findViewById(R.id.send_size_tv);
        tvSendSize.setText(String.format(getString(R.string.you_will_send), items.size()));
        tvNames = (TextView) findViewById(R.id.send_name_tv);
        tvSendSize.setVisibility(View.VISIBLE);
        tvNames.setVisibility(View.VISIBLE);
        final StringBuilder userNames = new StringBuilder();
        userNames.append(items.get(0).getName());
        for (int i = 1; i < items.size(); i++) {
            userNames.append(",");
            userNames.append(items.get(i).getName());
        }
        tvNames.setText(userNames);


//        initChatLinkIds();
        replaceFragment(GroupSendSessionFragment.create());
//        findViews();
//        initViews();
//        initTextEdit();
//        initAudioRecordBtn();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
//        presenter.init();
    }
    private ArrayList<BaseAction> getGroupSendAction(){

        ArrayList<BaseAction> menus = new ArrayList<>();
        menus.add(new ImageAction());
        menus.add(new ShootAction());
//        menus.add(new FaceTimeAction());
//        menus.add(new CallAction());
//        menus.add(new P2PRedPaperAction());
//        menus.add(new UserCardAction());
//        menus.add(new GroupCardAction());
        menus.add(new FileAction());
        menus.add(new MapAction());
        return menus;
    }

    @Nullable
    @Override
    protected Class<? extends Activity> getBackToClass() {
        return SelectGroupSendActivity.class;
    }



    @Nullable
    @Override
    public ArrayList<BaseAction> getActions() {
        return baseActionList;
    }

    @NonNull
    @Override
    public SessionType getSessionType() {
        return SessionType.GROUPSEND;
    }

    @Override
    public void sendComplete() {
        super.sendComplete();
        ToastUtils.showShort(getString(R.string.fasongwancheng));
        setResult(Activity.RESULT_OK);
        finish();
    }
}
