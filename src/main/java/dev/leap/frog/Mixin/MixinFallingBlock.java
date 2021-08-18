package dev.leap.frog.Mixin;

import dev.leap.frog.Event.Render.EventRenderBlockFall;
import dev.leap.frog.LeapFrog;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.entity.item.EntityFallingBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderFallingBlock.class)
public class MixinFallingBlock {

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    public void onRender(EntityFallingBlock entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        EventRenderBlockFall packet = new EventRenderBlockFall();
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            ci.cancel();
        }

    }

}
