/*
* Copyright (c) 2015-2016 Jh-health, Inc. All rights reserved.
* Jh-health PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*
*/
package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private final static SimpleDateFormat sdfHH = new SimpleDateFormat("HH");

    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfDD = new SimpleDateFormat("dd");

    private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

    private final static SimpleDateFormat sdDay = new SimpleDateFormat("yyyy-MM");

    private final static SimpleDateFormat mdDay = new SimpleDateFormat("MM-dd");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final static SimpleDateFormat newSdfDay = new SimpleDateFormat("yy/MM/dd");

    private final static SimpleDateFormat sdfDayTime = new SimpleDateFormat("MM-dd HH:mm");

    private final static SimpleDateFormat sdfHourTime = new SimpleDateFormat("HH:mm");

    private final static SimpleDateFormat sdfMonTime = new SimpleDateFormat("yyyy年MM月dd日");

    private final static SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyyMMddHHmmss");

    private final static SimpleDateFormat sdfCommonDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final static SimpleDateFormat sdfYearTime = new SimpleDateFormat("yyyy年");

    private final static SimpleDateFormat sdfMontTime = new SimpleDateFormat("yyyy年MM月");

    private final static SimpleDateFormat sdfMdHm = new SimpleDateFormat("MM-dd HH:mm");

    private final static SimpleDateFormat sdfMd = new SimpleDateFormat("MM-dd");

    private final static SimpleDateFormat sdfMdChines = new SimpleDateFormat("MM月dd日");

    private final static SimpleDateFormat sdfMdHmChines = new SimpleDateFormat("MM月dd日 HH:mm");


    public static String getDayByDayOfMonth(int dayOfMonth) {
        try {
            Calendar canlendar = Calendar.getInstance();
            canlendar.setTime(new Date());
            int curDayOfMonth = canlendar.get(Calendar.DAY_OF_MONTH);
            if (curDayOfMonth > dayOfMonth) {
                canlendar.add(Calendar.MONTH, 1);
            }
            canlendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            return getDateStr(canlendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getHour(String dateTimestr) {
        int hour = 0;
        try {
            Calendar canlendar = Calendar.getInstance();
            canlendar.setTime(geCommonDateTime(dateTimestr));
            return canlendar.get(Calendar.HOUR_OF_DAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hour;
    }


    public static Integer getYear(Date date) {
        int year = 0;
        try {
            return Integer.valueOf(sdfYear.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return year;
    }

    public static Integer getTodayDayNum() {
        int year = 0;
        try {
            return Integer.valueOf(sdfDD.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return year;
    }

    public static String getDateStr(Date date) {
        try {
            return sdfDay.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getYearMonthStr(Date date) {
        try {
            return sdDay.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取日期指定格式的字符串
     *
     * @param simpleDateFormat
     * @param date
     * @return
     */
    public static String getDateStr(SimpleDateFormat simpleDateFormat, Date date) {
        String dateStr = null;
        if (date != null) {
            dateStr = simpleDateFormat.format(date);
        }

        return dateStr;

    }

    public static String getNewDateStr(String datestr) {
        try {
            return newSdfDay.format(fomatDate(datestr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMdDayStr(String datestr) {
        try {
            return mdDay.format(fomatDate(datestr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMdDayStr(Date date) {
        try {
            return mdDay.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDateSTr(long longTime) {
        return getDateStr(new Date(longTime));
    }

    /**
     * 格式化日期 yyyy-MM-dd
     *
     * @return
     */
    public static Date fomatDate(String dateStr) {
        try {
            return sdfDay.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 格式化日期 yyyy-MM-dd
     *
     * @return
     */
    public static String getFomatDateStr(String dateStr) {
        Date date = fomatDate(dateStr);
        if (date != null) {
            return getDateStr(date);
        }
        return "";
    }


    public static String getFomatMdHm(Date time) {
        try {
            return sdfMdHm.format(time);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFomatMd(Date time) {
        try {
            return sdfMd.format(time);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTimeStr(Date time) {
        if (time == null) {
            return "";
        }
        try {
            return sdfTime.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDateTimeStr(Date time) {
        if (time == null) {
            return "";
        }
        try {
            return sdfDateTime.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTimeStr(long longtime) {
        Date date = new Date(longtime);
        return getTimeStr(date);
    }


    /**
     * 格式化时间 yyyy-MM-dd HH:mm
     *
     * @return
     */
    public static Date fomatTime(String timeStr) {
        try {
            return sdfTime.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化日期  yyyy-MM-dd HH:mm
     *
     * @return
     */
    public static String getFomatTimeStr(String timeStr) {
        Date dateTime = fomatTime(timeStr);
        if (dateTime != null) {
            return getTimeStr(dateTime);
        }
        return "";
    }

    public static String getDayTimeStr(Date time) {
        try {
            return sdfDayTime.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getFomatDayTimeStr(String timeStr) {
        Date dateTime = fomatTime(timeStr);
        if (dateTime != null) {
            return getDayTimeStr(dateTime);
        }
        return "";
    }


    public static String getHourTimeStr(Date time) {
        try {
            return sdfHourTime.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getFomatHourTimeStr(String timeStr) {
        Date dateTime = fomatTime(timeStr);
        if (dateTime != null) {
            return getHourTimeStr(dateTime);
        }
        return "";
    }


    /**
     * 格式化时间 HH:mm
     *
     * @return
     */
    public static Date fomatHourTime(String timeStr) {
        try {
            return sdfHourTime.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMonTimeStr(Date time) {
        try {
            return sdfMonTime.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getNowDateStr() {
        return getDateStr(new Date());
    }

    public static String getYearMonthStr() {
        return getYearMonthStr(new Date());
    }


    public static String getAfterSevenDateStr() {
        return DateUtil.getDateAddDateStr(getNowDateStr(), 7);
    }

    public static Date getNowDate() {
        return new Date();
    }

    public static String getThisMonTimeStr() {
        return getMonTimeStr(new Date());
    }

    public static String getNowDateTimeStr() {
        return sdfCommonDateTime.format(new Date());
    }

    public static String geCommonDateTimeStr(Date date) {
        return sdfCommonDateTime.format(date);
    }

    public static Date geCommonDateTime(String date) {
        try {
            return sdfCommonDateTime.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String geCommonDateTimeStr(String dateStr) {
        Date newDate = geCommonDateTime(dateStr);
        if (newDate != null) {
            return geCommonDateTimeStr(newDate);
        }
        return null;

    }

    private static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    //精确到天
    public static String getStartReportingDateStr(int dateNum) {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, dateNum);
        Date startDate = ca.getTime();
        return sdfDay.format(startDate);
    }

    //精确到天
    public static String getEndReportingDateStr() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, 1);
        return sdfDay.format(ca.getTime());
    }

    //精确到天
    public static String getNextDateStr(String dateStr) {
        Date date = fomatDate(dateStr);
        Calendar ca = Calendar.getInstance();
        if (date != null) {
            ca.setTime(date);
        }
        ca.add(Calendar.DATE, 1);
        return sdfDay.format(ca.getTime());
    }

    /**
     * 根据当前日期获取开始业务日期
     */
    public static String getStartReportingCuttime(int dateNum) {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, dateNum);
        return sdfDay.format(ca.getTime()) + " 06:00:00";
    }

    /**
     * 根据当前日期获取结束业务日期
     */
    public static String getEndReportingCuttime() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, 1);
        return sdfDay.format(ca.getTime()) + " 06:00:00";
    }


    public static Date getAfterMiniteDateTime(Date date, int minute) {
        try {
            Calendar canlendar = Calendar.getInstance();
            canlendar.setTime(date);
            canlendar.add(Calendar.MINUTE, minute);
            return canlendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getAfterMiniteDateTimeStr(String datestr, int minute) {
        try {
            Date date = fomatTime(datestr);
            Calendar canlendar = Calendar.getInstance();
            canlendar.setTime(date);
            canlendar.add(Calendar.MINUTE, minute);
            return geCommonDateTimeStr(canlendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geCommonDateTimeStr(new Date());
    }

    public static String getDateAddDateStr(String datestr, int daysInt) {
        Date date = fomatDate(datestr);
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(date);
        canlendar.add(Calendar.DAY_OF_MONTH, daysInt);
        return getDateStr(canlendar.getTime());
    }

    public static Date getDateAddDate(Date date, int daysInt) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(date);
        canlendar.add(Calendar.DAY_OF_MONTH, daysInt);
        return canlendar.getTime();
    }


    public static String getWeekDayStr(int week) {
        switch (week) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
        }

        return "";
    }

    public static String getWeekDayStr(String dateStr) {
        Date date = fomatDate(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return getWeekDayStr(week);
    }

    public static int getWeekDayInt(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return week;
    }

    public static Integer getWeek(String dateStr) {
        Date date = fomatDate(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return week;
    }

    public static String getNextTimeStr(String dateStr, String startTime, String endTime, int maxNum, int nowNum) {
        StringBuffer startDateTime = new StringBuffer();
        startDateTime.append(dateStr).append(" ").append(startTime);
        if (nowNum == 0) {
            return startDateTime.toString();
        }
        Date startDate = fomatTime(startDateTime.toString());
        StringBuffer endDateTime = new StringBuffer();
        endDateTime.append(dateStr).append(" ").append(endTime);
        Date endDate = fomatTime(endDateTime.toString());
        long longTime = endDate.getTime() - startDate.getTime();
        long newLongTime = (long) (startDate.getTime() + 1.0 * nowNum / maxNum * longTime);
        return getTimeStr(newLongTime);
    }

    public static int getBetWeentTimeMintor(String dateStr, String startTime, String endTime, int maxNum) {
        StringBuffer startDateTime = new StringBuffer();
        startDateTime.append(dateStr).append(" ").append(startTime);
        Date startDate = fomatTime(startDateTime.toString());
        StringBuffer endDateTime = new StringBuffer();
        endDateTime.append(dateStr).append(" ").append(endTime);
        Date endDate = fomatTime(endDateTime.toString());
        long longTime = endDate.getTime() - startDate.getTime();
        int result = (int) (longTime / maxNum / 1000 / 60);
        return result;
    }

    public static boolean comperDateTimeStr(String dateTimeStr1, String dateTimeStr2) {
        Date date1 = fomatTime(dateTimeStr1);
        Date date2 = fomatTime(dateTimeStr2);
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.getTime() >= date2.getTime();
    }

    public static boolean comperDateStr(String dateStr1, String dateStr2) {
        Date date1 = fomatDate(dateStr1);
        Date date2 = fomatDate(dateStr2);
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.getTime() >= date2.getTime();
    }

    public static int getThisYear() {
        Calendar c1 = Calendar.getInstance();
        return c1.get(Calendar.YEAR);
    }

    public static Long getDayBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return cal.getTimeInMillis();
    }

    public static String getDayBeginStr() {
        return getTimeStr(getDayBegin());
    }


    /**
     * 返回两个日期相差几天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDifferDay(Date date1, Date date2) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(date1);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(date2);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    /**
     * 返回两个日期相差几分钟
     *
     * @param dateTimeStr1
     * @param dateTimeStr2
     * @return
     */
    public static int getDifferTimes(String dateTimeStr1, String dateTimeStr2) {
        Date startDate = fomatTime(dateTimeStr1);
        Date startDate2 = fomatTime(dateTimeStr2);
        return (int) ((startDate2.getTime() - startDate.getTime()) / 1000 / 60);
    }

    /**
     * 判断一年时间内两个时间是否相差整月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isFullMonth(String date1, String date2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        StringBuffer months = new StringBuffer(date1);
        for (int i = 1; i <= 12; i++) {
            c.setTime(DateUtil.fomatDate(date1));
            c.add(Calendar.MONTH, i);
            String validityDate = df.format(c.getTime());
            months.append("," + validityDate);
        }
        if (months.indexOf(date2) > -1) {
            return true;
        } else {
            return false;
        }
    }

    public static Long getTimestamp(String dateTimeStr) {
        Date date = fomatTime(dateTimeStr);
        if (date != null) {
            return date.getTime();
        }
        return 0L;
    }

    /**
     * 将日期转换为系统保存的格式字符串,例如：20180510185408
     *
     * @param date
     * @return
     */
    public static String getSysDateTimeStr(Date date) {
        if (date == null) {
            date = new Date();
        }

        return sdfCommonDateTime.format(date);
    }


    /**
     * 将日期转换为系统保存的格式字符串,例如：20180510
     *
     * @param date
     * @return
     */
    public static String getSysDateStr(Date date) {
        if (date == null) {
            date = new Date();
        }

        return sdfDay.format(date);
    }

    /**
     * 获取指定日期，自1970年1月1日 0点0分0秒以来的秒数
     *
     * @param date
     * @return
     */
    public static String getSecondsFromDate(Date date) {
        if (date == null) {
            date = new Date();
        }

        long time = date.getTime();
        String timeStr = time + "";

        int returnLength = 10;
        int timeLength = timeStr.length();
        if (timeLength > returnLength) {
            timeStr = timeStr.substring(0, returnLength);
        } else {
            try {
                timeStr = timeStr + String.format("%0" + (returnLength - timeLength) + "d", 0);
            } catch (Exception e) {
            }
        }

        return timeStr;
    }


    public static String getNowMdChines() {
        try {
            return sdfMdChines.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getNowsdfMdHmChines() {
        try {
            return sdfMdHmChines.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getHms(long timeStamp) {
        long hours = (timeStamp % (60 * 60 * 24)) / (60 * 60);
        long minutes = (timeStamp % (60 * 60)) / 60;
        long seconds = timeStamp % 60;
        String hms = hours + "小时" + minutes + "分" + seconds + "秒";
        return hms;

    }

    /**
     * 日期减几天
     * @param date
     * @return 如 2030-11-11
     */
    public static String subDate(Date date, int dayNum){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_YEAR,-dayNum);
        Date dt=rightNow.getTime();
        return getDateStr(dt);
    }

    /**
     * 日期加几天
     * @param date
     * @return 如 2030-11-11
     */
    public String addDate(Date date, int dayNum){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_YEAR,dayNum);
        Date dt=rightNow.getTime();
        return getDateStr(dt);
    }

    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }

    public static void main(String[] args) {
//        Date date = getDateAddDate(new Date(), 6);
//        System.out.println(getSysDateStr(date));

        System.out.println(getYearMonthStr());
    }
}
