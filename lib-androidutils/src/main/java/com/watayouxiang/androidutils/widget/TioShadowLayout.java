package com.watayouxiang.androidutils.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.blankj.utilcode.util.SizeUtils;
import com.qmuiteam.qmui.layout.QMUILayoutHelper;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/12/21
 *     desc   :
 * </pre>
 */
public class TioShadowLayout extends QMUILinearLayout {

    public TioShadowLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TioShadowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TioShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
//        setBorderColor(Color.BLUE);
//        setBottom();
//        setBorderWidth(1);
//        setRadiusAndShadow(
//                SizeUtils.dp2px(15),// radius
//                QMUILayoutHelper.HIDE_RADIUS_SIDE_NONE,// hideRadiusSide
//                SizeUtils.dp2px(14),// shadowElevation
//                Color.BLUE,// shadowColor
//                0.25f// shadowAlpha
//        );
    }

}
