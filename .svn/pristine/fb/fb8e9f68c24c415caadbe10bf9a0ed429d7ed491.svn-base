package com.example.wsd_client.util.CalendarViewUtil;

import java.util.Calendar;

import com.example.wsd_client.application.Myapplication;


public class DateUtils {
	/**
     * 根据年份，测算出月份最大天数
     * 
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
		month++;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12: 
		    return 31;
		case 4:
		case 6:
		case 9:
		case 11: 
		    return 30;
		case 2:
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
				return 29;
			}else{
				return 28;
			}
		default:
			return  -1;
		}
    }
    /**
     * 根据年数，月数，测算出这个月的第一天是周几
     * @param year
     * 		年数
     * @param month
     * 		月份
     * @return
     * 	周日		周一		周二		周三		周四		周五		周六
     */
    public static int getFirstDayWeek(int year, int month){
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(year, month, 1);
    	Myapplication.log("DateView", "DateView:First:" + calendar.getFirstDayOfWeek());
    	return calendar.get(Calendar.DAY_OF_WEEK);
    }
    
}
