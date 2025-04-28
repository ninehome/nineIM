package com.tiocloud.chat.feature.search.user.mvp;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.search.user.fragment.SearchUserFragment;
import com.tiocloud.chat.util.KeyboardUtil;
import com.watayouxiang.androidutils.listener.SimpleTextWatcher;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 */
public class SearchUserActivityPresenter extends SearchUserActivityContract.Presenter {

    private SearchUserFragment fragment;
    private EditText etInput;

    private View searchView;

    private Fragment getSearchFragment(){
        return fragment;
    }



    public SearchUserActivityPresenter(SearchUserActivityContract.View view) {
        super(new SearchUserActivityModel(), view);
    }

    @Override
    public void initCancelBtn(TextView cancelBtn) {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getView().finishPage();
            }
        });
    }

    @Override
    public void initFragment(int containerId) {
        fragment = new SearchUserFragment();
        fragment.setContainerId(containerId);
        fragment = getView().addFragment(fragment);

    }

    @Override
    public void initEtInputView(EditText etInput, View searchView) {
        this.etInput = etInput;
        this.searchView = searchView;
        TextView tvSearchKey = searchView.findViewById(R.id.tvSearchKey);
        etInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
//                notifyInputTextChanged();
                if (etInput == null) return;
                if (fragment == null) return;
                fragment.showEmpty();
                String keyWord = etInput.getText().toString();
                if (TextUtils.isEmpty(keyWord)) {
                    searchView.setVisibility(View.GONE);
                } else {
                    searchView.setVisibility(View.VISIBLE);
                    tvSearchKey.setText(keyWord);
                }
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyInputTextChanged();
                searchView.setVisibility(View.GONE);
            }
        });

        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    // 进行相应的搜索操作
                    notifyInputTextChanged();
                    searchView.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });

        notifyInputTextChanged();
    }

    @Override
    public void showKeyBoard() {
        if (etInput == null) return;
        KeyboardUtil.showSoftInput(etInput);
    }

    private void notifyInputTextChanged() {
        if (etInput == null) return;
        if (fragment == null) return;

        String keyWord = etInput.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
            getView().hideFragment(fragment);
        } else {
            fragment.search(keyWord);
            getView().showFragment(fragment);
        }
    }
}
