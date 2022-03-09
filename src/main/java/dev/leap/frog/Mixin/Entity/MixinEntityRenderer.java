package dev.leap.frog.Mixin.Entity;

import dev.leap.frog.Event.Render.EventRenderHurtCam;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Render.ViewClip;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
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

    @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"), expect = 0)
    private RayTraceResult rayTraceBlocks(WorldClient worldClient, Vec3d start, Vec3d end) {
        if(LeapFrog.getModuleManager().getModule(ViewClip.class).isToggled()) {
            return null;
        } else {
            return worldClient.rayTraceBlocks(start, end);
        }
    }

}
