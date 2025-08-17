package com.watayouxiang.wallet.feature.recharge_result;

import androidx.annotation.IntDef;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/20
 *     desc   :
 * </pre>
 */
@IntDef({RechargeResultType.SUCCESS, RechargeResultType.FAILED, RechargeResultType.PROCESS})
public @interface RechargeResultType {
    int SUCCESS = 1;
    int FAILED = 2;
    int PROCESS = 3;
}
