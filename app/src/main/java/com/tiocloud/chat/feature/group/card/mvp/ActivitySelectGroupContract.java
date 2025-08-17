package com.tiocloud.chat.feature.group.card.mvp;

import android.widget.EditText;

import com.tiocloud.chat.feature.group.card.fragment.SelectGroupFragment;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public interface ActivitySelectGroupContract {
    interface View extends BaseView {
        void addFragment(SelectGroupFragment fragment);
    }

    class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(View view) {
            super(new Model(), view);
        }

        public abstract void installFragment();

        public abstract void initEditText(EditText et_input);
    }
}
