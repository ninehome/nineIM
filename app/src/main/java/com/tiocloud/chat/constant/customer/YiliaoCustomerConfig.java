package com.tiocloud.chat.constant.customer;

public class YiliaoCustomerConfig extends BaseCustomerConfig {
    //壹聊不显示群昵称的需求
    @Override
    public boolean isShowGroupNick(){
        return false;
    }
    //壹聊不显示群提示的需求
    @Override
    public boolean isShowGroupTipMsg(){
        return false;
    }
    @Override
    public boolean enterGroupChatCheck(){
        //壹聊进群进行验证，如果获取不到群信息，或者不在群里面，就不能进群聊，配置true
        //鸽哒的主版本，没网也能进群，配置false
        return true;
    }
    @Override
    public boolean userFragmentShowId(){
        //我的界面显示ID
        return false;
    }

    @Override
    public boolean showFriendCircle() {
        return false;
    }

    @Override
    public boolean modifyPwdByOldPwd() {
        return true;
    }

    @Override
    public boolean transAmountEnable() {
        return false;
    }

    @Override
    public boolean mainTabVibrate() {
        return false;
    }
}
