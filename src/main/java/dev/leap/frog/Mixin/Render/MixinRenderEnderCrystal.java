package dev.leap.frog.Mixin.Render;

import dev.leap.frog.Event.Render.EventRenderEntityEnderCrystal;
import dev.leap.frog.LeapFrog;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.item.EntityEnderCrystal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderEnderCrystal.class)
public class MixinRenderEnderCrystal {

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    public void onRender(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        EventRenderEntityEnderCrystal packet = new EventRenderEntityEnderCrystal(entity, x, y, z);
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            ci.cancel();
        }
    }

}
