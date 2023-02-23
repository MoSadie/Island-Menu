package com.mosadie.islandmenu.client.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class IslandMenuEnLangProvider extends FabricLanguageProvider {
    protected IslandMenuEnLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("island-menu.menu.join", "Join MCC Island!");
    }
}
