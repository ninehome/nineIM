package com.watayouxiang.androidutils.page;

import androidx.fragment.app.FragmentActivity;

/**
 * author : TaoWang
 * date : 2020-02-06
 * desc : 取消网络请求
 */
public abstract class TioFragment extends BaseFragment {

    // public final ObservableField<String> fromInfo = new ObservableField<>("小生的红包");

    @Override
    public TioFragment getFragment() {
        return this;
    }

    public TioActivity getTioActivity() {
        FragmentActivity activity = super.getActivity();
        if (activity instanceof TioActivity) {
            return (TioActivity) activity;
        }
        return null;
    }

    public void finish() {
        FragmentActivity activity = super.getActivity();
        if (activity != null) {
            activity.finish();
        }
    }
}
