package com.mosadie.islandmenu.client.theme;

import com.mosadie.servermainmenu.api.Util;

public class WinterTheme extends NormalTheme {
    @Override
    public String getId() {
        return "winter";
    }

    @Override
    public boolean rollOdds() {
        return Util.rollOddsMonthDay(12, 25, 25, 10);
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
