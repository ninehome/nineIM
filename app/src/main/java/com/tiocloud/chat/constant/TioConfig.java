package com.tiocloud.chat.constant;

import com.blankj.utilcode.util.StringUtils;
import com.tiocloud.chat.R;
import com.tiocloud.chat.constant.customer.BaseCustomerConfig;
import com.tiocloud.chat.constant.customer.EtcCustomerConfig;
import com.tiocloud.chat.constant.customer.GeDaCustomerConfig;
import com.tiocloud.chat.constant.customer.MiliaoCustomerConfig;
import com.tiocloud.chat.constant.customer.OACustomerConfig;
import com.watayouxiang.androidutils.yanxun.ConstantUtils;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.model.response.CollectEmotionListResp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TioConfig {

    public static final String BASE_URL_ONLINE = "http://106.53.115.205:82";//
//public static final String BASE_URL_ONLINE = "http://api.shelleytv.com:82";//测试

    public static final String HAND_SHAKE_KEY_ONLINE = "T"+"e"+"s"+"O"+"t"+"0"+"T";

    static BaseCustomerConfig customerConfig = new GeDaCustomerConfig();

    public static final boolean IS_DEVELOPE = true;

    public static final String share_url = BASE_URL_ONLINE+"/tioim/register?inviteUid=%s&inviteCode=%s";
    /**
     * 隐藏地址
     */
    public static final boolean HIDE_ADDRESS = true;
    /**
     * 初始化开关的KEY
     */
    public static class OpenCloseConfig{
        public static Map<String, String> confMap;
        //地图开关
        public static final String KEY_MAP_OPEN = "app.baidumap.is.open.flag";
        //一键登录开关
        public static final String KEY_ONEKEY_LOGIN = "app.one.key.login.is.open.flag";
        //钱包开关
        public static final String KEY_WALLET = "app.wallet.open.flag";
        //语音开关
        public static final String KEY_AUDIO = "app.video.is.open.flag";
        //视频通话开关
        public static final String KEY_VIDEO = "app.video.is.open.flag";
        //红包开关
        public static final String KEY_RED_PACKET = "yx.open.redPacket";

        public static String getValue(String key){
            if (confMap == null){
                return null;
            }
            return confMap.get(key);
        }

        public static String getCompanyTel(){
            return getValue("company_tel");
        }

        public static String getCompanyQQ(){
            return getValue("compay_qq");
        }

        public static String getCompanyAddr(){
            return getValue("company_address");
        }

        public static String getCompanyIntro(){
            return getValue("company_intro");
        }

        public static String getCompanyName(){
            return getValue("company");
        }

        //是否开启位置功能
        public static boolean isMapEnable(){
            return "1".equals(getValue(KEY_MAP_OPEN));
        }
        //是否开启手机号一键登录
        public static boolean isOnkeyLoginEnable(){
            return "1".equals(getValue(KEY_ONEKEY_LOGIN));
        }
        //是否开启钱包
        public static boolean isWalletEnable(){
            return "1".equals(getValue(KEY_WALLET));
        }
        //是否开启红包
        public static boolean isRedpacketEnable(){
            return isWalletEnable() && "1".equals(getValue(KEY_RED_PACKET));
        }
        //是否开启语音通话功能
        public static boolean isAudioEnable(){
            return "1".equals(getValue(KEY_AUDIO));
        }
        //是否开启视频通话
        public static boolean isVideoEnable(){
            return "1".equals(getValue(KEY_VIDEO));
        }

        //壹聊不显示群昵称的需求：是否显示群昵称
        public static boolean isShowGroupNick(){
            return customerConfig.isShowGroupNick();
        }
        //壹聊不显示群提示的需求：是否显示群系统提示
        public static boolean isShowGroupTipMsg(){
            return customerConfig.isShowGroupTipMsg();
        }
        //是否进群验证网络条件。废弃。
        public static boolean enterGroupChatCheck(){
            //壹聊进群进行验证，如果获取不到群信息，或者不在群里面，就不能进群聊，配置true
            //鸽哒的主版本，没网也能进群，配置false
            return customerConfig.enterGroupChatCheck();
        }
        //我的界面是否显示ID，true显示ID，false显示登录名
        public static boolean userFragmentShowId(){
            //我的界面显示ID
            return customerConfig.userFragmentShowId();
        }
        //探索页面是否要显示朋友圈
        public static boolean showFriendCircle(){
            return customerConfig.showFriendCircle();
        }
        //是否开戾通过老密码修改密码
        public static boolean modifyPwdByOldPwd(){
            return customerConfig.modifyPwdByOldPwd();
        }

        //是否开启自定义表情功能
        public static boolean collectFaceEmotionEnable(){
            return true;
        }
        //是否限制聊天图片的高度
        public static boolean limitChatPicHeight(){
            return true;
        }

        //自定义网站只有一个，则直接显示自定义网站webView
        public static boolean showWebViewIfOneSite(){
            return customerConfig.showWebViewIfOneSite();
        }
        //是否显示微信登录
        public static boolean isShowWxLogin(){
            return false;
        }

        //主界面是个按钮切换是否震动效果
        public static boolean mainTabVibrate(){
            return customerConfig.mainTabVibrate();
        }

        //群主或管理员撤回消息，普通成员不提示
        //true-不限制
        //false-普通成员不提示
        public static boolean showBackMsg(){
            return false;
        }
        //转账开关，是否开启转账功能
        public static boolean transAmountEnable(){
            return isWalletEnable();
        }
        //无用配置，是否OA版本
        public static boolean isOA(){
            return customerConfig instanceof OACustomerConfig;
        }
        //无用配置
        public static boolean isUliao(){
            return customerConfig.isUliao();
        }

        //群公告是不是主动弹窗，打开该开关，当来通知时，如果在群聊界面，会直接弹窗，如果不在群聊界面，在下次进入群聊界面时，会弹窗
        public static boolean noticeShowDialog(){
            return customerConfig.noticeShowDialog();
        }
        /**
         * 屏蔽注册
         * @return
         */
        public static boolean hideRegiest(){
            return customerConfig.hideRegiest();
        }

        /**
         * 屏蔽右上角的pop
         * @return
         */
        public static boolean hideRightTop(){
            return false;
        }
        //是否开启在线状态显示
        public static boolean showOnlineStatus(){
            return "1".equals(getValue("onlineStatus"));
        }
        //是否显示最近在线时间
        public static boolean showLastOnlineTime(){
            return true;
        }
        //是否显示输入状态
        public static boolean showInputStatus(){
            return "1".equals(getValue("displayInputStatus"));
        }
        //是否显示已读状态
        public static boolean showReadStatus(){
            return "1".equals(getValue("msg.read.focus.flag"));
        }
        //在线状态查询间隔(秒)
        public static int getOnlineRefreshTime(){
            try {
                return Integer.parseInt(getValue("onlineRefreshTime"));
            }catch (Exception e){
                e.printStackTrace();
            }
            return 60;
        }
        //普通成员是否显示邀请、踢人等提示
        public static boolean showInviteKickTips(){
            return "1".equals(getValue("InvitekickMemberTip"));
        }
        //是否需要推送碳
        public static boolean needPush(){
            return !TioDBPreferences.getCurrNoDisturb().equals("1");
        }

        //是否显示分享页面
        public static boolean needSharePage(){
            return true;
        }

        //是否显示我的二维码
        public static boolean showMyQrcode(){
            return true;
        }

        //主界面右上的pop只显示添加好友
        public static boolean showOnlyCreateGroupPop(){
            return false;
        }

        //新的密码规则
        public static boolean newPwdRule(){
            return true;
        }

        public static String getSitename(){
            String sitename = getValue("sitename");
            return StringUtils.isEmpty(sitename) ? ConstantUtils.context.getString(R.string.my_name):sitename;
        }

        public static int getSplashTime(){
            return 2000;
        }

    }


    /**
     * 一键登录
     */
    public static final String um_appkey = "60583f8db8c8d45c13a99bdb";
//    public static final String um_secry = "TO1pVjQLhBTb2GvFDvQWUcubfwFcgzpaOksQnO3+tZIVJZpY7lk0swUf1lx9JBBNSp8TdOV13KtlpPkZLuUJedFnc7c0gdFdW7Edy179whfeLEbW0HFfa6YywUGn39HCK88K/xpaZVY6Qdw2vLjqeSDZ+wQHMDjEThO+x/MJ1Hlw5ziBXLCNG6Sw/oinIEx6aKmRSlkIIoIUG7wSegV3epNqlX48/8lgRglkUJ3jD7qfyQe1EeKP+gFGseCADFKKOLLDsCwUqBjw+Swhd6t50uc2QIRyBIl6gGW4ez08SkdqGFZUBuz83htFos/QwVhO";
//    public static final String um_secry = "L7cpGs3OpuGhoxLcZmw2xhR+jPp8noLf58t42ycCwyjyd2g9J/vROk3wtPmv8H/fkV9BCHHe+rsbsN2ALkUoP34uBVEn5YakCgmRp0gbTS7XzK3Es1ODQa23zQNanv2PrBg2P8z5zXivHklLyT5f1zuRiI2a2RjtFr4skRkXFIVwkteR+Irl4ah1xZocARY+3vefE4SdNvD9i92yiYPENQrJDyPEzW1t0GycB+MpvS0x1aargLQFu42YWwMHLiMIyISF1nL/lyU6c4szTnoG+DZQKtZW4QOKn3kTF5ZHGN8Cmt3Y1FPcOg==";
    public static final String um_secry = "PN8AeC2NuVGR2mzLZZhDCNq6ADWRLVzWwgFxPlU7pjuA6z3Xg+7axl9WH4y7Tocp2ZR6S4z0jcNNTxNnDV09O3zQMrg8OtW+4LqLkw8kW0syvVM0Fl4UyfBv4woY3jTeTUo+O1Flq7cg3S+iiSjbsnOhZaPBr0bZRbhu7BYclpNtTRn3egplGHGjj6OzkXm1AFvwf+ejfKmYjLBG8LsvG1vako9ibeHwg/FVToL+qITOVW6ELlV4jTOKTozhosdUU/jTQLrIW+CViC8tTRjWs20xcr44peNxUxb8F0OCJ84WTSjJdldSpQ==";

    public static List<CollectEmotionListResp.CollectEmotion> emotionList;

    //好友备注缓存
    public static Map<String, String> friendRemarkCacheMap = new HashMap<>();

    /**
     * 免打扰 1-p2p 2-群聊
     * @return
     */
    public static boolean isNoDisturbBizId(int mode, String bizid){
        //如果全局免打扰开启，则返回true
        if(TioDBPreferences.getCurrNoDisturb().equals("1")){
            return true;
        }
        if (bizid == null){
            return false;
        }
        return TioDBPreferences.getBoolean("trubbiz_"+mode+"_"+bizid, false);
    }
    public static void saveNoDisturbBizId(int mode, String bizid, boolean b){
        TioDBPreferences.saveBoolean("trubbiz_"+mode+"_"+bizid, b);
    }

    public static boolean isNoDisturbChatLinkId(int mode, String bizid){
        //如果全局免打扰开启，则返回true
        if(TioDBPreferences.getCurrNoDisturb().equals("1")){
            return true;
        }
        if (bizid == null){
            return false;
        }
        return TioDBPreferences.getBoolean("trublink_"+mode+"_"+bizid, false);
    }
    public static void saveNoDisturbChatLinkId(int mode, String bizid, boolean b){
        TioDBPreferences.saveBoolean("trublink_"+mode+"_"+bizid, b);
    }

    public static boolean isNoDisturb(int mode, boolean isBiz, String id){
        return isBiz?isNoDisturbBizId(mode, id):isNoDisturbChatLinkId(mode, id);
    }
}
