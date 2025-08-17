package com.tiocloud.chat.yanxun.lable;

import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.List;

public class Label {

    private int _id;

    private String userId;// 标签拥有者

    private String groupId;// 标签Id

    private String groupname;// 标签名字

//    private String userIdList;// 该标签下的用户id     [100,120]

    private List<MailListResp.Friend> friendList;

    public List<MailListResp.Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<MailListResp.Friend> friendList) {
        this.friendList = friendList;
    }

    private boolean isSelected;// 该标签是否选中
    private boolean isSelectedInBelong;// 该标签是否选中

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

//    public String getUserIdList() {
//        return userIdList;
//    }
//
//    public void setUserIdList(String userIdList) {
//        this.userIdList = userIdList;
//    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelectedInBelong() {
        return isSelectedInBelong;
    }

    public void setSelectedInBelong(boolean selectedInBelong) {
        isSelectedInBelong = selectedInBelong;
    }
}
