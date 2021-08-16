package cn.maidaotech.smartapi.common.utils;

import cn.maidaotech.smartapi.api.merchant.entity.Duration;
import cn.maidaotech.smartapi.api.merchant.entity.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author ZKM
 * @date 2019-11-25
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {
    public static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("GMT+08:00");
    public static final String PATTERN_DEFAULT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DEFAULT_DATE = "yyyy-MM-dd";
    public static final String PATTERN_YYYYMMDD = "yyyyMMdd";
    public static final String PATTERN__YYMMDD = "yyMMdd";
    public static final String PATTERN_DEFAULT_TIME = "HH:mm:ss";
    public static final String PATTERN_YYYYMMDDHHmmss = "yyyyMMddHHmmss";

    /**
     * 当前日期转换成自定义的格式
     */
    public static String getCurrentDateStr(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 当前日期转换成yyyy-MM-dd字符串
     */
    public static String getCurrentDateStr() {
        return getCurrentDateStr(PATTERN_DEFAULT_DATE);
    }

    /**
     * 当前日期转换成yyyy-MM-dd HH:mm:ss字符串
     */
    public static String getCurrentDateTimeStr() {
        return format(new Date(), PATTERN_DEFAULT_DATETIME);
    }

    /**
     * 将Date类型的日期转换为yyyy-MM-dd 格式
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat(PATTERN_DEFAULT_DATE).format(date);
    }

    /**
     * 将Date类型的日期转换为yyyy-MM-dd HH:mm:ss格式
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return new SimpleDateFormat(PATTERN_DEFAULT_DATETIME).format(date);
    }

    /**
     * 将Date类型的日期转换为系统参数定义的格式的字符串。
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null || StringUtils.isEmpty(pattern)) {
            return null;
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String formatToSecond(Date date, TimeZone timezone) {
        if (date == null) {
            return null;
        }
        return formatToSecond(date.getTime(), timezone);
    }

    public static String formatToSecond(long timestamp, TimeZone timezone) {
        if (timezone == null) {
            timezone = DEFAULT_TIMEZONE;
        }
        Calendar cal = Calendar.getInstance(timezone);
        cal.setTimeInMillis(timestamp);
        StringBuilder buf = new StringBuilder();
        buf.append(cal.get(Calendar.YEAR)).append('-');
        buf.append(pad2(cal.get(Calendar.MONTH) + 1)).append('-');
        buf.append(pad2(cal.get(Calendar.DATE))).append(' ');
        buf.append(pad2(cal.get(Calendar.HOUR_OF_DAY))).append(':');
        buf.append(pad2(cal.get(Calendar.MINUTE))).append(':');
        buf.append(pad2(cal.get(Calendar.SECOND)));
        return buf.toString();
    }

    public static String formatToMinute(Date date, TimeZone timezone) {
        if (date == null) {
            return null;
        }
        return formatToMinute(date.getTime(), timezone);
    }

    public static String formatToMinute(long timestamp, TimeZone timezone) {
        if (timezone == null) {
            timezone = DEFAULT_TIMEZONE;
        }
        Calendar cal = Calendar.getInstance(timezone);
        cal.setTimeInMillis(timestamp);
        StringBuilder buf = new StringBuilder();
        buf.append(cal.get(Calendar.YEAR)).append('-');
        buf.append(pad2(cal.get(Calendar.MONTH) + 1)).append('-');
        buf.append(pad2(cal.get(Calendar.DATE))).append(' ');
        buf.append(pad2(cal.get(Calendar.HOUR_OF_DAY))).append(':');
        buf.append(pad2(cal.get(Calendar.MINUTE)));
        return buf.toString();
    }

    /**
     * 字符串格式化成日期
     */
    public static Date parseDate(String dateStr, String pattern) {
        if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(pattern)) {
            return null;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串格式化成日期
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, PATTERN_DEFAULT_DATE);
    }

    /**
     * 字符串格式化成日期
     */
    public static Date parseDateTime(String dateStr) {
        return parseDate(dateStr, PATTERN_DEFAULT_DATETIME);
    }

    public static Date now() {
        return new Date();
    }


    private static final Log logger = LogFactory.getLog(DateUtils.class);

    public static final String DATE_SHORT_FORMAT = "yyyyMMdd";
    public static final String DATE_CH_FORMAT = "yyyy年MM月dd日";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_MONTH_FORMAT = "yyyy-MM";
    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final String DAYTIME_START = "00:00:00";
    public static final String DAYTIME_END = "23:59:59";

    private static final String[] FORMATS = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd HH:mm:ss", "HH:mm", "HH:mm:ss", "yyyy-MM",
            "yyyy-MM-dd HH:mm:ss.S"};


    /**
     * 字符串转换成日期 主要解决json传入日期问题
     */
    public static Date convert(String str) {
        if (str != null && str.length() > 0) {
            if (str.length() > 10 && str.charAt(10) == 'T') {
                str = str.replace('T', ' ');// 去掉json-lib加的T字母
            }
            for (String format : FORMATS) {
                if (format.length() == str.length()) {
                    try {
                        Date date = new SimpleDateFormat(format).parse(str);
                        return date;
                    } catch (ParseException e) {
                        if (logger.isWarnEnabled()) {
                            logger.warn(e.getMessage());
                        }
                    }
                }
            }
        }
        return null;
    }


    /**
     * 将日期转换成format字符串
     *
     * @param date       例如: Sun Jun 10 09:18:00 CST 2018
     * @param dateFormat 例如: "yyyy-MM-dd HH:mm:ss"
     */
    public static String convert(Date date, String dateFormat) {
        if (date == null) {
            return null;
        }
        if (null == dateFormat) {
            dateFormat = DATE_TIME_FORMAT;
        }
        return new SimpleDateFormat(dateFormat).format(date);
    }

    /**
     * 根据传入的日期  转换成这样格式的字符串 如：“yyyy-MM-dd HH:mm:ss”
     */
    public static String convert(Date date) {
        return convert(date, DATE_TIME_FORMAT);
    }


    /**
     * 根据传入的日期返回年月日的6位字符串，例：20101203
     */
    public static String getDay(Date date) {
        return convert(date, DATE_SHORT_FORMAT);
    }

    /**
     * 根据传入的日期返回中文年月日字符串，例：2010年12月03日
     */
    public static String getChDate(Date date) {
        return convert(date, DATE_CH_FORMAT);
    }

    /**
     * 返回该天从00:00:00开始的日期
     */
    public static Date getStartDatetime(Date date) {
        String thisDate = convert(date, DATE_FORMAT);
        return convert(thisDate + " " + DAYTIME_START);
    }

    /**
     * 返回该天到23:59:59结束的日期
     */
    public static Date getEndDatetime(Date date) {
        String thisDate = convert(date, DATE_FORMAT);
        return convert(thisDate + " " + DAYTIME_END);
    }

    /**
     * 返回n天到23:59:59结束的日期
     */
    public static Date getEndDatetime(Date date, Integer diffDays) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String thisDate = dateFormat.format(date.getTime() + MILLIS_PER_DAY * diffDays);
        return convert(thisDate + " " + DAYTIME_END);
    }


    /**
     * 将传入的时间格式的字符串转成时间对象      例如：传入2012-12-03 23:21:24
     */
    public static Date strToDate(String dateStr) {
        SimpleDateFormat formatDate = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = formatDate.parse(dateStr);
        } catch (Exception e) {

        }
        return date;
    }

    /**
     * 返回该日期的最后一刻，精确到纳秒
     */
    public static Timestamp getLastEndDatetime(Date endTime) {
        Timestamp ts = new Timestamp(endTime.getTime());
        ts.setNanos(999999999);
        return ts;
    }

    /**
     * 返回该日期加1秒
     */
    public static Timestamp getEndTimeAdd(Date endTime) {
        Timestamp ts = new Timestamp(endTime.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ts);
        calendar.add(Calendar.MILLISECOND, 1000);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 返回该日期加 millisecond 毫秒，正数为加，负数为减
     */
    public static Timestamp getDateAdd(Date date, int millisecond) {
        Timestamp ts = new Timestamp(date.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ts);
        calendar.add(Calendar.MILLISECOND, millisecond);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 相对当前日期，增加或减少天数
     */
    public static String addDay(Date date, int day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        return simpleDateFormat.format(new Date(date.getTime() + MILLIS_PER_DAY * day));
    }

    /**
     * 相对当前日期，增加或减少天数
     */
    public static Date addDayToDate(Date date, int day) {
        return new Date(date.getTime() + MILLIS_PER_DAY * day);
    }

    public static Long getTimeDiff(String startTime, String endTime) {
        int months = 0;
        Long days = null;
        Date startDate = null;
        Date endDate = null;
        try {
            if (startTime.length() == 10 && endTime.length() == 10) {
                startDate = new SimpleDateFormat(DATE_FORMAT).parse(startTime);
                endDate = new SimpleDateFormat(DATE_FORMAT).parse(endTime);
                days = getTimeDiff(startDate, endDate);
            } else if (startTime.length() == 7 && endTime.length() == 7) {
                startDate = new SimpleDateFormat(DATE_MONTH_FORMAT).parse(startTime);
                endDate = new SimpleDateFormat(DATE_MONTH_FORMAT).parse(endTime);
                months = getMonthDiff(startDate, endDate);
                days = new Long((long) months);
            } else {
                startDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(startTime);
                endDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(endTime);
                days = getTimeDiff(startDate, endDate);
            }
        } catch (ParseException e) {
            if (logger.isWarnEnabled()) {
                logger.warn(e.getMessage());
            }
            days = null;
        }
        return days;
    }

    /**
     * 返回两个时间的相差天数
     */
    public static Long getTimeDiff(Date startTime, Date endTime) {
        Long days = null;

        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        long l_s = c.getTimeInMillis();
        c.setTime(endTime);
        long l_e = c.getTimeInMillis();
        days = (l_e - l_s) / MILLIS_PER_DAY;
        return days;
    }

    /**
     * 返回两个时间的相差分钟数
     */
    public static Long getMinuteDiff(Date startTime, Date endTime) {
        Long minutes = null;

        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        long l_s = c.getTimeInMillis();
        c.setTime(endTime);
        long l_e = c.getTimeInMillis();
        minutes = (l_e - l_s) / MILLIS_PER_MINUTE;
        return minutes;
    }

    /**
     * 返回两个时间的相差秒数
     */
    public static Long getSecondDiff(Date startTime, Date endTime) {
        return (endTime.getTime() - startTime.getTime()) / MILLIS_PER_SECOND;
    }

    /**
     * 返回两个时间的相差月数
     */
    public static int getMonthDiff(Date startTime, Date endTime) {
        int months = 0;

        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(startTime);
        endCalendar.setTime(endTime);
        months = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        return months;
    }

    /**
     * 获取某个日期 的几个月后 或者几个月前 的月份最后一天
     *
     * @param date     某日期
     * @param addMonth +N月 -N月
     * @return 月份最后一天
     */
    public static Date getLastDayOfMonth(Date date, int addMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        //设置为指定日期
        c.setTime(date);
        //指定日期月份减去一
        c.add(Calendar.MONTH, addMonth);
        //指定日期月份减去一后的 最大天数
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        //获取最终的时间
        Date lastDateOfPrevMonth = c.getTime();
        System.out.println("当前月月最后一天：" + sdf.format(lastDateOfPrevMonth));
        return lastDateOfPrevMonth;
    }

    private static String pad2(int number) {
        if (number < 10) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    public static Long duration2milli(Duration duration) {
        if (cn.maidaotech.smartapi.common.utils.StringUtils.isNull(duration)) {
            return null;
        }
        long val = duration.getValue();
        long milli = 0L;
        TimeUnit unit = duration.getUnit();
        if (unit.getCalendarUnit() == 1) {
            milli = 31104000000L;
        }
        if (unit.getCalendarUnit() == 2) {
            milli = 2592000000L;
        }
        if (unit.getCalendarUnit() == 3) {
            milli = 604800000L;
        }
        if (unit.getCalendarUnit() == 5) {
            milli = 86400000L;
        }
        if (unit.getCalendarUnit() == 11) {
            milli = 3600000L;
        }
        if (unit.getCalendarUnit() == 12) {
            milli = 60000L;
        }
        if (unit.getCalendarUnit() == 13) {
            milli = 1000L;
        }
        return val * milli;
    }


}
