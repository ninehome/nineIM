package com.tiocloud.chat.feature.search.curr.main.mvp;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tiocloud.chat.feature.search.curr.all.AllResultFragment;
import com.tiocloud.chat.feature.search.curr.main.base.BaseResultFragment;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;
import com.tiocloud.chat.feature.search.curr.main.SearchFragment;
import com.watayouxiang.androidutils.page.BaseFragment;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public interface SearchActivityContract {
    interface View extends BaseView {

        void finishPage();

        BaseResultFragment addFragment(BaseResultFragment fragment);

        void replaceFragment(BaseResultFragment fragment);

        void removeFragment(BaseResultFragment fragment);

        void hideFragment(BaseResultFragment fragment);

        void showFragment(BaseResultFragment fragment);
    }

    abstract class Model extends BaseModel {
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public Presenter(View view) {
            super(new SearchActivityModel(), view);
        }

        public abstract void initCancelBtn(TextView cancelBtn);

        public abstract void showKeyBoard();

        public abstract void initFragment(int containerId);

        public abstract void initEtInputView(EditText etInput, ImageView ivClearText);
    }
}
