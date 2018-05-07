package com.yeta.mongo.utils;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static Date getNewDateWithPlusOneDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }
}
