package com.watayouxiang.androidutils.widget.edittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.blankj.utilcode.util.ResourceUtils;
import com.watayouxiang.androidutils.R;
import com.watayouxiang.androidutils.util.ViewUtil;


/**
 * author : TaoWang
 * date : 2020-01-03
 * desc : 输入框扩展
 * <p>
 * 1、左侧：自定义图片
 * 2、右侧：密码显隐 / 文字清除(默认不开启)
 */
public class TioEditText extends AppCompatEditText {

    private final boolean clearIconEnable;

    private final int pwdShowIconId = R.drawable.icon_see;
    private final int pwdHideIconId = R.drawable.icon_seeno;
    private final int clearIconId1 = R.drawable.btn_close_small;
    private final int clearIconId2 = R.drawable.btn_close_small;

    private int clearIconType = 1;

    private Drawable rightDrawable;
    private int rightDrawableId;
    private final Drawable leftDrawable;

    public TioEditText(Context context) {
        this(context, null);
    }

    public TioEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public TioEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TioEditText);
        clearIconEnable = a.getBoolean(R.styleable.TioEditText_tet_clearIconEnable, false);
        leftDrawable = a.getDrawable(R.styleable.TioEditText_tet_leftDrawable);
        clearIconType = a.getInteger(R.styleable.TioEditText_clearIconType, 1);
        a.recycle();

        initViews();

        updateRightDrawable();
    }

    private void initViews() {
        // 左侧图片
        if (leftDrawable != null) {
            setLeftDrawable(leftDrawable);
        }
    }

    // ============================================================================
    // inner
    // ============================================================================

    @Override
    public void setInputType(int type) {
        super.setInputType(type);
        updateRightDrawable();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                if (event.getX() > getWidth() - getPaddingRight() - getCompoundDrawables()[2].getIntrinsicWidth()) {
                    if (isPwdType()) {
                        // 密码类型
                        togglePwdRightDrawable();
                    } else {
                        // 非密码类型
                        if (rightDrawableId == clearIconId1 || rightDrawableId == clearIconId2) {
                            setText("");
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        manageRightDrawable();
    }

    private void manageRightDrawable() {
        if (rightDrawable != null){
//            // 显示右侧图片
            if (!TextUtils.isEmpty(getText())){
                if (getCompoundDrawables()[2] != rightDrawable) {
                    setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], rightDrawable, getCompoundDrawables()[3]);
                }
            }else {
                setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
            }
        }
//        if (!TextUtils.isEmpty(getText()) && rightDrawable != null) {
//            // 有文字
//            // 显示右侧图片
//            if (getCompoundDrawables()[2] != rightDrawable) {
//                setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], rightDrawable, getCompoundDrawables()[3]);
//            }
//        } else {
//            // 没有文字
//            // 隐藏右侧图片
//            if (getCompoundDrawables()[2] != null) {
//                setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
//            }
//        }
    }

    private void togglePwdRightDrawable() {
        if (!isShowPwd()) {
            // 密码不可见
            // 显示密码、显示密码右侧按钮
            showPwd();
            setRightDrawable(pwdShowIconId);
        } else {
            // 密码可见
            // 隐藏密码、隐藏密码右侧按钮
            hidePwd();
            setRightDrawable(pwdHideIconId);
        }
        setSelection(length());
    }

    // ============================================================================
    // 左侧图片
    // ============================================================================

    private void setLeftDrawable(@DrawableRes int id) {
        this.setLeftDrawable(ResourceUtils.getDrawable(id));
    }

    /**
     * 设置左侧图片
     *
     * @param leftDrawable 左侧图片
     */
    private void setLeftDrawable(Drawable leftDrawable) {
        if (leftDrawable != null) {
            leftDrawable.setBounds(0, 0, leftDrawable.getIntrinsicWidth(), leftDrawable.getIntrinsicHeight());
        }
        setCompoundDrawables(leftDrawable, getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
    }

    // ============================================================================
    // 右侧图片
    // ============================================================================

    private void updateRightDrawable() {
        if (isPwdType()) {
            setRightDrawable(isShowPwd() ? pwdShowIconId : pwdHideIconId);
        } else {
            if (clearIconEnable) {
                setRightDrawable(clearIconType == 1 ? clearIconId1 : clearIconId2);
            } else {
                setRightDrawable(null);
            }
        }
    }

    private void setRightDrawable(@DrawableRes int id) {
        rightDrawableId = id;
        this.setRightDrawable(ResourceUtils.getDrawable(id));
    }

    /**
     * 设置右侧图片
     * 当有文字时，图片显示
     * 当无文字时，图片隐藏
     *
     * @param drawable 图片
     */
    private void setRightDrawable(Drawable drawable) {
        rightDrawable = drawable;
        if (rightDrawable != null) {
            rightDrawable.setBounds(0, 0, rightDrawable.getIntrinsicWidth(), rightDrawable.getIntrinsicHeight());
        }
        manageRightDrawable();
    }

    // ============================================================================
    // 便捷方法
    // ============================================================================

    protected void hidePwd() {
        setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    protected void showPwd() {
        setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    protected boolean isShowPwd() {
        return getTransformationMethod() instanceof HideReturnsTransformationMethod;
    }

    protected boolean isPwdType() {
        return getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Nullable
    public String getSubmitText() {
        return ViewUtil.getText(this);
    }

}
