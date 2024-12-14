package com.mosadie.islandmenu.client.theme;

import com.mosadie.islandmenu.client.IslandMenuClient;
import com.mosadie.servermainmenu.api.MenuTheme;
import com.mosadie.servermainmenu.api.Util;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class NormalTheme implements MenuTheme {
    @Override
    public String getId() {
        return "normal";
    }

    @Override
    public Identifier getPanorama() {
        return Identifier.of(IslandMenuClient.MOD_ID, "textures/gui/title/background/"+ getId() + "/panorama");
    }

    @Override
    public String getSplashText() {
        return IslandMenuClient.getSplashText();
    }

    @Override
    public Text getQuickJoinButtonText() {
        return Text.translatable("island-menu.menu.join");
    }

    @Override
    public void onQuickJoinClicked() {
        Util.joinServer("MCC Island", "play.mccisland.net");
    }

    @Override
    public boolean rollOdds() {
        return true;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean isSingleplayerVisible() {
        return false;
    }

    @Override
    public boolean isMultiplayerVisible() {
        return false;
    }

    @Override
    public boolean isQuickJoinVisible() {
        return true;
    }

    @Override
    public boolean isModsVisible() {
        return true;
    }
}
