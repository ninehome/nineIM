package com.watayouxiang.wallet.feature.redpaper;

import java.io.Serializable;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/25
 *     desc   :
 * </pre>
 */
public class RedPaperVo implements Serializable {
    @RedPaperType
    public int type;
    public String chatlinkid;

    public RedPaperVo(int type, String chatlinkid) {
        this.type = type;
        this.chatlinkid = chatlinkid;
    }
}
