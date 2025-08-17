package com.tiocloud.chat.feature.search.curr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.BarUtils;
import com.jaeger.library.StatusBarUtil;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.search.curr.all.AllResultFragment;
import com.tiocloud.chat.feature.search.curr.main.base.BaseResultFragment;
import com.watayouxiang.androidutils.page.BaseFragment;
import com.watayouxiang.androidutils.page.TioActivity;
import com.tiocloud.chat.feature.search.curr.main.SearchFragment;
import com.tiocloud.chat.feature.search.curr.main.mvp.SearchActivityContract;
import com.tiocloud.chat.feature.search.curr.main.mvp.SearchActivityPresenter;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public class SearchActivity extends TioActivity implements SearchActivityContract.View {

    private SearchActivityPresenter presenter;

    private TextView tv_cancelBtn;
    private EditText et_input;
    private ImageView iv_clearText;

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTranslucent(this);
        setContentView(R.layout.tio_search_activity);
        BarUtils.setStatusBarCustom(findViewById(R.id.frameLayout2));
        findViews();
        presenter = new SearchActivityPresenter(this);
        presenter.initCancelBtn(tv_cancelBtn);
        presenter.initFragment(R.id.frameLayout);
        presenter.initEtInputView(et_input, iv_clearText);
        presenter.showKeyBoard();
    }


    private void findViews() {
        tv_cancelBtn = findViewById(R.id.tv_cancelBtn);
        et_input = findViewById(R.id.et_input);
        iv_clearText = findViewById(R.id.iv_clearText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void finishPage() {
        finish();
    }

    @Override
    public BaseResultFragment addFragment(BaseResultFragment fragment) {
        return super.addFragment(fragment);
    }

    @Override
    public void hideFragment(BaseResultFragment fragment) {
        super.hideFragment(fragment);
    }

    @Override
    public void removeFragment(BaseResultFragment fragment) {
        super.removeFragment(fragment);
    }

    @Override
    public void replaceFragment(BaseResultFragment fragment) {
        super.replaceFragment(fragment);
    }

    @Override
    public void showFragment(BaseResultFragment fragment) {
        super.showFragment(fragment);
    }

    public void repalceFragment(int index, String keyword){
        presenter.replaceToFriendFragment(index, keyword);
    }
//    public void hideAllFragment(){
//        presenter.hideAllfragment();
//    }
//
//    public void showAllFriendFragment(){
//        presenter.replaceAllFriendFragment();
//    }
}
