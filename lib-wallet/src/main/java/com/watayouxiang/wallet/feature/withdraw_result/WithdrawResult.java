package com.watayouxiang.wallet.feature.withdraw_result;

import java.io.Serializable;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/20
 *     desc   :
 * </pre>
 */
public class WithdrawResult implements Serializable {
    private String withdrawAmount;// = "20.00";
    private String bankInfo;// = "民生银行(0238)";
    private String bankIcon;// = null;
    private String withdrawFee;// = "0.02";

    public WithdrawResult(String withdrawAmount, String bankInfo, String bankIcon, String withdrawFee) {
        this.withdrawAmount = withdrawAmount;
        this.bankInfo = bankInfo;
        this.bankIcon = bankIcon;
        this.withdrawFee = withdrawFee;
    }

    public String getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public String getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(String withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public String getBankIcon() {
        return bankIcon;
    }

    public void setBankIcon(String bankIcon) {
        this.bankIcon = bankIcon;
    }
}
