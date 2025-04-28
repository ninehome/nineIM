package com.tiocloud.chat.feature.home.chat.mvp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiocloud.chat.feature.main.MainActivity;
import com.watayouxiang.db.event.ChatListTableEvent;
import com.watayouxiang.httpclient.model.response.ChatListResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/06/24
 *     desc   :
 * </pre>
 */
public interface ChatContract {
    interface View extends BaseView {
        @Nullable
        MainActivity getMainActivity();

        void initUI();

        void onChatListRespSuccess(@Nullable ChatListResp lists);

        void onEndRefresh();

        void onChatListTableEvent(@NonNull ChatListTableEvent event);

        void onOffline(String uid, boolean onoff, String lastOnlineTime);
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }

        public abstract void getChatList(DataProxy<ChatListResp> proxy);

        public abstract void queryAllFromDB(@NonNull DataProxy<ChatListResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }

        public abstract void init();

        public abstract void getChatList(boolean forceHttpReq);

        public abstract void queryAllFromDB();
    }
}
