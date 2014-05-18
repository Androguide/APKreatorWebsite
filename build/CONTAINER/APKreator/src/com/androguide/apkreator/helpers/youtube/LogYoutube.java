package com.androguide.apkreator.helpers.youtube;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Message;

public class LogYoutube {

    private static final boolean live = false;
    private static final String TAG = "YoutubeLibrary";

    public static void d(String msg) {
        d(msg, null);
    }

    public static void d(String msg, Throwable e) {
        android.util.Log.d(TAG, Thread.currentThread().getName() + "| " + msg, e);
    }

    public static void i(String msg) {
        i(msg, null);
    }

    public static void i(String msg, Throwable e) {
        android.util.Log.i(TAG, Thread.currentThread().getName() + "| " + msg, e);
    }

    public static void e(String msg) {
        e(msg, null);
    }

    public static void e(String msg, Throwable e) {
        android.util.Log.e(TAG, Thread.currentThread().getName() + "| " + msg, e);
    }

    public static String identifyMessage(Resources res, Message msg) {
        try {
            return res.getResourceEntryName(msg.what);
        } catch (NotFoundException ignore) {
            return "not found";
        }
    }
}