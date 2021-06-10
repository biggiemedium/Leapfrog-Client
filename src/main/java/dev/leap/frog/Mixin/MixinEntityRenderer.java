package dev.leap.frog.Mixin;

import dev.leap.frog.Event.Render.EventRenderHurtCam;
import dev.leap.frog.LeapFrog;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    public void renderEffect(float partialTicks, CallbackInfo ci) {

        EventRenderHurtCam packet = new EventRenderHurtCam(partialTicks);
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            ci.cancel();
        }

    }

}
