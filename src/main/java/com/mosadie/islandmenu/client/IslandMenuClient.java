package com.mosadie.islandmenu.client;

import com.mosadie.islandmenu.client.theme.HalloweenTheme;
import com.mosadie.islandmenu.client.theme.NormalTheme;
import com.mosadie.islandmenu.client.theme.WinterTheme;
import com.mosadie.islandmenu.mccapi.EventInfo;
import com.mosadie.islandmenu.mccapi.MCCApi;
import com.mosadie.islandmenu.mccapi.ParticipantsInfo;
import com.mosadie.islandmenu.mccapi.Teams;
import com.mosadie.servermainmenu.client.ServerMainMenuLibClient;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class IslandMenuClient implements ClientModInitializer {

    public static final String MOD_ID = "island-menu";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static MCCApi mccApi;

    private static NormalTheme normalTheme = new NormalTheme();
    private static HalloweenTheme halloweenTheme = new HalloweenTheme();

    private static WinterTheme winterTheme = new WinterTheme();

    private final static String MCC_DATE_SPLASH = "island-menu.mccdatesplash";
    private final static String MCC_PLAYER_SPLASH = "island-menu.playersplash";

    private final static String MCC_TEAM_SPLASH = "island-menu.teamsplash";

    private final static String[] splashOptions = {
            "Set Sail Today!",
            "Enjoy your island vacation!",
            "Get the latest news at mccisland.net!",
            "See live MCC stats at mcc.live!",
            "Support the Noxcrew!",
            MCC_TEAM_SPLASH,
            MCC_DATE_SPLASH,
            MCC_PLAYER_SPLASH

    };

    private final static String[] teamSplashOptions = {
            "Go %s!",
            "Support the %s!"
    };

    public static String getSplashText() {
        String splash =  splashOptions[Random.create().nextBetweenExclusive(0, splashOptions.length)];

        switch(splash) {
            case MCC_DATE_SPLASH:
                if (mccApi != null) {
                    EventInfo eventInfo = mccApi.getEventInfo();
                    if (eventInfo != null) {
                        if (eventInfo.getData().getDate().after(new Date())) {
                            String dateString = new SimpleDateFormat("MMMM dd").format(eventInfo.getData().getDate());
                            return String.format("Watch MCC %s on %s!", eventInfo.getData().getEvent(), dateString);
                        } else {
                            return String.format("What did you think of MCC %s?", eventInfo.getData().getEvent());
                        }
                    }
                }
                return splashOptions[0];

            case MCC_PLAYER_SPLASH:
                if (mccApi != null) {
                    ParticipantsInfo participantsInfo = mccApi.getParticipantsInfo();

                    if (participantsInfo != null) {
                        ParticipantsInfo.ParticipantsData.Participant player = null;

                        ParticipantsInfo.ParticipantsData.Participant selfPlayer = participantsInfo.getPlayer(MinecraftClient.getInstance().getSession().getUuidOrNull());
                        if (selfPlayer != null) {
                            LOGGER.debug("MCC Player found! Picking them always for splash text.");
                            player = selfPlayer;
                        } else {
                            List<Teams> teams = new java.util.ArrayList<>(List.of(Teams.values().clone()));
                            Collections.shuffle(teams);

                            for (Teams team : teams) {
                                if (team.equals(Teams.NONE) || team.equals(Teams.SPECTATORS)) {
                                    continue;
                                }

                                ParticipantsInfo.ParticipantsData.Participant[] members = participantsInfo.getData().getTeam(team);

                                if (members.length != 0) {
                                    int memberIndex = RandomUtils.nextInt(0, members.length);
                                    player = members[memberIndex];
                                    break;
                                }
                            }

                            if (player == null)
                                return splashOptions[0];
                        }

                        if (player == null || player.getUsername() == null)
                            return splashOptions[0];

                        EventInfo eventInfo = mccApi.getEventInfo();
                        String eventString = "";
                        if (eventInfo != null && eventInfo.getData().getDate().after(new Date())) {
                            eventString = " in MCC " + eventInfo.getData().getEvent();
                        }
                        return String.format("Check out %s!", player.getUsername() + eventString);
                    }
                }

                return splashOptions[0];

            case MCC_TEAM_SPLASH:
                Teams team = null;

                if (config != null) {
                    team = config.supportingTeam;
                }

                // If player is in the next/most recent MCC, pick their team.
                if ((team == null || team.equals(Teams.NONE)) && mccApi != null && mccApi.getParticipantsInfo() != null && MinecraftClient.getInstance().getSession().getUuidOrNull() != null) {
                    Teams selfTeam = mccApi.getParticipantsInfo().getPlayerTeam(MinecraftClient.getInstance().getSession().getUuidOrNull());
                    if (!selfTeam.equals(Teams.NONE)) {
                        LOGGER.debug("MCC Player team found!");
                        team = selfTeam;
                    }
                }

                while(team == null || team.equals(Teams.NONE) || (team.equals(Teams.SPECTATORS) && !config.supportingTeam.equals(Teams.SPECTATORS))) {
                    team = Teams.values()[RandomUtils.nextInt(0, Teams.values().length)];
                }

                String teamString = teamSplashOptions[RandomUtils.nextInt(0, teamSplashOptions.length)];

                return String.format(teamString, team.getName());

            default:
                return splash;
        }
    }

    private static IslandMenuConfig config;

    public static Text getButtonText() {
        return Text.translatable("island-menu.menu.join");
    }

    public static String getButtonServerName() {
        return "MCC Island";
    }

    public static String getButtonServerAddress() {
        return "play.mccisland.net";
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Island Menu...");

        LOGGER.info("Registering Theme...");

        Registry.register(ServerMainMenuLibClient.registry, Identifier.of(IslandMenuClient.MOD_ID, "normal"), normalTheme);
        Registry.register(ServerMainMenuLibClient.registry, Identifier.of(IslandMenuClient.MOD_ID, "halloween"), halloweenTheme);
        Registry.register(ServerMainMenuLibClient.registry, Identifier.of(IslandMenuClient.MOD_ID, "winter"), winterTheme);

        LOGGER.info("Configuring Config...");

        AutoConfig.register(IslandMenuConfig.class, GsonConfigSerializer::new);

        AutoConfig.getConfigHolder(IslandMenuConfig.class).registerSaveListener(IslandMenuClient::onConfigSave);

        config = AutoConfig.getConfigHolder(IslandMenuConfig.class).getConfig();

        LOGGER.info("Requesting information from MCC API...");

        mccApi = new MCCApi(config.devOptions.apiUrl);

        // Calls to make the cache when minor lag is fine.
        mccApi.getEventInfo();
        mccApi.getParticipantsInfo();


        LOGGER.info("Island Menu Initialized!");
    }

    private static ActionResult onConfigSave(ConfigHolder<IslandMenuConfig> islandMenuConfigConfigHolder, IslandMenuConfig islandMenuConfig) {
        LOGGER.info("Updating config!");

//        config = islandMenuConfig;

        if (mccApi == null || !mccApi.getBaseUrl().equalsIgnoreCase(config.devOptions.apiUrl))
            mccApi = new MCCApi(config.devOptions.apiUrl);

        return ActionResult.PASS;
    }
}
