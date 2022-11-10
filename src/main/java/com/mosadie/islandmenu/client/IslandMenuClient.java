package com.mosadie.islandmenu.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.math.random.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Calendar;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class IslandMenuClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(getModID());

    private static MenuTheme selectTheme() {
        LOGGER.info("Selecting Menu Theme!");

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

    public static final MenuTheme menuTheme = selectTheme();

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
    public static String getRandomSplashText() {
        return splashOptions[Random.create().nextBetweenExclusive(0, splashOptions.length)];
    }

    public static String getModID() {
        return "island-menu";
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Island Menu Initialized!");
    }
}
