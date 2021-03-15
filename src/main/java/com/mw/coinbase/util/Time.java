package com.mw.coinbase.util;

/**
 * @author LoomisM4
 */
public class Time {
    public static final long SECOND = 1000L;
    public static final long MINUTE = SECOND * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    public static final long WEEK = DAY * 7;
    public static final long MONTH = WEEK * 4;
    public static final long YEAR = MONTH * 12;

    public static long getTime(String interval) {
        switch (interval) {
            case "daily": return DAY;
            case "weekly": return WEEK;
            case "monthly": return MONTH;
            case "yearly": return YEAR;
            default: throw new IllegalArgumentException("Unexpected value: " + interval);
        }
    }
}
