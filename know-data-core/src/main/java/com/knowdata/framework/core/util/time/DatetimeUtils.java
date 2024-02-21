package com.knowdata.framework.core.util.time;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author johnnyzen
 * @version v1.0
 * @project know-data-parent
 * @create-date 2022/8/10 11:59
 * @description //TODO 待优化，国际化时区问题
 * @reference-doc
 *  [1] java 不同时区转换 - CSDN - https://blog.csdn.net/qq_41344974/article/details/125505290
 */
public class DatetimeUtils {
    /** 天级时间格式 **/
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd";
    /* 秒级时间格式 */
    public static final String SECOND_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 毫秒级时间字符串 millisecond time format **/
    public final static String MILLISECOND_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String calendarToString(Calendar calendar, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format); //such as: "yyyy-MM-dd"
        return sdf.format(calendar.getTime());
    }

    public static Calendar stringToCalendar(String dateStr,String format) throws ParseException {
        SimpleDateFormat sdf= new SimpleDateFormat(format); //such as:"yyyy-MM-dd"
        Date date =sdf.parse(dateStr);
        return dateToCalendar(date);
    }

    public static Date longToDate(long time){
        return new Date(time);
    }

    public static Calendar longToCalender(long time){
        return dateToCalendar( longToDate(time) );
    }

    public static Long calenderToLong(Calendar calendar){
        return calendarToTimestamp(calendar).getTime();
    }

    /**
     *
     * @param time
     *  eg:
     *      1665211423751L
     * @param format
     *  eg:
     *      "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     * @return
     *  eg:
     *      "2022-10-08T14:43:43.751Z"
     */
    public static String longToString(long time, String format){
        return calendarToString(longToCalender(time), format);
    }

    /**
     *
     * @param datetimeStr 当前应用的时间字符串
     *  eg:
     *      "2022-10-06 11:49:00"
     *      "2022-10-06 11:49"
     *      "2022-10-08T07:01:40.751Z"
     * @param format
     *  eg:
     *      "yyyy-MM-dd hh:mm:ss"
     *      "yyyy-MM-dd hh:mm"
     *      "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" | T代表可替换的任意字符, Z 代表时区
     * @param timeZone [选填参数] 允许为 null
     *  可选值有:
     *      "UTC" / "Asia/Shanghai" / "GMT" / "GMT+08:00" / ...
     * @return
     *  eg:
     *      1665028140000L
     *      1665028140000L
     *      1665212500751L [timeZone=UTC] 1665183700751L [timeZone=null]
     * @reference-doc
     *  [1] java 不同时区转换 - CSDN - https://blog.csdn.net/qq_41344974/article/details/125505290
     */
    public static long stringToLong(String datetimeStr, String format, String timeZone) {
        SimpleDateFormat formatSDF = new SimpleDateFormat(format);// such as "yyyyMMddhhmmss"
        //new SimpleDateFormat(format, Locale.getDefault());// such as "yyyyMMddhhmmss"
        if(!StringUtils.isEmpty(timeZone)){
            formatSDF.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        Date date = null;
        try {
            date = formatSDF.parse(datetimeStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static long stringToLong(String datetimeStr, String format) {
        return stringToLong(datetimeStr, format, null);
    }

    public static String dateToString(Date date, String format){
        SimpleDateFormat sdf= new SimpleDateFormat(format); //such as:"yyyy-MM-dd"
        return sdf.format(date);
    }

    public static Date stringToDate(String dateStr,String formatOfStr) throws ParseException {
        //dateStr such as :"2018-11-4"
        SimpleDateFormat sdf= new SimpleDateFormat(formatOfStr);//such as:"yyyy-MM-dd"
        return sdf.parse(dateStr);
    }

    public static Calendar dateToCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date calendarToDate(Calendar calendar){
        Date date = calendar.getTime();
        return date;
    }

    public static Timestamp stringToTimestamp(String dateStr){
        //dateStr such as:"2019-1-14 08:11:00"
        return Timestamp.valueOf(dateStr);
    }

    public static String timestampToString(Timestamp timestamp, String format){
        SimpleDateFormat sdf= new SimpleDateFormat(format);//such as:"yyyy-MM-dd"
        return sdf.format(timestamp);
    }

    public static Timestamp dateToTimestamp(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Timestamp.valueOf(df.format(date));
    }

    public static Calendar timestampToCalendar(Timestamp timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        return calendar;
    }
    public static Timestamp calendarToTimestamp(Calendar calendar){
        return dateToTimestamp(calendarToDate(calendar));
    }

    public static String millisecondToDateString(long ms){
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();
        if(day >= 0) {
            sb.append(day+"day");
        }
        if(hour >= 0) {
            sb.append(hour+"hour");
        }
        if(minute >= 0) {
            sb.append(minute+"minute");
        }
        if(second >= 0) {
            sb.append(second+"second");
        }
        return sb.toString();
    }
}
