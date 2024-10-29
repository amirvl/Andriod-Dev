package com.example.eecs4443project;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Counter {
    private static final String PREFS_NAME = "WrongAnswerCounts";
    private static final String MYDEBUG = "MYDEBUG";

    public static void incrementWrongAnswerCount(Context context, String questionId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int currentCount = prefs.getInt(questionId, 0) + 1;
        prefs.edit().putInt(questionId, currentCount).apply();
    }

    public static int getWrongAnswerCount(Context context, String questionId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int count = prefs.getInt(questionId, 0);
        return count;
    }

    public static void resetWrongAnswerCount(Context context, String questionId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(questionId).apply();
    }
}
