package com.tiocloud.chat.feature.group.create.mvp;

import android.widget.EditText;

import com.tiocloud.chat.feature.group.create.fragment.CreateGroupFragment;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public interface ActivityCreateGroupContract {
    interface View extends BaseView {
        void addFragment(CreateGroupFragment fragment);
    }

    class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(View view) {
            super(new Model(), view);
        }

        public abstract void installFragment();

        public abstract void initEditText(EditText et_input);

        public abstract void showKeyboard(EditText et_input);

        public abstract void createGroup();
    }
}
