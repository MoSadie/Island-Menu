package com.mosadie.islandmenu.client;

import com.mosadie.islandmenu.client.IslandMenuClient;
import com.mosadie.islandmenu.mccapi.Teams;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = IslandMenuClient.MOD_ID)
public class IslandMenuConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    Teams supportingTeam = Teams.NONE;
    @ConfigEntry.Gui.CollapsibleObject
    JoinButtonOptions joinButtonOptions = new JoinButtonOptions();

    @ConfigEntry.Gui.CollapsibleObject
    ThemeOptions themeOptions = new ThemeOptions();

    @ConfigEntry.Gui.CollapsibleObject
    SplashOptions splashOptions = new SplashOptions();

    @ConfigEntry.Gui.CollapsibleObject
    DeveloperOptions devOptions = new DeveloperOptions();

    static class JoinButtonOptions {
        boolean overrideJoinButton = false;
        String buttonTextOverride = "Join MCC Island!";
        @ConfigEntry.Gui.Tooltip
        String buttonServerNameOverride = "MCC Island!";
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

    static class DeveloperOptions {
        String apiUrl = "https://api.mcchampionship.com";
    }
}
