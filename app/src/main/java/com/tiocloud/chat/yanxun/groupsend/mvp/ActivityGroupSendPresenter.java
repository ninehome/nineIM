package com.tiocloud.chat.yanxun.groupsend.mvp;

import android.widget.EditText;

import com.tiocloud.chat.util.KeyboardUtil;
import com.tiocloud.chat.yanxun.groupsend.fragment.SelectGroupSendFragment;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class ActivityGroupSendPresenter extends ActivityGroupSendContract.Presenter {

    public SelectGroupSendFragment fragment;

    public ActivityGroupSendPresenter(ActivityGroupSendContract.View view) {
        super(view);
    }

    @Override
    public void installFragment() {
        fragment = SelectGroupSendFragment.create();
        getView().addFragment(fragment);
    }

    @Override
    public void initEditText(EditText et_input) {
        et_input.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s != null && fragment != null) {
                    String keyWord = s.toString();
                    fragment.search(keyWord);
                }
            }
        });
    }

    @Override
    public void showKeyboard(EditText et_input) {
        KeyboardUtil.showSoftInput(et_input);
    }

    @Override
    public void createGroup() {
        if (fragment != null) {
            fragment.createGroup();
        }
    }
}
