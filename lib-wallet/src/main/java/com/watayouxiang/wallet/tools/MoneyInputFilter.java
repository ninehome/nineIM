package com.watayouxiang.wallet.tools;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/09
 *     desc   :
 *
 *     https://blog.csdn.net/jwmxxx/article/details/77531702
 *     https://blog.csdn.net/guangdeshishe/article/details/93888388
 * </pre>
 */
public class MoneyInputFilter implements InputFilter {
    private final EditText etTxt;

    public MoneyInputFilter(EditText etTxt) {
        this.etTxt = etTxt;
    }

    /**
     * @param source 将要输入的字符串,如果是删除操作则为空字符串
     * @param start  将要输入的字符串起始下标，一般为0
     * @param end    start + source字符的长度
     * @param dest   输入之前文本框中的内容
     * @param dstart 将会被替换的起始位置
     * @param dend   dstart+将会被替换的字符串长度
     * @return 方法返回的值将会替换掉dest字符串中dstartd位置到dend位置之间字符，返回source表示不做任何处理，返回空字符串""表示不输入任何字符
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (source.equals(".") && dstart == 0 && dend == 0) {// 判断小数点是否在第一位
            etTxt.setText(String.format(Locale.getDefault(), "0%s%s", source, dest));// 给小数点前面加0
            etTxt.setSelection(2);// 设置光标
        }
        // 判断小数点是否存在并且小数点后面是否已有两个字符
        if (dest.toString().contains(".") && (dest.length() - dest.toString().indexOf(".")) > 2) {
            if ((dest.length() - dstart) < 3) {//判断现在输入的字符是不是在小数点后面
                return "";// 过滤当前输入的字符
            }
        }
        //判断..情况
        if (dest.toString().contains(".") && source.toString().equals(".")) {
            return "";// 过滤当前输入的字符
        }
        // 非法小数点输入
        int posDot = dest.toString().indexOf(".");
        if (start < end && posDot < 0 && dest.toString().equals("0") && !source.equals(".")) {
            return "";
        }
        return null;
    }
}
