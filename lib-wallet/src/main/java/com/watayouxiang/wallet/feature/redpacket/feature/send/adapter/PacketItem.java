package com.watayouxiang.wallet.feature.redpacket.feature.send.adapter;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/04
 *     desc   :
 * </pre>
 */
public class PacketItem {

    private String titleTxt = "普通红包";
    private String subtitleTxt = "9-12";
    private String moneyInfoTxt = "2.12元";
    private CharSequence amountInfoTxt = "1/1个";
    private boolean isTimeoutRed = false;

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

    public CharSequence getAmountInfoTxt() {
        return amountInfoTxt;
    }

    public void setAmountInfoTxt(CharSequence amountInfoTxt) {
        this.amountInfoTxt = amountInfoTxt;
    }

    public boolean isTimeoutRed() {
        return isTimeoutRed;
    }

    public void setTimeoutRed(boolean timeoutRed) {
        isTimeoutRed = timeoutRed;
    }
}
