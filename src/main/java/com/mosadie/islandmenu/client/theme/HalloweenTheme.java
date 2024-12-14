package com.mosadie.islandmenu.client.theme;

import com.mosadie.servermainmenu.api.Util;

public class HalloweenTheme extends NormalTheme {

    @Override
    public String getId() {
        return "halloween";
    }

    @Override
    public boolean rollOdds() {
        return Util.rollOddsMonthDay(10, 31, 31, 0);
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
