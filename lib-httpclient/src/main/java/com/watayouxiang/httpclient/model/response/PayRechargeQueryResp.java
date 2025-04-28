package com.watayouxiang.httpclient.model.response;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/27
 *     desc   :
 * </pre>
 */
public class PayRechargeQueryResp {
    /**
     * walletId : 6288883250000000103
     * amount : 1
     * completeDateTime : 2020-11-27 11:22:41
     * serialnumber : 1332162865855082496
     * merchantId : 890000595
     * bankname : 中国邮政储蓄
     * bankicon : https://tx.t-io.org/bank/default.png
     * bankcardnumber : 621***5341
     * bankcode : POST
     * status : SUCCESS
     */

    /**
     * walletId : 6288883250000000103
     * amount : 25555500
     * completeDateTime : 2020-11-27 13:40:10
     * serialnumber : 1332197454241734656
     * merchantId : 890000595
     * bankname : 中国邮政储蓄
     * ordererrormsg : 交易金额或次数超过限制
     * bankicon : https://tx.t-io.org/bank/default.png
     * bankcardnumber : 621***5341
     * bankcode : POST
     * status : FAIL
     */

    private String walletId;
    private String amount;
    private String completeDateTime;
    private String serialnumber;
    private String merchantId;
    private String bankname;
    private String ordererrormsg;
    private String bankicon;
    private String bankcardnumber;
    private String bankcode;
    private String status;

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

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getOrdererrormsg() {
        return ordererrormsg;
    }

    public void setOrdererrormsg(String ordererrormsg) {
        this.ordererrormsg = ordererrormsg;
    }

    public String getBankicon() {
        return bankicon;
    }

    public void setBankicon(String bankicon) {
        this.bankicon = bankicon;
    }

    public String getBankcardnumber() {
        return bankcardnumber;
    }

    public void setBankcardnumber(String bankcardnumber) {
        this.bankcardnumber = bankcardnumber;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
