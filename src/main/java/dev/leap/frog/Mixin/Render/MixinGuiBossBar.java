package dev.leap.frog.Mixin.Render;

import dev.leap.frog.Event.Render.EventRenderBossBar;
import dev.leap.frog.LeapFrog;
import net.minecraft.client.gui.GuiBossOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiBossOverlay.class)
public class MixinGuiBossBar {

    @Inject(method = "renderBossHealth", at = @At("HEAD"), cancellable = true)
    public void cancelBossBar(CallbackInfo ci) {

        EventRenderBossBar packet = new EventRenderBossBar();
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            ci.cancel();
        }

    }
}
