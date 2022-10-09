package com.mosadie.islandmenu.client.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class IslandMenuLangProvider extends FabricLanguageProvider {
    protected IslandMenuLangProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("island-menu.menu,join", "Join MCC Island!");
    }
}
