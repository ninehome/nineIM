package com.tiocloud.chat.feature.search.curr.main.mvp;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.search.curr.all.AllResultFragment;
import com.tiocloud.chat.feature.search.curr.friend.FriendResultFragment;
import com.tiocloud.chat.feature.search.curr.group.GroupResultFragment;
import com.tiocloud.chat.feature.search.curr.main.base.BaseResultFragment;
import com.tiocloud.chat.feature.search.curr.msg.MsgResultFragment;
import com.tiocloud.chat.util.KeyboardUtil;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public class SearchActivityPresenter extends SearchActivityContract.Presenter {

//    private SearchFragment fragment;
    private BaseResultFragment currentFragment;
    private EditText etInput;

    public SearchActivityPresenter(SearchActivityContract.View view) {
        super(view);
    }

    @Override
    public void initCancelBtn(TextView cancelBtn) {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment instanceof AllResultFragment){
                    getView().finishPage();
                }else {
                    replaceToFriendFragment(0, etInput.getText().toString());
                }
            }
        });
    }

    @Override
    public void showKeyBoard() {
        if (etInput == null) return;
        KeyboardUtil.showSoftInput(etInput);
    }

    @Override
    public void initFragment(int containerId) {
//        fragment = new SearchFragment();
//        fragment.setContainerId(containerId);
//        fragment = getView().addFragment(fragment);
        currentFragment = new AllResultFragment();
        currentFragment.setContainerId(containerId);
        currentFragment = getView().addFragment(currentFragment);
    }

    public void replaceToFriendFragment(int index, String keyword){
//        getView().removeFragment(currentFragment);
        if (index == 1){
            currentFragment = new FriendResultFragment();
        }else if (index == 2){
            currentFragment = new GroupResultFragment();
        }else if (index == 0){
            currentFragment = new AllResultFragment();
        }else {
            currentFragment = new MsgResultFragment();
        }
        currentFragment.setContainerId(R.id.frameLayout);
        getView().addFragment(currentFragment);
        getView().replaceFragment(currentFragment);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentFragment.updateKeyWord(keyword);
                currentFragment.load();
            }
        }, 300);

    }

    @Override
    public void initEtInputView(EditText etInput, ImageView ivClearText) {
        this.etInput = etInput;
        etInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                ivClearText.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
                notifyInputTextChanged();
            }
        });

        ivClearText.setVisibility(View.GONE);
        ivClearText.setOnClickListener(view -> etInput.setText(""));

        notifyInputTextChanged();
    }

    private void notifyInputTextChanged() {
        if (etInput == null) return;
        if (currentFragment == null) return;

        String keyWord = etInput.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
            getView().hideFragment(currentFragment);
        } else {
//            if (currentFragment instanceof AllResultFragment){
//                ((AllResultFragment)currentFragment).search(keyWord);
//            }
            currentFragment.updateKeyWord(keyWord);
            currentFragment.load();
            getView().showFragment(currentFragment);
        }
    }


//
//    public void hideAllfragment(){
//        getView().hideFragment(currentFragment);
//    }

    @Override
    public void detachView() {
        super.detachView();
        if (etInput != null) {
            KeyboardUtil.hideSoftInput(etInput);
        }
    }
}
