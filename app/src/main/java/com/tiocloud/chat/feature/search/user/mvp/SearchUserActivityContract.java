package com.tiocloud.chat.feature.search.user.mvp;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.tiocloud.chat.feature.search.user.fragment.SearchUserFragment;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 */
public interface SearchUserActivityContract {
    interface View extends BaseView {
        void finishPage();

        SearchUserFragment addFragment(SearchUserFragment fragment);

        void hideFragment(SearchUserFragment fragment);

        void showFragment(SearchUserFragment fragment);
    }

    abstract class Model extends BaseModel {
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view) {
            super(model, view);
        }

        public abstract void initCancelBtn(TextView cancelBtn);

        public abstract void initFragment(int containerId);

        public abstract void initEtInputView(EditText etInput, android.view.View searchView);

        public abstract void showKeyBoard();
    }
}
