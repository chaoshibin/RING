package com.ring.common.util;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author chaoshibin
 */
public final class DateUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);
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
     * 默认格式
     *
     * @param date
     * @return
     */
    public static String formatDefault(Date date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        return FastDateFormat.getInstance(YYYY_MM_DD_HH_MM_SS).format(date);
    }

    /**
     * 格式化日期
     *
     * @param date    Date
     * @param pattern 日期格式 e.g. yyyy-MM-dd HH:mm:ss
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
     * @return
     */
    public static Date parseDefault(String dateStr) {
        Date date;
        try {
            date = FastDateFormat.getInstance(YYYY_MM_DD_HH_MM_SS).parse(dateStr);
        } catch (ParseException e) {
            LOG.error("parse Date failure", e);
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * 解析日期
     *
     * @param dateStr Date
     * @param pattern 日期格式 e.g. yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date parse(String dateStr, String pattern) {
        Date date;
        try {
            date = FastDateFormat.getInstance(pattern).parse(dateStr);
        } catch (ParseException e) {
            LOG.error("parse Date failure", e);
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * 获取当日零点时间 yyyy-MM-dd 00:00:00
     *
     * @return Date
     */
    public static Date getTodayOfStart() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        return instance.getTime();
    }

    /**
     * 获取当日结束时间 yyyy-MM-dd 23:59:59
     *
     * @return Date
     */
    public static Date getTodayOfEnd() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 59);
        return instance.getTime();
    }

    /**
     * 获取某日零点时间 yyyy-MM-dd 00:00:00
     *
     * @return Date
     */
    public static Date getDayOfStart(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        return instance.getTime();
    }

    /**
     * 获取某日结束时间 yyyy-MM-dd 23:59:59
     *
     * @return Date
     */
    public static Date getDayOfEnd(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 59);
        return instance.getTime();
    }

    /**
     * 对时间域加值
     *
     * @param date   操作时间
     * @param field  Calendar.YEAR/.../HOUR_OF_DAY/MINUTE
     * @param amount 数值
     * @return 取得时间
     */
    public static Date incrementField(Date date, int field, int amount) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(field, amount);
        return instance.getTime();
    }

    /**
     * 对时间域设值
     *
     * @param date  操作时间
     * @param field Calendar.YEAR/.../HOUR_OF_DAY/MINUTE
     * @param value 数值
     * @return 取得时间
     */
    public static Date setField(Date date, int field, int value) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(field, value);
        return instance.getTime();
    }
}
