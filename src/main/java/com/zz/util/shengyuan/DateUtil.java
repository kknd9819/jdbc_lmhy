package com.zz.util.shengyuan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class DateUtil {
    public static final String DATE_PATTERN = "yyyyMMdd";
    public static final String DATE_TIME_PATTERN = "yyyyMMddHHmmss";
    public static final String TIME_PATTERN = "HHmmss";
    public static final String YYYY_MM = "yyyyMM";
    public static final String YYYY_MM_CHINA = "yyyy年MM月";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_SMS_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String Date_JS_FORMAT = "yyyy/MM/dd,";
    public static final String Date_JS_FORMAT_YMDHMS = "yyyy/MM/dd,HH:mm:ss";
    public static final String DATE_TO_ORDER = "MM月dd日";
    public static final String DATE_TO_ORDER_HH = "HH:mm";

    public DateUtil() {
    }

    public static String format(long millis, String pattern) {
        return format(new Date(millis), pattern);
    }

    public static String format(Date date, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static String formatDate(Date date) {
        return format(date, "yyyyMMdd");
    }

    public static String formatDate(String date) {
        String sp = null;
        String tp = null;
        if(date.length() == 8) {
            sp = "yyyyMMdd";
            tp = "yyyy-MM-dd";
        } else {
            if(date.length() != 10) {
                throw new IllegalArgumentException("不支持的日期字符串:" + date);
            }

            sp = "yyyy-MM-dd";
            tp = "yyyyMMdd";
        }

        return format(parse(date, sp), tp);
    }

    public static String formatTime(Date date) {
        return format(date, "HHmmss");
    }

    public static String formatTime(String date) {
        String sp = null;
        String tp = null;
        if(date.length() == 6) {
            sp = "HHmmss";
            tp = "HH:mm:ss";
        } else {
            if(date.length() != 8) {
                throw new IllegalArgumentException("不支持的时间字符串:" + date);
            }

            sp = "HH:mm:ss";
            tp = "HHmmss";
        }

        return format(parse(date, sp), tp);
    }

    public static String formatDateTime(Date date) {
        return format(date, "yyyyMMddHHmmss");
    }

    public static String formatDateTime(String date) {
        String sp = null;
        String tp = null;
        if(date.length() == 14) {
            sp = "yyyyMMddHHmmss";
            tp = "yyyy-MM-dd HH:mm:ss";
        } else {
            if(date.length() != 19) {
                throw new IllegalArgumentException("不支持的日期时间字符串:" + date);
            }

            sp = "yyyy-MM-dd HH:mm:ss";
            tp = "yyyyMMddHHmmss";
        }

        return format(parse(date, sp), tp);
    }

    public static String formatCurrent(String pattern) {
        return format(new Date(), pattern);
    }

    public static String formatCurrentDate() {
        return format(new Date(), "yyyyMMdd");
    }

    public static String formatCurrentTime() {
        return format(new Date(), "HHmmss");
    }

    public static String formatCurrentDateTime() {
        return format(new Date(), "yyyyMMddHHmmss");
    }

    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getTheDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date parse(String dateString, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        try {
            return formatter.parse(dateString);
        } catch (ParseException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static Date addYears(Date date, int amount) {
        return add(date, 1, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return add(date, 2, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return add(date, 3, amount);
    }

    public static Date addDays(Date date, int amount) {
        return add(date, 5, amount);
    }

    public static Date addHours(Date date, int amount) {
        return add(date, 11, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return add(date, 12, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return add(date, 13, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return add(date, 14, amount);
    }

    private static Date add(Date date, int calendarField, int amount) {
        if(date == null) {
            throw new IllegalArgumentException("日期对象不允许为null!");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }

    public static String friendlyTime(Date time) {
        if(time == null) {
            return "时间不明";
        } else {
            int ct = (int)((System.currentTimeMillis() - time.getTime()) / 1000L);
            if(ct < 3600) {
                return Math.max(ct / 60, 1) + "分钟前";
            } else if(ct >= 3600 && ct < 86400) {
                return ct / 3600 + "小时前";
            } else if(ct >= 86400 && ct < 2592000) {
                int day = ct / 86400;
                return day > 1?day + "天前":"昨天";
            } else {
                return ct >= 2592000 && ct < 31104000?ct / 2592000 + "月前":ct / 31104000 + "年前";
            }
        }
    }

    public static Date getMonthFirstDay(int months) {
        Calendar cal = Calendar.getInstance();
        cal.set(5, 1);
        cal.add(2, months);
        return cal.getTime();
    }

    public static Date getMonthLastDay(int months) {
        Calendar cal = Calendar.getInstance();
        cal.set(5, 1);
        cal.add(2, months + 1);
        cal.add(5, -1);
        return cal.getTime();
    }

    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(date);
        calendar.set(7, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(date);
        calendar.set(7, calendar.getFirstDayOfWeek() + 6);
        return calendar.getTime();
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, 1);
        calendar.set(5, 1);
        calendar.add(5, -1);
        return calendar.getTime();
    }

    public static void dealStartDateAndEndDate(Map<String, Object> param) {
        Calendar calendar = Calendar.getInstance();
        Date statisticsDate = null;
        String endTime = "";
        String startTime = "";
        if("D".equals(param.get("type"))) {
            if(null != param.get("statisticsDate") && !"".equals(param.get("statisticsDate"))) {
                statisticsDate = parse(param.get("statisticsDate").toString(), "yyyy-MM-dd");
            } else {
                calendar.add(6, -1);
                statisticsDate = calendar.getTime();
            }

            startTime = formatDate(formatDate(statisticsDate)) + " 00:00:00";
            endTime = formatDate(formatDate(statisticsDate)) + " 23:59:59";
        } else if("W".equals(param.get("type"))) {
            if(null != param.get("statisticsDate") && !"".equals(param.get("statisticsDate"))) {
                statisticsDate = parse(param.get("statisticsDate").toString(), "yyyy-MM-dd");
            } else {
                calendar.add(3, -1);
                statisticsDate = calendar.getTime();
            }

            startTime = formatDate(formatDate(getFirstDayOfWeek(statisticsDate))) + " 00:00:00";
            endTime = formatDate(formatDate(getLastDayOfWeek(statisticsDate))) + " 23:59:59";
        } else if("M".equals(param.get("type"))) {
            if(null != param.get("statisticsDate") && !"".equals(param.get("statisticsDate"))) {
                statisticsDate = parse(param.get("statisticsDate").toString(), "yyyy-MM-dd");
            } else {
                calendar.add(2, -1);
                statisticsDate = calendar.getTime();
            }

            startTime = formatDate(formatDate(getFirstDayOfMonth(statisticsDate))) + " 00:00:00";
            endTime = formatDate(formatDate(getLastDayOfMonth(statisticsDate))) + " 23:59:59";
        }

        param.put("startTime", startTime);
        param.put("endTime", endTime);
    }

    public static final Date stringToYYYYMMDDHHMMSS(String dateString) {
        return stringToDate(dateString, "yyyy-MM-dd HH:mm:ss");
    }

    public static final Date stringToYYYYMMDD(String dateString) {
        return stringToDate(dateString, "yyyy-MM-dd");
    }

    public static final Date stringToHHMMSS(String dateString) {
        return stringToDate(dateString, "HH:mm:ss");
    }

    public static final Date stringToDate(String dateString, String pattern) {
        if(StringUtil.isEmpty(dateString)) {
            return null;
        } else {
            if(StringUtil.isEmpty(pattern)) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }

            try {
                SimpleDateFormat formater = new SimpleDateFormat(pattern);
                return formater.parse(dateString);
            } catch (IllegalArgumentException var6) {
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    return formater.parse(dateString);
                } catch (ParseException var5) {
                    return null;
                }
            } catch (ParseException var7) {
                return null;
            }
        }
    }

    public static final String dateToStringYYYYMMDDHHMMSS(Date date) {
        return dateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static final String dateToStringYYYYMMDDHHMM(Date date) {
        return dateToString(date, "yyyy-MM-dd HH:mm");
    }

    public static final String dateToStringYYYYMMDD(Date date) {
        return dateToString(date, "yyyy-MM-dd");
    }

    public static final String dateToStringHHMMSS(Date date) {
        return dateToString(date, "HH:mm:ss");
    }

    public static final String dateToString(Date date, String pattern) {
        if(date == null) {
            return "";
        } else {
            if(StringUtil.isEmpty(pattern)) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }

            String dateStr = null;

            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                dateStr = sdf.format(date);
            } catch (IllegalArgumentException var5) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateStr = sdf.format(date);
            }

            return dateStr;
        }
    }

    public static int compareDate(Date date, Date compareDate) {
        if(date != null && compareDate != null) {
            long dateLong = date.getTime();
            long compareDateLong = compareDate.getTime();
            return dateLong < compareDateLong?-1:(dateLong == compareDateLong?0:(dateLong > compareDateLong?1:-2));
        } else {
            return -2;
        }
    }

    public static Date getDayHHMMSS(Date date, String hhmmss) {
        String latesTime = dateToStringYYYYMMDD(date) + " " + hhmmss;
        return stringToYYYYMMDDHHMMSS(latesTime);
    }

    public static int getIntervalSeconds(Date start, Date end) {
        if(compareDate(start, end) >= 0) {
            return 0;
        } else {
            long sl = start.getTime();
            long el = end.getTime();
            long ei = el - sl;
            return (int)(ei / 1000L);
        }
    }
}