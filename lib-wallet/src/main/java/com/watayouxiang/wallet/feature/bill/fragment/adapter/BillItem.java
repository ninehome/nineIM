package com.watayouxiang.wallet.feature.bill.fragment.adapter;

import com.blankj.utilcode.util.StringUtils;
import com.watayouxiang.httpclient.model.response.PayGetWalletItemsResp;
import com.watayouxiang.wallet.tools.MoneyUtils;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/04
 *     desc   :
 * </pre>
 */
public class BillItem {

    private String title;
    private String subtitle;
    private String rightTitle;
    private String rightTitleTextColor;
    private String rightSubtitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = StringUtils.null2Length0(title);
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = StringUtils.null2Length0(subtitle);
    }

    public String getRightTitle() {
        return rightTitle;
    }

    public void setRightTitle(PayGetWalletItemsResp.ListBean bean) {
        String money = MoneyUtils.fen2yuan(bean.getAmount() + "");
        boolean isPlus = bean.getCoinflag() == 1;
        this.rightTitle = (isPlus ? "+" : "-") + money;
    }

    public String getRightTitleTextColor() {
        return rightTitleTextColor;
    }

    public void setRightTitleTextColor(PayGetWalletItemsResp.ListBean bean) {
        // 订单状态：SUCCESS;FAIL;PROCESS
        String status = /*bean.getOrderstatus()*/"SUCCESS";
        boolean isPlus = bean.getCoinflag() == 1;
        String temp;
        if ("PROCESS".equals(status) || "SUCCESS".equals(status)) {
            if (isPlus) {
                temp = "#FF06D89A";
            } else {
                temp = "#FF000000";
            }
        } else {
            temp = "#999999";
        }
        this.rightTitleTextColor = temp;
    }

    public String getRightSubtitle() {
        return rightSubtitle;
    }

    public void setRightSubtitle(PayGetWalletItemsResp.ListBean bean) {
        // 订单状态：SUCCESS;FAIL;PROCESS
        if (bean.getData() == null){
            this.rightSubtitle = "成功";
            return;
        }
        if (bean.getMode() != 1 && bean.getMode() != 2){
            this.rightSubtitle = "";
            return;
        }
        Float integer;
        try {
            integer = Float.parseFloat(String.valueOf(bean.getData().get("status")));
        }catch (Exception e){
            e.printStackTrace();
            this.rightSubtitle = "成功";
            return;
        }
        if (integer == 1) {
            this.rightSubtitle = "待审核";
        } else if (integer == 2) {
            this.rightSubtitle = "审核中";
        } else if (integer == 3) {
            this.rightSubtitle = "已通过";
        }else if (integer == 4) {
            this.rightSubtitle = "拒绝";
        } else if (integer == 5) {
            this.rightSubtitle = "成功";
        }else {
            this.rightSubtitle = String.format(Locale.getDefault(), "订单状态(%s)", integer);
        }
    }
}
