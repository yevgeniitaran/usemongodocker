package com.yeta.mongo.utils;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static Date getNewDateShiftingDay(Date date, int numberOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, numberOfDays);
        return cal.getTime();
    }
}
