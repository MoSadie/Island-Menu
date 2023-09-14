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
    DeveloperOptions devOptions = new DeveloperOptions();

    static class DeveloperOptions {
        String apiUrl = "https://api.mcchampionship.com";
    }
}
