package com.fiserv.preproposal.api.infrastrucutre.aid.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    private DateUtil() {

    }

    public static LocalDate toDate(String dateString) {
        if (!StringUtils.hasText(dateString))
            return null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);

        return LocalDate.parse(dateString, dtf);
    }

    public static LocalDate toDate(String dateString, String pattern) {
        if (!StringUtils.hasText(dateString))
            return null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);

        return LocalDate.parse(dateString, dtf);
    }

    public static LocalDateTime toDateTime(String dateTimeString) {
        if (!StringUtils.hasText(dateTimeString))
            return null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

        return LocalDateTime.parse(dateTimeString, dtf);
    }

    public static LocalTime toTime(String timeString, String pattern) {
        if (!StringUtils.hasText(timeString))
            return null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);

        return LocalTime.parse(timeString, dtf);
    }

    public static String toString(LocalDate date) {
        if (date == null)
            return null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return date.format(dtf);
    }

    public static String toString(LocalDate date, String pattern) {
        if (date == null)
            return null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return date.format(dtf);
    }

    public static String toString(LocalDateTime dateTime) {
        if (dateTime == null)
            return null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        return dateTime.format(dtf);
    }

    public static String toString(LocalDateTime dateTime, String pattern) {
        if (dateTime == null)
            return null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(dtf);
    }

    public static String toString(LocalTime time) {
        if (time == null)
            return null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TIME_PATTERN);
        return time.format(dtf);
    }

    public static String toString(LocalTime time, String pattern) {
        if (time == null)
            return null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return time.format(dtf);
    }
    
    public static String dateTimeNowToString (String pattern, ZoneId zone) {
    	if(!StringUtils.hasText(pattern) || null == zone) {
    		return null;
    	}
    	return toString(LocalDateTime.now(zone), pattern);
    }
}
