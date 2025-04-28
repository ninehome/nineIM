package com.tiocloud.chat.feature.curr.modify.mvp;

import android.view.View;

import com.tiocloud.chat.feature.curr.modify.model.ModifyType;
import com.tiocloud.chat.feature.curr.modify.model.PageUiModel;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.mvp.BaseModel;

/**
 * author : TaoWang
 * date : 2020/4/1
 * desc :
 */
public class ModifyPresenter extends ModifyContract.Presenter {
    private boolean isClickMenuBtn = false;

    public ModifyPresenter(ModifyContract.View view) {
        super(new ModifyModel(), view);
    }

    @Override
    public void init() {
        getView().resetUI();

        PageUiModel uiModel = PageUiModel.getInstance(getView().getModifyType());
        if (uiModel != null) {
            getView().setUIData(uiModel);
        }
    }

    @Override
    public void onClickMenuBtn(View view) {
        ModifyType type = getView().getModifyType();
        if (type == null) return;
        if (isClickMenuBtn) return;
        isClickMenuBtn = true;

        switch (type) {
            case GROUP_NAME:
                getModel().reqModifyGroupName(getView().getEtContent(), getView().getGroupId(), dataProxy);
                break;
            case GROUP_INTRO:
                getModel().reqModifyGroupIntro(getView().getEtContent(), getView().getGroupId(), dataProxy);
                break;
            case GROUP_NOTICE:
                getModel().reqModifyGroupNotice(getView().getEtContent(), getView().getGroupId(), dataProxy);
                break;
            case GROUP_NICK:
                getModel().reqModifyGroupNick(getView().getEtContent(), getView().getGroupId(), dataProxy);
                break;
            case CURR_NICK:
                getModel().updateNickReq(getView().getEtContent(), dataProxy);
                break;
            case CURR_SIGN:
                getModel().updateSignReq(getView().getEtContent(), dataProxy);
                break;
            case USER_REMARK_NAME:
                getModel().modifyRemarkNameReq(getView().getEtContent(), getView().getUid(), dataProxy);
                break;
        }
    }

    private final BaseModel.DataProxy<String> dataProxy = new BaseModel.DataProxy<String>() {
        @Override
        public void onSuccess(String s) {
            super.onSuccess(s);
            getView().getActivity().finish();
        }

        @Override
        public void onFailure(String msg) {
            super.onFailure(msg);
            isClickMenuBtn = false;
            TioToast.showShort(msg);
        }
    };
}
