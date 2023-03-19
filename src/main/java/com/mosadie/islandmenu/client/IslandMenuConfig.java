package com.mosadie.islandmenu.client;

import com.mosadie.islandmenu.client.IslandMenuClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = IslandMenuClient.MOD_ID)
public class IslandMenuConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    JoinButtonOptions joinButtonOptions = new JoinButtonOptions();

    @ConfigEntry.Gui.CollapsibleObject
    ThemeOptions themeOptions = new ThemeOptions();

    @ConfigEntry.Gui.CollapsibleObject
    SplashOptions splashOptions = new SplashOptions();

    static class JoinButtonOptions {
        boolean overrideJoinButton = false;
        String buttonTextOverride = "";
        String buttonServerNameOverride = "Join MCC Island!";
        String buttonServerAddressOverride = "play.mccisland.net";
    }

    static class ThemeOptions {
        @ConfigEntry.Gui.Tooltip()
        boolean overrideTheme = false;
        MenuTheme theme = MenuTheme.NORMAL;
    }

    static class SplashOptions {
        boolean overrideSplash = false;
        String overrideSplashText = "Set Sail Today!";
    }
}
