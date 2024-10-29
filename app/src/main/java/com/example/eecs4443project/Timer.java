package com.example.eecs4443project;

import android.content.Context;
import android.content.SharedPreferences;

public class Timer {
        private static final String PREF_NAME = "TimerPreferences";
        private static final String START_TIME = "startTime";
        private static final String IS_RUNNING = "isRunning";
        private static final String TIMER_DATA = "timerData";


        public static void setTimerRunning(Context context, boolean isRunning) {
            SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(IS_RUNNING, isRunning);
            editor.apply();
        }

        public static boolean isTimerRunning(Context context) {
            SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return prefs.getBoolean(IS_RUNNING, false);
        }

        public static void setStartTime(Context context, long startTime) {
            SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong(START_TIME, startTime);
            editor.apply();
        }

        public static long getStartTime(Context context) {
            SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return prefs.getLong(START_TIME, 0);
        }

    public static void saveTimerData(Context context, String data) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TIMER_DATA, data);
        editor.apply();
    }
    public static String getTimerData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TIMER_DATA, "00:00");
    }
}
