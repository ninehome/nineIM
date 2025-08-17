package com.tiocloud.chat.feature.search.curr.main.mvp;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.tiocloud.chat.feature.search.curr.main.adapter.SearchPagerAdapter;
import com.tiocloud.chat.feature.search.curr.main.adapter.SearchTabAdapter;

/**
 * author : TaoWang
 * date : 2020-02-13
 * desc :
 */
public class SearchFragmentPresenter extends SearchFragmentContract.Presenter {

    private SearchPagerAdapter pagerAdapter;

    public SearchFragmentPresenter(SearchFragmentContract.View view) {
        super(view);
    }

    @Override
    public void initPager(ViewPager pager, RecyclerView indicator) {
        pagerAdapter = new SearchPagerAdapter(getView().getSupportFragmentManager(), pager);
        pager.setAdapter(pagerAdapter);
        SearchTabAdapter tabAdapter = new SearchTabAdapter(indicator);
        tabAdapter.setViewPager(pager);
    }

    @Override
    public void search(String keyWord) {
        if (pagerAdapter != null) {
            pagerAdapter.search(keyWord);
        }
    }
}
