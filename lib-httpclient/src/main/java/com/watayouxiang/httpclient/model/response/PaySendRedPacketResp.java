package com.watayouxiang.httpclient.model.response;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/19
 *     desc   :
 * </pre>
 */
public class PaySendRedPacketResp {

    /**
     * walletId : 6288883250000000103
     * amount : 1
     * serialNumber : 1329361709844008960
     * merchantId : 890000595
     * orderStatus : INIT
     * token : 20201119740734514135706537676800
     * createDateTime : 2020-11-19 17:51:44
     */

    private String walletId;
    private String amount;
    private String serialnumber;
    private String merchantId;
    private String orderStatus;
    private String token;
    private String createDateTime;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }
}
