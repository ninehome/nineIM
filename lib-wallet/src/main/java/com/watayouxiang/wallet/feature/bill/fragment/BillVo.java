package com.watayouxiang.wallet.feature.bill.fragment;

import com.watayouxiang.imclient.utils.ConstantUtils;
import com.watayouxiang.wallet.R;

import java.io.Serializable;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/26
 *     desc   :
 * </pre>
 */
public class BillVo implements Serializable {
    public static final BillVo ALL = new BillVo(ConstantUtils.context.getString(R.string.quanbu), null);
    public static final BillVo RECHARGE = new BillVo(ConstantUtils.context.getString(R.string.chongzhi), "1");
    public static final BillVo WITHDRAW = new BillVo(ConstantUtils.context.getString(R.string.tixian), "2");
    public static final BillVo RED_PAPER = new BillVo(ConstantUtils.context.getString(R.string.hongbao), "3");

    public String name;
    public String mode;

    private BillVo(String name, String mode) {
        this.name = name;
        this.mode = mode;
    }
}
