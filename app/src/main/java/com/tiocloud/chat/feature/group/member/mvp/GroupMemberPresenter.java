package com.tiocloud.chat.feature.group.member.mvp;

import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.response.GroupUserListResp;

/**
 * author : TaoWang
 * date : 2020-02-27
 * desc :
 */
public class GroupMemberPresenter extends GroupMemberContract.Presenter {

    public GroupMemberPresenter(GroupMemberContract.View view) {
        super(new GroupMemberModel(), view);
    }

    @Override
    public void init() {
        getView().resetUI();
        load(1, null);
    }

    // ====================================================================================
    // data
    // ====================================================================================

    private int mPageNumber;
    private String mSearchKey = null;

    @Override
    public void refresh() {
        load(1, mSearchKey);
    }

    @Override
    public void search(String keyWord) {
        load(1, keyWord);
    }

    @Override
    public void loadMore() {
        load(++mPageNumber, mSearchKey);
    }

    private void load(int pageNumber, String searchKey) {
        mPageNumber = pageNumber;
        mSearchKey = searchKey;

        String pageNumberStr = String.valueOf(pageNumber);
        String groupId = getView().getGroupId();

        getModel().getGroupUserList(pageNumberStr, groupId, searchKey, new TioCallback<GroupUserListResp>() {
            @Override
            public void onTioSuccess(GroupUserListResp users) {
                // 设置标题
                getView().onMemberCount(users.totalRow);
                // 设置列表数据
                getView().onLoadListSuccess(users);
                // 设置群主长按操作
                if (users.firstPage) {
                    if (getModel().currUserIsGroupOwner(users.list)) {
                        getView().setListLongClickEnable(true);
                    } else {
                        getView().setListLongClickEnable(true);
                    }
                }
            }

            @Override
            public void onTioError(String msg) {
                getView().onLoadListError(msg, mPageNumber);
            }
        });
    }
}
