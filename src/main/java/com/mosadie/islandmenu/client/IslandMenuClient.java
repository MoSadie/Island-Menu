package com.mosadie.islandmenu.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.math.random.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class IslandMenuClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("island-menu");

    private final static String[] splashOptions = {
            "Set Sail Today!",
            "Enjoy your island vacation!",
            "Get the latest Island news at mccisland.net!",
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

    @Override
    public void onInitializeClient() {
        LOGGER.info("Init Island Menu Mod!");
    }
}
