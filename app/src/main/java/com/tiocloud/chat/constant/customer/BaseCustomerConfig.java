package com.tiocloud.chat.constant.customer;

public abstract class BaseCustomerConfig {
    //壹聊不显示群昵称的需求
    public abstract boolean  isShowGroupNick();
    //壹聊不显示群提示的需求
    public abstract boolean isShowGroupTipMsg();
    //壹聊进群进行验证，如果获取不到群信息，或者不在群里面，就不能进群聊，配置true
    //鸽哒的主版本，没网也能进群，配置false
    public abstract boolean enterGroupChatCheck();
    //我的界面显示ID
    public abstract boolean userFragmentShowId();

    public abstract boolean showFriendCircle();

    public abstract boolean modifyPwdByOldPwd();

    public abstract boolean transAmountEnable();

    public abstract boolean mainTabVibrate();

    public boolean showWebViewIfOneSite(){
        return true;
    }

    public boolean isUliao(){
        return false;
    }

    public boolean noticeShowDialog(){
        return true;
    }

    /**
     * 屏蔽注册
     * @return
     */
    public boolean hideRegiest(){
        return false;
    }

    /**
     * 屏蔽右上角的pop
     * @return
     */
    public boolean hideRightTop(){
        return false;
    }


}
