package com.tiocloud.chat.feature.group.silent;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.View;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.forbidden.ForbiddenMvpPresenter;
import com.watayouxiang.androidutils.util.SpanUtils;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.oper.EasyOperDialog;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.ForbiddenResp;
import com.watayouxiang.httpclient.model.response.ForbiddenUserListResp;

import java.util.List;

class MvpPresenter extends MvpContract.Presenter {
    private final ForbiddenMvpPresenter forbiddenPresenter = new ForbiddenMvpPresenter(null);
    private int mPageNumber = 1;
    private String mKeyWord = null;

    public MvpPresenter(MvpContract.View view) {
        super(new MvpModel(), view, false);
    }

    @Override
    public void detachView() {
        super.detachView();
        forbiddenPresenter.detachView();
    }

    @Override
    public void init() {
        getView().resetUI();
        load(null, 1);
    }

    @Override
    public void load(String keyWord, int pageNumber) {
        mKeyWord = keyWord;
        mPageNumber = pageNumber;

        forbiddenPresenter.getModel().reqForbiddenUserList(mPageNumber + "", getView().getGroupId(), keyWord, new TioCallback<ForbiddenUserListResp>() {
            @Override
            public void onTioSuccess(ForbiddenUserListResp resp) {
                List<ListModel> models = getModel().forbiddenUserList2Models(resp.getList());
                getView().onLoadSuccess(pageNumber, models, resp);
            }

            @Override
            public void onTioError(String msg) {
                getView().onLoadError(pageNumber, msg);
            }
        });
    }

    @Override
    public void search(String keyWord) {
        load(keyWord, 1);
    }

    @Override
    public void loadMore() {
        load(mKeyWord, ++mPageNumber);
    }

    @Override
    public void forbidden_cancelUser_confirmDialog(ListNormalItem normalItem, int position) {
        SpannableStringBuilder title = SpanUtils
                .getBuilder(getView().getActivity().getString(R.string.confirm_will)).setForegroundColor(Color.parseColor("#333333"))
                .append(normalItem.getTitle()).setForegroundColor(Color.parseColor("#4C94FF"))
                .append(getView().getActivity().getString(R.string.remove_forbiden_list)).setForegroundColor(Color.parseColor("#333333"))
                .create();

        new EasyOperDialog.Builder(title)
                .setPositiveBtnTxt(getView().getActivity().getString(R.string.confirm))
                .setNegativeBtnTxt(getView().getActivity().getString(R.string.cancel))
                .setOnBtnListener(new EasyOperDialog.OnBtnListener() {
                    @Override
                    public void onClickPositive(View view, EasyOperDialog dialog) {
                        forbidden_cancelUser(normalItem, position);
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickNegative(View view, EasyOperDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show_unCancel(getView().getActivity());
    }

    private void forbidden_cancelUser(ListNormalItem item, int position) {
        ForbiddenUserListResp.ListBean bean = item.getOriginalData();
        int uid = bean.getUid();
        String mode = bean.getForbiddenMode();
        String groupId = getView().getGroupId();

        forbiddenPresenter.forbidden_cancelUser(mode, uid + "", groupId, new TioCallback<ForbiddenResp>() {
            @Override
            public void onTioSuccess(ForbiddenResp forbiddenResp) {
                getView().onRemoveListItem(position);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }
}
