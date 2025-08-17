package com.tiocloud.chat.feature.group.card.mvp;

import android.widget.EditText;

import com.tiocloud.chat.feature.group.card.fragment.SelectGroupFragment;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class ActivitySelectGroupPresenter extends ActivitySelectGroupContract.Presenter {

    private SelectGroupFragment fragment;

    public ActivitySelectGroupPresenter(ActivitySelectGroupContract.View view) {
        super(view);
    }

    @Override
    public void installFragment() {
        fragment = SelectGroupFragment.create();
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
}
