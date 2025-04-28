package com.tiocloud.chat.feature.group.removemember.mvp;

/**
 * author : TaoWang
 * date : 2020/2/26
 * desc :
 */
public class ActivityRemoveMemberPresenter extends ActivityRemoveMemberContract.Presenter {

    public ActivityRemoveMemberPresenter(ActivityRemoveMemberContract.View view) {
        super(view);
    }

    @Override
    public void init() {
        getView().initEditText();
        getView().installFragment();
    }
}
