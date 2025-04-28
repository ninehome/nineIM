package com.watayouxiang.wallet.feature.redpaper;

import androidx.annotation.IntDef;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/12
 *     desc   :
 * </pre>
 */
@IntDef({RedPaperType.Group, RedPaperType.P2P})
public @interface RedPaperType {
    public static final int Group = 1;
    public static final int P2P = 2;
}
