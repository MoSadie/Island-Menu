package com.mosadie.islandmenu.client.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class IslandMenuHuLangProvider extends FabricLanguageProvider {
    protected IslandMenuHuLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "hu_hu");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("island-menu.menu.join", "Csatlakozz az MCC Islandre!");
    }
}
