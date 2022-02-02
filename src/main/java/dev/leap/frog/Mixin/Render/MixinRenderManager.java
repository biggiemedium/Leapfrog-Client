package dev.leap.frog.Mixin.Render;

import dev.leap.frog.Event.Render.EventRenderEntity;
import dev.leap.frog.LeapFrog;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderManager.class)
public class MixinRenderManager {

    @Inject(method = "renderEntity", at = @At("HEAD"), cancellable = true)
    public void onEntityRender(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_, CallbackInfo ci) {
        EventRenderEntity packet = new EventRenderEntity(entityIn, x, y, z, yaw, partialTicks);
        LeapFrog.EVENT_BUS.post(packet);
        if(packet.isCancelled()) {
            ci.cancel();
        }
    }
}
