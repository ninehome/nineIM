package com.tiocloud.chat.feature.session.common.mvp;

public class SessionFragmentPresenter extends SessionFragmentContract.Presenter {
    public SessionFragmentPresenter(SessionFragmentContract.View view) {
        super(new SessionFragmentModel(), view, false);
    }
}
