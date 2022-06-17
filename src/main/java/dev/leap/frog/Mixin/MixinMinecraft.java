package dev.leap.frog.Mixin;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Client.ChatLogger;
import dev.leap.frog.Util.Listeners.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void onShutdown(CallbackInfo ci) {
        LeapFrog.getFileManager().save();
        LeapFrog.getDiscordManager().Stop();
        ChatLogger.INSTANCE.close(ChatLogger.INSTANCE.getWriter());
    }

    @Inject(method = "crashed", at = @At("HEAD"))
    public void onCrash(CallbackInfo ci) {
        //LeapFrog.getFileManager().save();
    }
}
