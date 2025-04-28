package com.tiocloud.chat.feature.search.curr.main.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiocloud.chat.R;
import com.watayouxiang.androidutils.page.TioFragment;
import com.tiocloud.chat.util.StringUtil;
import com.tiocloud.chat.feature.search.curr.main.model.SearchModel;

/**
 * author : TaoWang
 * date : 2020-02-14
 * desc :
 */
public abstract class BaseResultFragment<Result> extends TioFragment {

    private final int loadingLayoutId = R.layout.tio_search_result_fragment_loading;
    private final int errorLayoutId = R.layout.tio_search_result_fragment_error;
    private final int emptyLayoutId = R.layout.tio_search_result_fragment_empty;

    private int currentLayoutId;// 当前布局
    private String keyWord;// 搜索词
    private String loadKeyWord;// 加载过的搜索词
    private boolean isAlive;// 页面是否存活

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentLayoutId = loadingLayoutId;
        return inflater.inflate(loadingLayoutId, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isAlive = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isAlive = false;
        if (mModel != null) {
            mModel.detachModel();
        }
    }

    // ====================================================================================
    // public
    // ====================================================================================

    /**
     * 更新搜索词
     *
     * @param keyWord 搜索词
     */
    public void updateKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * 加载
     * 1、当滚动到当前tab页时，被动调用
     * 2、输入搜索词后，主动调用
     */
    public void load() {
        // 搜索词不一样：需要加载
        // 加载失败、加载中：需要加载
        if (!StringUtil.equals(loadKeyWord, keyWord)
                || currentLayoutId == errorLayoutId
                || currentLayoutId == loadingLayoutId) {
            notifyLoading();
        }
    }

    // ====================================================================================
    // 四种界面切换
    // ====================================================================================

    /**
     * 加载中
     */
    private void notifyLoading() {
        if (!isAlive) return;
        replaceRootView(loadingLayoutId);
        currentLayoutId = loadingLayoutId;
        loadData(keyWord);
        loadKeyWord = keyWord;
    }

    protected abstract void loadData(String keyWord);

    /**
     * 加载成功
     */
    protected void notifySuccess(Result result) {
        if (!isAlive) return;
        replaceRootView(successLayoutId());
        currentLayoutId = successLayoutId();
        initSuccessLayout(result, keyWord);
    }

    protected abstract int successLayoutId();

    protected abstract void initSuccessLayout(Result result, String keyWord);

    /**
     * 数据为空
     */
    protected void notifyEmpty() {
        if (!isAlive) return;
        replaceRootView(emptyLayoutId);
        currentLayoutId = emptyLayoutId;
    }

    /**
     * 加载失败
     */
    protected void notifyError() {
        if (!isAlive) return;
        replaceRootView(errorLayoutId);
        currentLayoutId = errorLayoutId;
        findViewById(R.id.tv_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyLoading();
            }
        });
    }

    // ====================================================================================
    // data
    // ====================================================================================

    private SearchModel mModel;

    public SearchModel getModel() {
        if (mModel == null) {
            mModel = new SearchModel();
        }
        return mModel;
    }
}
