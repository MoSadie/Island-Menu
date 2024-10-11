package com.mosadie.islandmenu.client.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class IslandMenuHuLangProvider extends FabricLanguageProvider {
    protected IslandMenuHuLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "hu_hu", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("island-menu.menu.join", "Csatlakozz az MCC Islandre!");

        translationBuilder.add("text.autoconfig.island-menu.title", "Island Menu Beállítások");

        translationBuilder.add("text.autoconfig.island-menu.option.supportingTeam", "Támogatni kívánt csapat");
        translationBuilder.add("text.autoconfig.island-menu.option.supportingTeam.@Tooltip", "Mutassa ki egy csapat iránti elkötelezettségét a kezdőképernyőn egy felugró üzenetben, a megadott csapatot választja ki, ahelyett, hogy véletlenszerűen választana egy csapatot, amikor szükséges.");

        translationBuilder.add("text.autoconfig.island-menu.option.devOptions", "Fejlesztői Beállítások");
        translationBuilder.add("text.autoconfig.island-menu.option.devOptions.apiUrl", "MCC API Bázis URL-cím");
    }
}
