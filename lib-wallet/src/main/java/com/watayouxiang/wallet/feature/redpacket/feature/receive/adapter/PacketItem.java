package com.watayouxiang.wallet.feature.redpacket.feature.receive.adapter;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/04
 *     desc   :
 * </pre>
 */
public class PacketItem {

    private String titleTxt = "大叔";
    private String subtitleTxt = "9-12";
    private String moneyInfoTxt = "2.12元";
    private String avatarUrl = null;
    private boolean isPin = true;

    public String getTitleTxt() {
        return titleTxt;
    }

    public void setTitleTxt(String titleTxt) {
        this.titleTxt = titleTxt;
    }

    public String getSubtitleTxt() {
        return subtitleTxt;
    }

    public void setSubtitleTxt(String subtitleTxt) {
        this.subtitleTxt = subtitleTxt;
    }

    public String getMoneyInfoTxt() {
        return moneyInfoTxt;
    }

    public void setMoneyInfoTxt(String moneyInfoTxt) {
        this.moneyInfoTxt = moneyInfoTxt;
    }

    public boolean isPin() {
        return isPin;
    }

    public void setPin(boolean pin) {
        isPin = pin;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
