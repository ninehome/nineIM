package com.tiocloud.chat.constant.customer;

public class OACustomerConfig extends BaseCustomerConfig {
    @Override
    public boolean isShowGroupNick(){
        return true;
    }
    @Override
    public boolean isShowGroupTipMsg(){
        return true;
    }
    @Override
    public boolean enterGroupChatCheck(){
        //壹聊进群进行验证，如果获取不到群信息，或者不在群里面，就不能进群聊，配置true
        //鸽哒的主版本，没网也能进群，配置false
        return false;
    }
    @Override
    public boolean userFragmentShowId(){
        //我的界面显示ID
        return true;
    }

    @Override
    public boolean showFriendCircle() {
        return true;
    }

    @Override
    public boolean modifyPwdByOldPwd() {
        return false;
    }

    @Override
    public boolean transAmountEnable() {
        return true;
    }

    @Override
    public boolean mainTabVibrate() {
        return false;
    }
}
