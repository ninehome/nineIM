package com.tiocloud.chat.feature.group.info.mvp;

import com.tiocloud.chat.feature.group.info.fragment.GroupInfoFragment;

/**
 * author : TaoWang
 * date : 2020-02-26
 * desc :
 */
public class ActivityGroupInfoPresenter extends ActivityGroupInfoContract.Presenter {
    public ActivityGroupInfoPresenter(ActivityGroupInfoContract.View view) {
        super(new ActivityGroupInfoModel(), view);
    }

    @Override
    public void showFragment() {
        getView().addFragment(GroupInfoFragment.create(getView().getGroupId()));

//        getModel().getGroupInfo("1", getView().getGroupId(), new BaseModel.DataProxy<GroupInfoResp>() {
//            @Override
//            public void onSuccess(GroupInfoResp groupInfo) {
//                GroupInfoResp.GroupUser user = groupInfo.groupuser;
//                int groupRole = user.grouprole;
//            }
//        });
    }
}
