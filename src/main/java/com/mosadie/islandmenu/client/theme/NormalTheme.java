package com.mosadie.islandmenu.client.theme;

import com.mosadie.islandmenu.client.IslandMenuClient;
import com.mosadie.servermainmenu.api.MenuTheme;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class NormalTheme implements MenuTheme {
    @Override
    public String getId() {
        return "normal";
    }

    @Override
    public Identifier getPanorama() {
        return new Identifier(IslandMenuClient.MOD_ID, "textures/gui/title/background/"+ getId() + "/panorama");
    }

    @Override
    public String getSplashText() {
        return IslandMenuClient.getSplashText();
    }

    @Override
    public Text getJoinServerButtonText() {
        return Text.translatable("island-menu.menu.join");
    }

    @Override
    public ServerInfo getServerInfo() {
        ServerInfo info = new ServerInfo("MCC Island", "play.mccisland.net", false);
        return info;
    }

    @Override
    public boolean rollOdds() {
        return true;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
