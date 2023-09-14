package com.mosadie.islandmenu.client.theme;

import com.mosadie.servermainmenu.api.Util;

import java.util.Calendar;

public class WinterTheme extends NormalTheme {
    @Override
    public String getId() {
        return "winter";
    }

    @Override
    public boolean rollOdds() {
        return Util.rollOddsMonthDay(12, 25, 25, 5);
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
