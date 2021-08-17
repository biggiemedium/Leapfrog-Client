package dev.leap.frog.Mixin;

import dev.leap.frog.LeapFrog;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void onShutdown(CallbackInfo ci) {

        LeapFrog.getFileManager().save();

    }

    @Inject(method = "crashed", at = @At("HEAD"))
    public void onCrash(CallbackInfo ci) {

        LeapFrog.getFileManager().save();

    }
}
