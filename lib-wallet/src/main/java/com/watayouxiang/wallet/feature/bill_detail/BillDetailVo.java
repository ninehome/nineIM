package com.watayouxiang.wallet.feature.bill_detail;

import com.watayouxiang.httpclient.model.response.PayGetWalletItemsResp;
import com.watayouxiang.wallet.tools.MoneyUtils;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/30
 *     desc   :
 * </pre>
 */
public class BillDetailVo implements Serializable {
    // 业务类型：1充值，2提现，3红包
    private int bizMode;
    // 业务描述：充值/收红包/发红包
    private String modeDesc;
    // 业务状态：SUCCESS，PROCESS，FAIL
    private String bizStatus;
    // 时间
    private String time;
    // 单号
    private String serialNumber;
    // 金额（+9.9）
    private String money;
    // 收支状态：1收入，2支出
    private int coinFlag;

    private HashMap<String, Object> data;

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public static BillDetailVo getInstance(PayGetWalletItemsResp.ListBean bean) {
        BillDetailVo vo = new BillDetailVo();
        vo.setBizMode(bean.getMode());
        vo.setModeDesc(bean.getRemark());
        vo.setBizStatus("SUCCESS");
        vo.setTime(bean.getCreatetime());
        vo.setSerialNumber(bean.getBiznumber());
        vo.setData(bean.getData());
//        vo.setModeDesc(bean.getBizstr());
//        vo.setBizStatus(bean.getOrderstatus());
//        vo.setTime(bean.getBizcreattime());
//        vo.setSerialNumber(bean.getSerialnumber());
        vo.setMoney(MoneyUtils.fen2yuan(bean.getAmount() + ""));
        vo.setCoinFlag(bean.getCoinflag());
        return vo;
    }

    public int getBizMode() {
        return bizMode;
    }

    public void setBizMode(int bizMode) {
        this.bizMode = bizMode;
    }

    public String getModeDesc() {
        return modeDesc;
    }

    public void setModeDesc(String modeDesc) {
        this.modeDesc = modeDesc;
    }

    public String getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(String bizStatus) {
        this.bizStatus = bizStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getCoinFlag() {
        return coinFlag;
    }

    public void setCoinFlag(int coinFlag) {
        this.coinFlag = coinFlag;
    }

    public String getBizModeStr() {
        String modeStr = null;
        if (bizMode == 1) {
            modeStr = "充值";
        } else if (bizMode == 2) {
            modeStr = "提现";
        } else if (bizMode == 3) {
            modeStr = "红包";
        }
        return modeStr;
    }

    public String getBizStatusStr() {
        String statusStr = null;
        if (data == null || data.get("status") == null){
            return "成功";
        }
        Float integer;
        try {
            integer = Float.parseFloat(String.valueOf(data.get("status")));
        }catch (Exception e){
            e.printStackTrace();
            return "成功1";
        }
        if (integer == 1) {
            statusStr = "待审核";
        } else if (integer == 2) {
            statusStr = "审核中";
        } else if (integer == 3) {
            statusStr = "已通过";
        }else if (integer == 4) {
            statusStr = "拒绝";
        } else if (integer == 5) {
            statusStr = "成功";
        }else {
            statusStr = String.format(Locale.getDefault(), "订单状态(%s)", integer);
        }
        return statusStr;
    }

    public String getMoneyStr() {
        // coinFlag：1收入，2支出
        String moneyStr = null;
        if (coinFlag == 1) {
            moneyStr = "+" + money;
        } else if (coinFlag == 2) {
            moneyStr = "-" + money;
        }
        return moneyStr;
    }
}
