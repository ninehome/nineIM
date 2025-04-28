package com.tiocloud.chat.util;

import com.tiocloud.chat.R;
import com.watayouxiang.imclient.utils.ConstantUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    /**
     * 根据不同时间段，显示不同时间
     *
     * @param date
     * @return
     */
    public static String getTodayTimeBucket(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat timeformatter0to11 = new SimpleDateFormat("KK:mm", Locale.getDefault());
        SimpleDateFormat timeformatter1to12 = new SimpleDateFormat("hh:mm", Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5) {
            return "凌晨 " + timeformatter0to11.format(date);
        } else if (hour >= 5 && hour < 12) {
            return "上午 " + timeformatter0to11.format(date);
        } else if (hour >= 12 && hour < 18) {
            return "下午 " + timeformatter1to12.format(date);
        } else if (hour >= 18 && hour < 24) {
            return "晚上 " + timeformatter1to12.format(date);
        }
        return "";
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {ConstantUtils.context.getString(R.string.xingqiri),
                ConstantUtils.context.getString(R.string.xingqiyi), ConstantUtils.context.getString(R.string.xingqier),
                ConstantUtils.context.getString(R.string.xingqisan), ConstantUtils.context.getString(R.string.xingqisi),
                ConstantUtils.context.getString(R.string.xingqiwu), ConstantUtils.context.getString(R.string.xingqiliu)};
        // String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    /**
     * 是否同一年
     *
     * @param current
     * @param today
     * @return
     */
    private static boolean isSameYearDates(Date current, Date today) {
        Calendar currentCal = Calendar.getInstance();
        Calendar todayCal = Calendar.getInstance();
        currentCal.setTime(current);
        todayCal.setTime(today);
        return currentCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR);
    }

    /**
     * 是否在同一周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }


    // ====================================================================================
    // 获取显示时间
    // ====================================================================================

    public static String getShowTime(long milliseconds, boolean abbreviate) {
        // 输入时间（日期）
        Date inputData = new Date(milliseconds);
        // 输入时间（时分）
        String inputTime24 = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(inputData);

        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);

        // 今天的开始
        Date todayBegin = todayStart.getTime();
        // 昨天的开始
        Date yesterdayBegin = new Date(todayBegin.getTime() - 3600 * 24 * 1000);
        // 前天的开始
        Date preYesterdayBegin = new Date(yesterdayBegin.getTime() - 3600 * 24 * 1000);

        String dataString;

        if (!inputData.before(todayBegin)) {// 今天
            dataString = inputTime24;
        } else if (!inputData.before(yesterdayBegin)) {// 昨天
            if (abbreviate) {
                dataString = ConstantUtils.context.getString(R.string.zuotian);
            } else {
                dataString = ConstantUtils.context.getString(R.string.zuotian)+" " + inputTime24;
            }
        } else if (!inputData.before(preYesterdayBegin)) {// 前天
            if (abbreviate) {
                dataString = ConstantUtils.context.getString(R.string.qiantian);
            } else {
                dataString = ConstantUtils.context.getString(R.string.qiantian) + " " + inputTime24;
            }
        } else if (isSameWeekDates(inputData, new Date())) {// 本周
            if (abbreviate) {
                dataString = getWeekOfDate(inputData);
            } else {
                dataString = getWeekOfDate(inputData) + " " + inputTime24;
            }
        } else if (isSameYearDates(inputData, new Date())) {// 本年
            if (abbreviate) {
                dataString = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(inputData);
            } else {
                dataString = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(inputData) + " " + inputTime24;
            }
        } else {// 其他
            if (abbreviate) {
                dataString = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(inputData);
            } else {
                dataString = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(inputData) + " " + inputTime24;
            }
        }

        return dataString;
    }

    // ====================================================================================
    // 其他
    // ====================================================================================

    /**
     * dareString 转 时间戳
     *
     * @param dateString
     * @return
     */
    public static Long dateString2Long(String dateString) {
        Long time = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = format.parse(dateString);
            time = date.getTime();
        } catch (Exception ignored) {
        }
        return time;
    }

    public static String dateLong2String(Long time) {
        return dateLong2String(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateLong2String(Long time, String pattern) {
        if (time == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(new Date(time));
    }

    /**
     * 总毫秒转 %s:%s
     *
     * @param totalMS
     * @return
     */
    public static String formatMS(long totalMS) {
        totalMS = totalMS / 1000;
        long min = totalMS / 60 % 60;
        long sec = totalMS % 60;
        DecimalFormat df = new DecimalFormat("00");
        return String.format("%s:%s", df.format(min), df.format(sec));
    }
}
