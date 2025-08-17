package com.tiocloud.chat.feature.search.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.search.user.fragment.SearchUserFragment;
import com.tiocloud.chat.feature.search.user.mvp.SearchUserActivityContract;
import com.tiocloud.chat.feature.search.user.mvp.SearchUserActivityPresenter;
import com.watayouxiang.androidutils.widget.WtTitleBar;
import com.watayouxiang.androidutils.page.TioActivity;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 */
public class SearchUserActivity extends TioActivity implements SearchUserActivityContract.View {

    private EditText et_input;



    private SearchUserActivityPresenter presenter;
    private WtTitleBar titleBar;

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchUserActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tio_search_user_activity);
        findViews();
        initViews();
        presenter = new SearchUserActivityPresenter(this);
        presenter.initFragment(R.id.frameLayout);
        presenter.initEtInputView(et_input, findViewById(R.id.ll_search2));
        presenter.showKeyBoard();

        findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
//        titleBar.setTitle("添加好友");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void findViews() {
        et_input = findViewById(R.id.et_input);
        titleBar = findViewById(R.id.titleBar);
    }

    @Override
    public void finishPage() {
        finish();
    }

    @Override
    public SearchUserFragment addFragment(SearchUserFragment fragment) {
        return super.addFragment(fragment);
    }

    @Override
    public void hideFragment(SearchUserFragment fragment) {
        super.hideFragment(fragment);
    }

    @Override
    public void showFragment(SearchUserFragment fragment) {
        super.showFragment(fragment);
    }
}
