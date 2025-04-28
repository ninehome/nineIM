package com.tiocloud.chat.yanxun.groupsend.mvp;

import android.widget.EditText;

import com.tiocloud.chat.yanxun.groupsend.fragment.SelectGroupSendFragment;
import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public interface ActivityGroupSendContract {
    interface View extends BaseView {
        void addFragment(SelectGroupSendFragment fragment);
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
