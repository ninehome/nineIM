package com.watayouxiang.androidutils;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.watayouxiang.androidutils.util.PinYin;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/05
 *     desc   :
 * </pre>
 */
public class AndroidUtils {

    /**
     * 初始化
     */
    public static void init(Application app) {
        // pinyin
        PinYin.init(app);
        // fresco
        Fresco.initialize(app);
    }
}
