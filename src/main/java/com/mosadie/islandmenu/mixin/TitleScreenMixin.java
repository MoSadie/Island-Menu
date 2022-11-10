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
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
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

import java.util.function.Consumer;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    @Shadow @Nullable private String splashText;

    @Final @Mutable
    @Shadow @Nullable public static CubeMapRenderer PANORAMA_CUBE_MAP = new CubeMapRenderer(new Identifier(IslandMenuClient.getModID(), "textures/gui/title/background/" + IslandMenuClient.menuTheme.getPath() + "/panorama"));

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "areRealmsNotificationsEnabled", at = @At("HEAD"), cancellable = true)
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
        this.addDrawableChild(new ButtonWidget(self.width / 2 - 100, y, 200, 20, Text.translatable("menu.singleplayer"), (button) -> {
            MinecraftClient.getInstance().setScreen(new SelectWorldScreen(self));
        }));

        final Text text = ((TitleScreenInvoker) this).invokeGetMultiplayerDisabledText();
        boolean bl = text == null;
        ButtonWidget.TooltipSupplier tooltipSupplier = text == null ? ButtonWidget.EMPTY : new ButtonWidget.TooltipSupplier() {
            public void onTooltip(ButtonWidget buttonWidget, MatrixStack matrixStack, int i, int j) {
                self.renderOrderedTooltip(matrixStack, MinecraftClient.getInstance().textRenderer.wrapLines(text, Math.max(self.width / 2 - 43, 170)), i, j);
            }

            public void supply(Consumer<Text> consumer) {
                consumer.accept(text);
            }
        };

        this.addDrawableChild(new ButtonWidget(self.width / 2 - 100, y + spacingY, 200, 20, Text.translatable("island-menu.menu,join"), (button) -> {
            ServerAddress serverAddress = ServerAddress.parse("play.mccisland.net");
            ServerInfo serverInfo =    new ServerInfo("MCC Island", serverAddress.getAddress(), false);
            serverInfo.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.ENABLED);
            ConnectScreen.connect(self, MinecraftClient.getInstance(), serverAddress, serverInfo);
        }, tooltipSupplier)).active = bl;


        this.addDrawableChild(new ButtonWidget(self.width / 2 - 100, y + spacingY * 2, 200, 20, Text.translatable("menu.multiplayer"), (button) -> {
            Screen screen = MinecraftClient.getInstance().options.skipMultiplayerWarning ? new MultiplayerScreen(self) : new MultiplayerWarningScreen(self);
            MinecraftClient.getInstance().setScreen(screen);
        }, tooltipSupplier)).active = bl;
    }
}
