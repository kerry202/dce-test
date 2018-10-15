package cn.dagongniu.oax.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.dagongniu.oax.OAXApplication;

/**
 * 类描述：format date 时间
 */

public class DateUtils {
    static OAXApplication myApplication = OAXApplication.getInstance();
    public static final String FULL = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String COMMON = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD = "yyyy-MM-dd";
    public static final String HMS = "HH:mm:ss";
    public static final String HM = "HH:mm";
    public static final String MD = "MM-dd";

    public static String getSystemTime() {
        return parseMillisIntoString(System.currentTimeMillis(), COMMON);
    }

    public static String getSystemTime(String formatStyle) {
        return parseMillisIntoString(System.currentTimeMillis(), formatStyle);
    }

    public static String parseMillisIntoString(long millisString, String formatStyle) {
        if (millisString == 0) {
            return "未知";
        } else {
            Date date = new Date(millisString);
            SimpleDateFormat sdf = new SimpleDateFormat(formatStyle, Locale.getDefault());
            return sdf.format(date);
        }
    }

    public static String parseDate(Long time) {
        /**
         * 将毫秒数转化为时间
         */
        DateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return formatter.format(calendar.getTime());
    }

    public static String onDataFormat() {
        SimpleDateFormat dataFormat = new SimpleDateFormat(
                "yyyyMMddHHmmss");
        Date date = new Date();
        String format = dataFormat.format(date);
        return format;
    }

    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToDate1(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToDate2(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getStringTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        return dateString;
    }


    public static String formatDate(long time) {
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formatDate = dateFormat2.format(time);
        return formatDate;
    }

    public static Date StrCommonToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(COMMON);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getStringTimeHM(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(HM);
        String dateString = sdf.format(date);
        return dateString;
    }

    public static String getStringTimeMD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(MD);
        String dateString = sdf.format(date);
        return dateString;
    }
}
