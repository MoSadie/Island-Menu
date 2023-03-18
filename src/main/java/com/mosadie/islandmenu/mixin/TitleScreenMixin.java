package com.mosadie.islandmenu.mixin;

import com.mosadie.islandmenu.client.IslandMenuClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    @Shadow @Nullable private String splashText;

    @Final @Mutable
    @Shadow @Nullable public static CubeMapRenderer PANORAMA_CUBE_MAP = new CubeMapRenderer(new Identifier(IslandMenuClient.getModID(), "textures/gui/title/background/" + IslandMenuClient.menuTheme.getPath() + "/panorama"));

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "isRealmsNotificationsGuiDisplayed", at = @At("HEAD"), cancellable = true)
    private void injectRealmsNotifications(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(false);
        info.cancel();
    }

    @Inject(method = "init()V", at = @At("HEAD"))
    private void injectSplashText(CallbackInfo info) {
        this.splashText = IslandMenuClient.getRandomSplashText();
    }

    @Redirect(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;initWidgetsNormal(II)V"))
    private void redirectInitWidgetsNormal(TitleScreen self, int y, int spacingY) {
        ButtonWidget.Builder singlePlayerButtonWidgetBuilder = ButtonWidget.builder(Text.translatable("menu.singleplayer"), (button -> {
            MinecraftClient.getInstance().setScreen(new SelectWorldScreen((self)));
        }))
                .size(200, 20)
                .position(self.width / 2 - 100, y);

        this.addDrawableChild(singlePlayerButtonWidgetBuilder.build());

        final Text disabledText = ((TitleScreenInvoker) this).invokeGetMultiplayerDisabledText();
        boolean isDisabled = disabledText != null;

        Tooltip tooltip = Tooltip.of(disabledText);

        ButtonWidget.Builder joinIslandButtonWidgetBuilder = ButtonWidget.builder(Text.translatable("island-menu.menu.join"), (button) -> {
            ServerAddress serverAddress = ServerAddress.parse("play.mccisland.net");
            ServerInfo serverInfo =    new ServerInfo("MCC Island", serverAddress.getAddress(), false);
            serverInfo.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.ENABLED);
            ConnectScreen.connect(self, MinecraftClient.getInstance(), serverAddress, serverInfo);
        }).position(self.width / 2 - 100, y + spacingY).size(200, 20);

        if (isDisabled) {
            joinIslandButtonWidgetBuilder.tooltip(tooltip);
        }

        ButtonWidget joinIslandButtonWidget = joinIslandButtonWidgetBuilder.build();
        joinIslandButtonWidget.active = !isDisabled;
        this.addDrawableChild(joinIslandButtonWidgetBuilder.build());

        ButtonWidget.Builder multiplayerButtonWidgetBuilder = ButtonWidget.builder(Text.translatable("menu.multiplayer"), button -> {
            Screen screen = MinecraftClient.getInstance().options.skipMultiplayerWarning ? new MultiplayerScreen(self) : new MultiplayerWarningScreen(self);
            MinecraftClient.getInstance().setScreen(screen);
        }).position(self.width / 2 - 100, y + spacingY * 2).size(200, 20);

        if (isDisabled)
            multiplayerButtonWidgetBuilder.tooltip(tooltip);

        ButtonWidget multiplayerButtonWidget = multiplayerButtonWidgetBuilder.build();
        multiplayerButtonWidget.active = !isDisabled;

        this.addDrawableChild(multiplayerButtonWidget);
    }
}
