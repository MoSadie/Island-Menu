package com.mosadie.islandmenu.client;

import java.util.Calendar;

public enum MenuTheme {
    NORMAL(-1, -1, "normal"),
    HALLOWEEN(Calendar.NOVEMBER, 9, "halloween"),
    WINTER(Calendar.DECEMBER, 25, "winter");

    private final int month;
    private final int day;
    private final String path;

    MenuTheme(int month, int day, String path) {
        this.month = month;
        this.day = day;
        this.path = path;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getPath() {
        return path;
    }
}
