package com.watayouxiang.httpclient.model.response;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/24
 *     desc   :
 * </pre>
 */
public class PayGrabRedPacketResp {

    /**
     * amount : 1
     * completeDateTime : 2020-11-24 14:53:33
     * serialnumber : 1331078729685929984
     * merchantId : 890000595
     * receiveWalletId : 6288883250000000103
     * status : SUCCESS
     * reqid : 13310786445667368961905733174708
     */

    // 抢到的红包总额
    private String amount;
    private String completeDateTime;
    // 订单号
    private String serialnumber;
    // 商户号
    private String merchantId;
    // 抢红包的钱包id
    private String receiveWalletId;
    // 订单状态
    private String status;
    private String reqid;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCompleteDateTime() {
        return completeDateTime;
    }

    public void setCompleteDateTime(String completeDateTime) {
        this.completeDateTime = completeDateTime;
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

    public String getReceiveWalletId() {
        return receiveWalletId;
    }

    public void setReceiveWalletId(String receiveWalletId) {
        this.receiveWalletId = receiveWalletId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }
}
