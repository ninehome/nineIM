package com.watayouxiang.wallet.feature.withdraw_record.adapter;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/27
 *     desc   :
 * </pre>
 */
public class NormalItem {

    private String titleTxt;
    private String subtitleTxt;
    private String money;
    /**
     * {@link com.watayouxiang.httpclient.model.vo.WithholdStatus}
     */
    private String status;

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
