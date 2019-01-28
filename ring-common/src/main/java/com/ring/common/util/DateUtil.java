package com.ring.common.util;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author chaoshibin
 */
public final class DateUtil extends DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD_HH_MM_SS_SLASH = "yyyy/MM/dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMMSS_SSS = "yyyyMMddHHmmssSSS";

    private DateUtil() {
    }

    /**
     * 默认格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDefault(Date date) {
        return format(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 解析日期默认格式 yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr Date
     * @return
     */
    public static Date parseDefault(String dateStr) {
        return parse(dateStr, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 格式 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatYMD(Date date) {
        return format(date, YYYY_MM_DD);
    }

    /**
     * 格式 yyyy-MM-dd
     *
     * @param dateStr
     * @return
     */
    public static Date parseYMD(String dateStr) {
        return parse(dateStr, YYYY_MM_DD);
    }

    /**
     * 格式 yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String formatYMDWithoutSeparate(Date date) {
        return format(date, YYYYMMDD);
    }

    /**
     * 格式 yyyyMMdd
     *
     * @param dateStr
     * @return
     */
    public static Date parseYMDWithoutSeparate(String dateStr) {
        return parse(dateStr, YYYYMMDD);
    }

    /**
     * 格式化日期
     *
     * @param date    Date
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        return FastDateFormat.getInstance(pattern).format(date);
    }

    /**
     * 解析日期
     *
     * @param dateStr Date
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String dateStr, String pattern) {
        Date date;
        try {
            date = FastDateFormat.getInstance(pattern).parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public static Date dateOfBegin(Date date){
        return truncate(date, Calendar.DATE);
    }

    public static Date dateOfEnd(Date date){
        return ceiling(date, Calendar.DATE);
    }
}
