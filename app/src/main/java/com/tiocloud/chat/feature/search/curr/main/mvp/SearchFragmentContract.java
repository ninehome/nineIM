package com.tiocloud.chat.feature.search.curr.main.mvp;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.watayouxiang.androidutils.mvp.BaseModel;
import com.watayouxiang.androidutils.mvp.BasePresenter;
import com.watayouxiang.androidutils.mvp.BaseView;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public interface SearchFragmentContract {
    interface View extends BaseView {

        FragmentManager getSupportFragmentManager();
    }

    abstract class Model extends BaseModel {
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public Presenter(View view) {
            super(new SearchFragmentModel(), view);
        }

        public abstract void initPager(ViewPager pager, RecyclerView indicator);

        public abstract void search(String keyWord);
    }
}
