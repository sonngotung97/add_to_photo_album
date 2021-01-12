package com.tinhvan.photo_album.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTool {
    public final static String PATTERN_FULL_1 = "hh:mm a, dd/MM/yyyy";
    public final static String PATTERN_FULL_2 = "hh:mm:ss_a_dd-MM-yyyy";
    public final static String PATTERN_FULL_3 = "HH-mm-ss_dd-MM-yyyy";

    public final static String PATTERN_DATE = "dd/MM/yyyy";

    public final static String PATTERN_TIME = "hh:mm a";
    public DateTool() {
    }

    // 25200000 ms tương đương với 7h để chuyển UTC +0 sang múi UTC +7
    public static long getDayOfMonth(long seconds, int UTC) {
        return Long.parseLong(new SimpleDateFormat("d")
                .format(new Date(seconds * 1000 + UTC * 3600 * 1000)));
    }

    public static long getMonth(long seconds, int UTC) {
        return Long.parseLong(new SimpleDateFormat("M")
                .format(new Date(seconds * 1000 + UTC * 3600 * 1000)));
    }

    public static long getYear(long seconds, int UTC) {
        return Long.parseLong(new SimpleDateFormat("yyyy")
                .format(new Date(seconds * 1000 + UTC * 3600 * 1000)));
    }

    public static String getTimestamp(String dateString) throws ParseException {
        // System.out.println("dateString" + dateString);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss_dd/MM/yyyy", new Locale("vi", "VN"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        Date date = dateFormat.parse(dateString);
        // Đã fix được
        return String.valueOf(date.getTime());
    }

    public static String getTimestamp(String dateString, String format) throws ParseException {
        // System.out.println("dateString" + dateString);
        DateFormat dateFormat = new SimpleDateFormat(format, new Locale("vi", "VN"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        Date date = dateFormat.parse(dateString);
        // Đã fix được
        return String.valueOf(date.getTime());
    }

    public static String format(long ms, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern, new Locale("vi", "VN"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        return dateFormat.format(new Date(ms));
    }
}
