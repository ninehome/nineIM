package com.tiocloud.chat.feature.user.selectfriend.fragment.mvp;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.widget.ContactsCatalogView;
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
public interface FragmentSelectFriendContract {
    interface View extends BaseView {

        Activity getActivity();

        void finishPage();
    }

    abstract class Model extends BaseModel {
        public abstract void getMailList(String searchKey, DataProxy<List<MailListResp.Friend>> proxy);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void initRecyclerView(RecyclerView recyclerView, ContactsCatalogView contactsCatalogView);

        public abstract void search(String keyWord);
    }
}
