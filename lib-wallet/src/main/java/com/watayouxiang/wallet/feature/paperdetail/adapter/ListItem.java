package com.watayouxiang.wallet.feature.paperdetail.adapter;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/16
 *     desc   :
 * </pre>
 */
public class ListItem {

    private String avatar;
    private String name = "wata";
    private String moneyInfo = "16.80";
    private String timeInfo = "10-12 12:00:40";
    private boolean bestLucky = true;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoneyInfo() {
        return moneyInfo;
    }

    public void setMoneyInfo(String moneyInfo) {
        this.moneyInfo = moneyInfo;
    }

    public String getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(String timeInfo) {
        this.timeInfo = timeInfo;
    }

    public boolean isBestLucky() {
        return bestLucky;
    }

    public void setBestLucky(boolean bestLucky) {
        this.bestLucky = bestLucky;
    }
}
