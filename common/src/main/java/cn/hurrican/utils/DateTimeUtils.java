package cn.hurrican.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/1/25
 * @Modified 9:46
 */
public class DateTimeUtils {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static Date addSpecifiedSecondToDate(Date date, int second){
        if(date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * @param dateString yyyy-MM-dd HH:mm:ss格式的字符串
     * @param second     相对参数dateString的秒偏移量
     * @return 目标日期 或 null(发生异常时返回null)
     */
    public static Date addSpecifiedSecondToDate(String dateString, int second) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);
        try {
            Date date = dateFormat.parse(dateString);
            return addSpecifiedSecondToDate(date, second);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 设当前时间戳对应的时间为 "2018-1-25 10:43:14" ,second = 16; return "2018-1-25 10:43:30"
     * @param startTimestamp 时间戳
     * @param seconds 秒数（可正可负）
     * @return Date 将当前时间戳加上或减去指定秒数的Date
     */
    public static Date addSpecifiedSecondToDate(long startTimestamp, int seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTimestamp);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
    
    public static String format(Date date) {
		return new SimpleDateFormat(PATTERN).format(date);
	}

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static long parseDateToTimestamp(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static double getTimeAddition(long currentTimestamp){
        long currentSeconds =  currentTimestamp / 1000;
        Long referenceValue = new Double(Math.pow(10D, ((double) ("" + currentTimestamp / 1000).length()))).longValue();
        return  (referenceValue - currentSeconds) / ((double)referenceValue);
    }


    /**
     * 转换String类型为Date类型
     *
     * @param value
     * @return
     * @throws java.text.ParseException
     */
    public static Date getSimpleDate(String value) throws ParseException {
        if(null == value || "".equals(value.trim())){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.parse(value);
    }


    /**
     * 获取只有日期的时间戳<br/>
     * 如：<br/>
     * &nbsp&nbsp 参数 timestamp 对应的时间为 2018-11-14 11:47:44 <br/>
     * &nbsp&nbsp 结果将返回 2018-11-14 00:00:00 对应的时间戳
     * @return
     */
    public static long getTimestampOnlyDate(long timestamp){
        Calendar calendar = getTimestampOnlyDateCalendar(timestamp);
        return calendar.getTimeInMillis();
    }

    private static Calendar getTimestampOnlyDateCalendar(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }


    public static long getTimestampOnlyDate(long timestamp, int fewDay){
        Calendar calendar = getTimestampOnlyDateCalendar(timestamp);
        calendar.add(Calendar.DAY_OF_MONTH, fewDay);
        return calendar.getTimeInMillis();
    }

}
