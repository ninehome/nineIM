package com.watayouxiang.wallet.feature.paperdetail.adapter;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/16
 *     desc   :
 *
 *     未被领取：红包金额8.88元，等待对方领取
 *
 *     已被领取：一个红包，共8.88元
 *              已领取 2/4个，共 4.00/8.00元
 *              4个红包共8.00元，15分钟被领完
 *
 *     已过期：该红包已过期。已领取0/4个，共0.00/8.00元
 *
 * </pre>
 */
public class SendInfoItem {

    private String info;

    public SendInfoItem(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
