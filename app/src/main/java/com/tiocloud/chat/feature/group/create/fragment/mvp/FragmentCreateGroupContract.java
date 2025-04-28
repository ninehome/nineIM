package com.tiocloud.chat.feature.group.create.fragment.mvp;

import android.app.Activity;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.widget.ContactsCatalogView;
import com.watayouxiang.httpclient.model.response.CreateGroupResp;
import com.watayouxiang.httpclient.model.response.MailListResp;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public interface FragmentCreateGroupContract {
    interface View extends BaseView {

        Activity getActivity();

        void finishPage();
    }

    abstract class Model extends BaseModel {
        public abstract void getMailList(String searchKey, DataProxy<List<MailListResp.Friend>> proxy);

        public abstract void postCreateGroup(String groupName, String uidList, DataProxy<CreateGroupResp> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void initRecyclerView(RecyclerView recyclerView, ContactsCatalogView contactsCatalogView);

        public abstract void initMenuBtn(TextView tvMenuBtn);

        public abstract void search(String keyWord);

        public abstract void createGroup();
    }
}
