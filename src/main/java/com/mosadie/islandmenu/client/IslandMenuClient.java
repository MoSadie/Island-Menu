package com.mosadie.islandmenu.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.random.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class IslandMenuClient implements ClientModInitializer {

    public static final String MOD_ID = "island-menu";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static MenuTheme selectTheme() {
        LOGGER.info("Selecting Menu Theme!");

        if (config != null && config.themeOptions.overrideTheme) {
            LOGGER.info("Theme selected via override: " + config.themeOptions.theme.name());
            return config.themeOptions.theme;
        }

        Calendar calendar = Calendar.getInstance();

        MenuTheme selectedTheme = MenuTheme.NORMAL;

        Random random = Random.create();


        for(MenuTheme theme : MenuTheme.values()) {
            if (calendar.get(Calendar.MONTH) == theme.getMonth() && calendar.get(Calendar.DAY_OF_MONTH) <= theme.getDay()) {
                LOGGER.info("Possible Theme Found: " + theme.name());

                int roll = random.nextBetween(1, theme.getDay());

                // If the roll is less than the day of the month, set as the theme to select.
                // The goal is as the dates get closer, the theme appears more often.
                if (roll <= calendar.get(Calendar.DAY_OF_MONTH)) {
                    LOGGER.info("Successfully rolled " + roll + "/" + theme.getDay() + " (compared to " + calendar.get(Calendar.DAY_OF_MONTH) + ") for theme");
                    selectedTheme = theme;
                } else {
                    LOGGER.info("Failed rolled " + roll + "/" + theme.getDay() + " (compared to  " + calendar.get(Calendar.DAY_OF_MONTH) + ") for theme");
                }
            }
        }

        LOGGER.info("Selected Menu Theme: " + selectedTheme.name());
        return selectedTheme;
    }

    private static MenuTheme menuTheme = null;

    public static MenuTheme getTheme() {
        if (menuTheme != null) {
            return menuTheme;
        }

        return MenuTheme.NORMAL;
    }

    private final static String[] splashOptions = {
            "Set Sail Today!",
            "Enjoy your island vacation!",
            "Get the latest news at mccisland.net!",
            "See live MCC stats at mcc.live!",
            "Support the Red Rabbits!",
            "Support the Orange Ocelots!",
            "Support the Yellow Yaks!",
            "Support the Lime Llamas!",
            "Support the Green Geckos!",
            "Support the Cyan Coyotes!",
            "Support the Aqua Axolotls!",
            "Support the Blue Bats!",
            "Support the Purple Pandas!",
            "Support the Pink Parrots!"
    };
    public static String getSplashText() {
        if (config != null && config.splashOptions.overrideSplash) {
            return config.splashOptions.overrideSplashText;
        }
        return splashOptions[Random.create().nextBetweenExclusive(0, splashOptions.length)];
    }

    private static IslandMenuConfig config;

    public static Text getButtonText() {
        if (config != null && config.joinButtonOptions.overrideJoinButton) {
            return Text.of(config.joinButtonOptions.buttonTextOverride);
        }

        return Text.translatable("island-menu.menu.join");
    }

    public static String getButtonServerName() {
        if (config != null && config.joinButtonOptions.overrideJoinButton) {
            return config.joinButtonOptions.buttonServerNameOverride;
        }

        return "MCC Island";
    }

    public static String getButtonServerAddress() {
        if (config != null && config.joinButtonOptions.overrideJoinButton) {
            return config.joinButtonOptions.buttonServerAddressOverride;
        }

        return "play.mccisland.net";
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Island Menu...");

        AutoConfig.register(IslandMenuConfig.class, GsonConfigSerializer::new);

        AutoConfig.getConfigHolder(IslandMenuConfig.class).registerLoadListener(IslandMenuClient::onConfigLoad);

        config = AutoConfig.getConfigHolder(IslandMenuConfig.class).getConfig();

        menuTheme = selectTheme();

        LOGGER.info("Island Menu Initialized!");
    }

    private static ActionResult onConfigLoad(ConfigHolder<IslandMenuConfig> islandMenuConfigConfigHolder, IslandMenuConfig islandMenuConfig) {
        LOGGER.info("Loading config!");

        config = islandMenuConfig;

        menuTheme = selectTheme();

        return ActionResult.PASS;
    }
}
