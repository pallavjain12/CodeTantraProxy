package com.example.codetantraproxy.Helper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class ApiHelper {
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static String todaysEpochTIme() {
        String today = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            today = LocalDate.now().toString();
        }
        try {
            return  toEpochTime(df.parse(today));
        }
        catch (Exception e) {
            return "";
        }
    }

    public static String tomorrowsEpochTime() {
        String tomorrow = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            tomorrow = LocalDate.now().plusDays(1).toString();
        }
        try {
            return toEpochTime(df.parse(tomorrow));
        }
        catch (Exception e) {
            return "";
        }
    }

    public static String toEpochTime(Date date) {
        return String.valueOf(date.getTime());
    }
}
