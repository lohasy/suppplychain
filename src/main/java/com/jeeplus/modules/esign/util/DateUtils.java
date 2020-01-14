package com.jeeplus.modules.esign.util;


import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 自定义时间处理工具类
 */
public class DateUtils {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YEE_TS = "yyyyMMddHHmmssSSS";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";

    private static SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
    private static SimpleDateFormat dfNo = new SimpleDateFormat(YYYYMMDDHHMMSS);
    private static SimpleDateFormat punchYmd = new SimpleDateFormat(YYYY_MM_DD);
    private static SimpleDateFormat yeeTs = new SimpleDateFormat(YEE_TS);
    private static SimpleDateFormat applyIdSuf = new SimpleDateFormat(YYYYMMDDHHMMSS);
    private static SimpleDateFormat yyyymmdd = new SimpleDateFormat(YYYYMMDD);
    private static SimpleDateFormat yyyymmdd_hh_mm_ss = new SimpleDateFormat(YYYYMMDD);

    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = new LinkedList<>();
        lDate.add(beginDate);//把开始时间加入
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        while (true) {
            //根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);//把结束时间加入
        return lDate;
    }


    public static Date stringToDate(String timeStr, String format) {
        Date targetDate = null;
        if (StringUtils.isNotBlank(timeStr)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                targetDate = sdf.parse(timeStr);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return targetDate;
    }

    // date 转 String
    public static String dateToString(Date date) {
        if (null == date) {
            return "date is null";
        }
        return sdf.format(date);
    }

    public static String dateToStringYmd(Date date) {
        if (null == date) {
            return "date is null";
        }
        return punchYmd.format(date);
    }

    public static String dateToYMDStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        return sdf.format(date);
    }


    //减一天
    public static Date minDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    //当前时间减去 一小时
    public static String dateToStringBeforeHour(Date date) {
        if (null == date) {
            return "date is null";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        return sdf.format(date);
    }

    public static Date addMonth(Date originalDate, int addMonths) {
        return add(originalDate, Calendar.MONTH, addMonths);
    }

    // 添加N天
    public static Date addDays(Date originalDate, int addDays) {
        return add(originalDate, Calendar.DAY_OF_MONTH, addDays);
    }

    /**
     * 添加N分钟
     */
    public static Date addMinutes(Date originalDate, int addMinutes) {
        return add(originalDate, Calendar.MINUTE, addMinutes);
    }

    /**
     * 添加N秒
     */
    public static Date addSecond(Date originalDate, int addSecond) {
        return add(originalDate, Calendar.SECOND, addSecond);
    }

    /**
     * 根据指定的日期，类型，增加或减少数量
     */
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public static String getSourceSystem(Date date) {
        return yyyymmdd.format(date);
    }

    /**
     * 订单编号所需
     *
     * @param
     */
    public static String getYMD() {
        String orderNo = dfNo.format(new Date());
        return orderNo;
    }

    /**
     * 计算指定时间与当前相差年月
     *
     * @param startCal
     * @return
     */
    public static String getTaktTime(String startCal) {
        String result = "";
        try {
            Date startDate = sdf.parse(startCal);
            Date endDate = new Date();
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);

            if (endCalendar.compareTo(startCalendar) < 0) {
                return result;
            }
            int day = endCalendar.get(Calendar.DAY_OF_MONTH) - startCalendar.get(Calendar.DAY_OF_MONTH);
            int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
            int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
            if (day < 0) {
                month--;
            }
            if (month < 0) {
                month += 12;
                year--;
            }
            result = year + "年" + month + "月";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //只取年月日
    public static Date getOnlyYmd() {
        String sDate = punchYmd.format(new Date());
        try {
            Date date = punchYmd.parse(sDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getYmdAddDay(Date date, int day) {
        String sDate = punchYmd.format(addDays(date, day));
        try {
            date = punchYmd.parse(sDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getYmds(Date date) {
        String format = punchYmd.format(date);
        try {
            return punchYmd.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据提供的年月日获取该月份的第一天
     *
     * @param year
     * @param monthOfYear
     * @return
     */
    public static Date getSupportBeginDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return firstDate;
    }

    /**
     * 根据提供的年月获取该月份的最后一天
     *
     * @param year
     * @param monthOfYear
     * @return
     */
    public static Date getSupportEndDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return lastDate;
    }

    /**
     * 得到某一天的最开始时刻
     */
    public static Date getStartOfDay(Date nowDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 得到某一天的最后时刻
     */
    public static Date getEndOfDay(Date nowDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDay);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static boolean timeRange(int startHour, int startMinute, int endHour, int endMintue) {
        Date date = new Date();
        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.HOUR_OF_DAY, startHour);
        startCal.set(Calendar.MINUTE, startMinute);
        startCal.set(Calendar.SECOND, 0);
        Date startTime = startCal.getTime();

        Calendar entCal = Calendar.getInstance();
        entCal.set(Calendar.HOUR_OF_DAY, endHour);
        entCal.set(Calendar.MINUTE, endMintue);
        entCal.set(Calendar.SECOND, 0);
        Date endTime = entCal.getTime();
        if (date.after(startTime) && date.before(endTime)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean timeBA(int startHour, int startMinute) {
        Date date = new Date();
        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.HOUR_OF_DAY, startHour);
        startCal.set(Calendar.MINUTE, startMinute);
        startCal.set(Calendar.SECOND, 0);
        Date startTime = startCal.getTime();
        if (date.after(startTime)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getYeeTs() {
        String strTs = yeeTs.format(new Date());
        return strTs;
    }

    public static String getYeeApplyIdSuf() {
        return applyIdSuf.format(new Date());
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param time
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date time, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(time);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else if (time.compareTo(beginTime) == 0 || time.compareTo(endTime) == 0) {
            return true;
        } else {
            return false;
        }
    }

    //time1 当前时间  time2  过期时间
    public static Long intervalTime(Date time1, Date time2) {
        long days = ChronoUnit.DAYS.between(dateToLocalDateTime(time1), dateToLocalDateTime(time2));
        return days;
    }

    //Date  转LocalDateTime
    private static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * @Title: getDayList
     * @Description: 获取两个日期之间所有日期
     * @param: [startTime, endTime]
     * @return: java.util.List<java.lang.String>
     * @throws:
     */
    public static List<String> getDayList(String startTime, String endTime) {
        // 返回的日期集合
        List<String> days = new ArrayList<String>();
        try {
            Date start = punchYmd.parse(startTime);
            Date end = punchYmd.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(punchYmd.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    // 2019-08-02 02:02:02  转 yyyy年MM月dd日 HH:mm:ss 注：传进格式必须是"yyyy-MM-dd HH:mm:ss";
    public static String getYear(String dateStr) {
        try {
            Date date = sdf.parse(dateStr);
            return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 毫秒转 yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String timeStamp2Date(String time) {
        Long timeLong = Long.parseLong(time);
        Date date;
        try {
            date = sdf.parse(sdf.format(timeLong));
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.getYear("2019-08-02 02:02:02"));
    }

}
