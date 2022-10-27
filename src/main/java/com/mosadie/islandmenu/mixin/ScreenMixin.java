package com.mosadie.islandmenu.mixin;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

// This class is 100% jank, theoretically should just be able to @Invoke Screen.addDrawableChild, but the mapping fails to resolve.
@Mixin(Screen.class)
public class ScreenMixin {

    @Shadow
    private List<Drawable> drawables;

    @Shadow
    private List<Element> children;

    @Shadow
    private List<Selectable> selectables;

    protected <T extends Element & Drawable & Selectable> T addDrawableChild_IM(T drawableElement) {
        this.drawables.add((Drawable)drawableElement);
        return this.addSelectableChild_IM(drawableElement);
    }

    protected <T extends Element & Selectable> T addSelectableChild_IM(T child) {
        this.children.add(child);
        this.selectables.add((Selectable)child);
        return child;
    }
}
