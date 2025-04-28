package com.tiocloud.chat.yanxun.lable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.home.friend.adapter.ContactAdapter;
import com.tiocloud.chat.feature.home.friend.adapter.model.IData;
import com.tiocloud.chat.feature.home.friend.task.maillist.MailListTask;
import com.tiocloud.chat.feature.home.friend.task.maillist.MailListTaskData;
import com.tiocloud.chat.feature.home.friend.task.maillist.MailListTaskProxy;
import com.watayouxiang.androidutils.page.TioActivity;
import com.watayouxiang.androidutils.widget.TioToast;

public class SelectLabelFriendActivity extends TioActivity {
    private ContactAdapter adapter;
    private MailListTask mailListTask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lable_friend);

        adapter = new ContactAdapter(this);
        adapter.installCatalogView(findViewById(R.id.contacts_catalog_view), findViewById(R.id.contact_list_view));

        ((ListView)findViewById(R.id.contact_list_view)).setAdapter(adapter);

        initData();
    }

    public static void start(Context context){
        Intent intent = new Intent(context, SelectLabelFriendActivity.class);
        context.startActivity(intent);
    }

    private void initData() {
        cancelMailList();
        mailListTask = new MailListTask(new MailListTaskProxy() {
            @Override
            public void onTaskDone(MailListTaskData taskData) {
                Log.w("zlb","1111111111==>"+(this==null));
                if (taskData.ok) {
                    if (adapter != null) {
                        adapter.setData(taskData.data);
                    }
                } else {
                    TioToast.showShort(taskData.msg);
                }
                Log.w("zlb","222222222222==>"+(this==null));
                if (this != null) {
                    Log.w("zlb","3333333333==>"+(this==null));
                    this.onTaskDone(taskData);
                }
                Log.w("zlb","4444444444444==>"+(this==null));
            }
        });
        IData iData = new IData();
        mailListTask.execute(iData);
    }

    private void cancelMailList() {
        if (mailListTask != null) {
            mailListTask.setCancel(true);
            mailListTask.cancel(false);
        }
    }
}
