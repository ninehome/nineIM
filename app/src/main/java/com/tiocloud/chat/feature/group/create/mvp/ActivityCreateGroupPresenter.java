package com.tiocloud.chat.feature.group.create.mvp;

import android.widget.EditText;

import com.tiocloud.chat.feature.group.create.fragment.CreateGroupFragment;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;
import com.tiocloud.chat.util.KeyboardUtil;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class ActivityCreateGroupPresenter extends ActivityCreateGroupContract.Presenter {

    private CreateGroupFragment fragment;

    public ActivityCreateGroupPresenter(ActivityCreateGroupContract.View view) {
        super(view);
    }

    @Override
    public void installFragment() {
        fragment = CreateGroupFragment.create();
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
