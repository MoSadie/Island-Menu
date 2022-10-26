package com.mosadie.islandmenu.mixin;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Screen.class)
public interface ScreenInvoker {

    // For reference the method I am looking for is Screen.addDrawableChild
    @Invoker("addDrawableChild") //FIXME This shortened version works fine in dev env, but fails in live game, doesn't seem to map correctly.

    //@Invoker("Lnet/minecraft/client/gui/screen/Screen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;")
    // Error is 'Cannot find method Lnet/minecraft/client/gui/screen/Screen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element; in target class'
    // The weird part is I copied this from https://maven.fabricmc.net/docs/yarn-1.19.2+build.28/net/minecraft/client/gui/screen/Screen.html#addDrawableChild(T)
    // The weirdest part, if I manually put this string into the refmap json file everything just works.
    <T extends Element & Drawable & Selectable> T callAddDrawableChild(T drawableElement);
}
