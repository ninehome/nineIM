package com.watayouxiang.wallet.tools;

import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/17
 *     desc   :
 * </pre>
 */
public class MoneyUtils {
    /**
     * 分 -> 元(保留两位小数)
     */
    @Nullable
    public static String fen2yuan(String fen) {
        try {
            BigDecimal _money = new BigDecimal(fen);
            BigDecimal _100 = new BigDecimal(100);
            BigDecimal _temp = _money.divide(_100);
            return new DecimalFormat("0.00").format(_temp);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static String fen2yuan(BigDecimal fen) {
        try {
            BigDecimal _100 = new BigDecimal(100);
            BigDecimal _yuan = fen.divide(_100);
            return new DecimalFormat("0.00").format(_yuan);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 元 -> 分(不保留小数)
     */
    @Nullable
    public static String yuan2fen(String yuan) {
        try {
            BigDecimal _money = new BigDecimal(yuan);
            BigDecimal _100 = new BigDecimal(100);
            BigDecimal _temp = _money.multiply(_100);
            return String.valueOf(_temp.intValue());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 格式化 元(保留两位小数)
     */
    @Nullable
    public static String formatYuan(String yuan) {
        try {
            BigDecimal _temp = new BigDecimal(yuan);
            return new DecimalFormat("0.00").format(_temp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 格式化 元(保留两位小数)
     */
    @Nullable
    public static String formatYuan(String yuan, String count) {
        try {
            BigDecimal _yuan = new BigDecimal(yuan);
            BigDecimal _count = new BigDecimal(count);
            BigDecimal _temp = _yuan.multiply(_count);
            return new DecimalFormat("0.00").format(_temp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取银行卡后四位
     */
    @Nullable
    public static String getBankCardLast4No(String bankCardNo) {
        if (bankCardNo != null && bankCardNo.length() >= 4) {
            return bankCardNo.substring(bankCardNo.length() - 4);
        }
        return null;
    }
}
