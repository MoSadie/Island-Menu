package com.mosadie.islandmenu.client.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class IslandMenuEnLangProvider extends FabricLanguageProvider {

    protected IslandMenuEnLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("island-menu.menu.join", "Join MCC Island!");

        translationBuilder.add("text.autoconfig.island-menu.title", "Island Menu Settings");

        translationBuilder.add("text.autoconfig.island-menu.option.supportingTeam", "Team to support");
        translationBuilder.add("text.autoconfig.island-menu.option.supportingTeam.@Tooltip", "Show your support for a team in the title screen's splash text, chooses the specified team instead of randomly picking a team when required.");

        translationBuilder.add("text.autoconfig.island-menu.option.devOptions", "Developer Settings");
        translationBuilder.add("text.autoconfig.island-menu.option.devOptions.apiUrl", "MCC API Base URL");

    }
}
