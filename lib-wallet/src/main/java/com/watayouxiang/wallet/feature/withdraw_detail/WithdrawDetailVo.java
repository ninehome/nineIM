package com.watayouxiang.wallet.feature.withdraw_detail;

import com.blankj.utilcode.util.StringUtils;
import com.watayouxiang.httpclient.model.response.PayWithholdListResp;
import com.watayouxiang.httpclient.model.vo.WithholdStatus;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.wallet.tools.MoneyUtils;

import java.io.Serializable;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/01
 *     desc   :
 * </pre>
 */
public class WithdrawDetailVo implements Serializable {

    /**
     * {@link WithholdStatus}
     */
    public String status;
    /**
     * 失败原因
     */
    public String failedReason;
    /**
     * 10.00
     */
    public String money_total;
    /**
     * 9.90
     */
    public String money_receive;
    /**
     * 0.01
     */
    public String money_fee;
    /**
     * 单号
     */
    public String serialNumber;
    /**
     * 银行名称
     */
    public String bankName;
    /**
     * 银行后四位
     */
    public String bankCardLast4No;
    /**
     * 银行卡图标
     */
    public String bankIcon;
    /**
     * 提交时间
     */
    public String submitTime;
    /**
     * 到账时间
     */
    public String finishTime;

    private WithdrawDetailVo() {
    }

    public static WithdrawDetailVo getInstance(PayWithholdListResp.ListBean original) {
        WithdrawDetailVo vo = new WithdrawDetailVo();
        vo.status = StringUtils.null2Length0(original.getStatus());
        vo.failedReason = "";
        vo.money_total = MoneyUtils.fen2yuan(original.getAmount() + "");
        vo.money_receive = MoneyUtils.fen2yuan(original.getArrivalamount() + "");
        vo.money_fee = MoneyUtils.fen2yuan((original.getAmount() - original.getArrivalamount()) + "");
        vo.bankName = StringUtils.null2Length0(original.getBankname());
        vo.bankIcon = StringUtils.null2Length0(HttpCache.getResUrl(original.getBankicon()));
        vo.bankCardLast4No = StringUtils.null2Length0(MoneyUtils.getBankCardLast4No(original.getBankcardnumber()));
        vo.submitTime = StringUtils.null2Length0(original.getBizcreattime());
        vo.finishTime = StringUtils.null2Length0(original.getBizcompletetime());
        vo.serialNumber = StringUtils.null2Length0(original.getSerialnumber());
        return vo;
    }
}
