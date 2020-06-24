package com.example.chatapp.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class Utils {
    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
        return date;
    }
}
