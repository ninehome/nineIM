package com.watayouxiang.wallet.feature.recharge_result;

import java.io.Serializable;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/20
 *     desc   :
 * </pre>
 */
public class RechargeResult implements Serializable {
    private int type;
    private String money;
    private String failedReason;

    private RechargeResult(int type, String money, String failedReason) {
        this.type = type;
        this.money = money;
        this.failedReason = failedReason;
    }

    public static RechargeResult getSuccess(String money) {
        return new RechargeResult(RechargeResultType.SUCCESS, money, null);
    }

    public static RechargeResult getFailed(String money, String failedReason) {
        return new RechargeResult(RechargeResultType.FAILED, money, failedReason);
    }

    public static RechargeResult getProcess(String money) {
        return new RechargeResult(RechargeResultType.PROCESS, money, null);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }
}
